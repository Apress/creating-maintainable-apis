package rs.exproit.load_profile_generator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rs.exproit.load_profile_generator.protocol.LPCreationRequest;
import rs.exproit.load_profile_generator.protocol.LoadProfileRPC;
import rs.exproit.load_profile_generator.protocol.ServiceError;

public class ClientServerTest {
    private static final int PORT = 65111;
    
    private static App app;
    private static NettyTransceiver client;
    private static LoadProfileRPC proxy;
    private static MockProducer<String, byte[]> mockProducer;
    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    @BeforeClass
    public static void setup() throws IOException {
        PartitionInfo partitionInfo = 
                new PartitionInfo(LoadProfileWriter.TOPIC_NAME, 0, null, null, null);
        Cluster cluster = new Cluster(
                Collections.<Node>emptyList(), Arrays.asList(partitionInfo), Collections.<String>emptySet());
        mockProducer = new MockProducer<>(
                cluster, 
                false, 
                new DefaultPartitioner(), 
                new StringSerializer(), 
                new ByteArraySerializer());
        app = new App(new LoadProfileRPCImpl(new LoadProfileWriter(mockProducer)));
        app.start(PORT);
        client = new NettyTransceiver(new InetSocketAddress(PORT));
        proxy = SpecificRequestor.getClient(LoadProfileRPC.class, client);
    }
    
    @Before
    public void clearMockProducer() {
        mockProducer.clear();
    }
    
    @AfterClass
    public static void teardown() {
        client.close();
        app.stop();
        executor.shutdown();
    }

    private LPCreationRequest createRequest(String loadCondition) {
        LPCreationRequest request = LPCreationRequest.newBuilder().build();        
        if (loadCondition != null) {
            request.setLoadCondition(loadCondition);
        }
        return request;
    }
    
    @Test
    public void triggerSuccessfulLPCreationsAndValidateThatDataIsSent() throws IOException {
        // We need to setup the notifier thread to propel the main test.
        executor.execute(new Runnable() {
            private int requestCount = 101;
            
            @Override
            public void run() {
                while (requestCount > 0) {
                    if (mockProducer.completeNext()) {
                        requestCount--;
                    }
                    Thread.yield();
                }
            }            
        });
        
        // Sends requests and check responses.
        String response;
        for (int i = 0; i < 100; i++) {
            response = 
                    proxy
                     .lpCreate(createRequest("http://example.org/api/Controller/LoadConditions/" + i))
                     .toString();
            assertNotNull(response);
            assertTrue(response.contains(
                    "\"loadCondition\": \"http://example.org/api/Controller/LoadConditions/" + i + "\""));
        }
        response = proxy.lpCreate(createRequest(null)).toString();
        assertNotNull(response);
        assertTrue(response.contains("\"loadCondition\": null"));

        // Check history of sent events.
        mockProducer.flush();
        List<ProducerRecord<String, byte[]>> records = mockProducer.history();
        assertEquals(101, records.size());
        
        for (ProducerRecord<String, byte[]> record : records) {
            assertTrue(record.key().startsWith(LoadProfileRPCImpl.DEVICE_ID_PREFIX));
            assertNotNull(record.value());
        }
    }

    @Test(expected = ServiceError.class)
    public void triggerABadLPCreationAndValidateThatExceptionHasOccurred() throws IOException {
        // We need to setup the notifier thread to announce an exception.
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!mockProducer.errorNext(new RuntimeException())) {
                    Thread.yield();
                }
            }            
        });
        
        proxy.lpCreate(createRequest(null));
    }
}

package rs.exproit.load_profile_system;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.MockTime;
import kafka.utils.TestUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import kafka.zk.EmbeddedZookeeper;
import rs.exproit.load_profile_generator.protocol.LPCreationRequest;
import rs.exproit.load_profile_generator.protocol.LoadProfileRPC;

public class LoadProfileSystemTest {
    private static EmbeddedZookeeper zkServer;
    private static ZkClient zkClient;
    private static KafkaServer kafkaServer;

    @BeforeClass
    public static void setupKafka() throws IOException {
        zkServer = new EmbeddedZookeeper();
        String zkConnect = "127.0.0.1:" + zkServer.port();
        zkClient = new ZkClient(zkConnect, 10000, 10000, ZKStringSerializer$.MODULE$);
        ZkUtils zkUtils = ZkUtils.apply(zkClient, false);

        Properties config = new Properties();
        config.setProperty("zookeeper.connect", zkConnect);
        config.setProperty("broker.id", "0");
        config.setProperty("listeners", "PLAINTEXT://0.0.0.0:9092");
        config.setProperty("auto.create.topics.enable", "false");
        MockTime mockTime = new MockTime();
        kafkaServer = TestUtils.createServer(new KafkaConfig(config), mockTime);
        
        String topicName = "load_profile";
        int numPartitions = 3;

        AdminUtils.createTopic(
                zkUtils, topicName, numPartitions, 1, new Properties(), RackAwareMode.Disabled$.MODULE$);
        final List<KafkaServer> servers = new ArrayList<>();
        servers.add(kafkaServer);
        TestUtils.waitUntilMetadataIsPropagated(
                scala.collection.JavaConversions.asScalaBuffer(servers), topicName, 0, 5000);    
    }
 
    @AfterClass 
    public static void stopKafka() {
        kafkaServer.shutdown();
        zkClient.close();
        zkServer.shutdown();
    }
    
    private NettyTransceiver controller;
    private ByteArrayOutputStream stdOutputBuffer = new ByteArrayOutputStream();
    private PrintStream standardOutput = System.out;
    
    @Before
    public void startLoadProfileSystem() throws IOException {
        int port = 65111;
        rs.exproit.load_profile_generator.App.main(new String[] { Integer.toString(port) });
        controller = new NettyTransceiver(new InetSocketAddress(port));        

        System.setOut(new PrintStream(stdOutputBuffer));        
        rs.exproit.load_profile_classifier.App.main(new String[] { "3" });
    }
    
    @After
    public void stopLoadProfileSystem() {
        controller.close();
        System.setOut(standardOutput);
    }

    private LPCreationRequest createRequest(String loadCondition) {
        LPCreationRequest request = LPCreationRequest.newBuilder().build();        
        if (loadCondition != null) {
            request.setLoadCondition(loadCondition);
        }
        return request;
    }
    
    private void waitUntilAllEventsAreProcessed(int numEvents) {
        while (stdOutputBuffer.toString().split("\r?\n").length < numEvents) {
            Thread.yield();
        }        
    }
    
    @Test(timeout = 60000)
    public final void tiggerLoadProfileCreationsAndCheckResult() 
            throws InterruptedException, IOException {
        LoadProfileRPC proxy = SpecificRequestor.getClient(LoadProfileRPC.class, controller);
        
        for (int i = 0; i < 100; i++) {
            String response = proxy.lpCreate(createRequest("Data: " + i)).toString();
            assertNotNull(response);
            assertTrue(response.contains("\"loadCondition\": \"Data: " + i + "\""));
        }

        waitUntilAllEventsAreProcessed(100);
        
        String result = stdOutputBuffer.toString();
        for (int i = 0; i < 100; i++) {
            assertTrue(result.contains("\"loadCondition\": \"Data: " + i + "\""));
        }
    }
}
package rs.exproit.load_profile_classifier;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;

import rs.exproit.load_profile_generator.domain.LoadProfileModel;

public class LPClassifierTest {
    private final DatumWriter<LoadProfileModel> eventWriter = 
            new SpecificDatumWriter<>(LoadProfileModel.class);
    
    private byte[] serializeLP(LoadProfileModel lp) throws IOException {
        assertNotNull("The load profile reference cannot be null", lp);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);

        eventWriter.write(lp, encoder);
        encoder.flush();
        out.close();
        
        return out.toByteArray();
    }

    private final TopicPartition partition = new TopicPartition(LoadProfileReader.TOPIC_NAME, 0);
    private final MockConsumer<String, byte[]> mockConsumer = 
            new MockConsumer<>(OffsetResetStrategy.EARLIEST);

    @Before
    public void setup() {
        Map<TopicPartition, Long> offsets = new HashMap<>();
        offsets.put(partition, 0L);
        mockConsumer.updateBeginningOffsets(offsets);
        mockConsumer.assign(Arrays.asList(partition));
    }

    private class EventProcessor implements Observer {
        private int startIdx;
        
        @Override
        public void update(Observable o, Object arg) {
            assertTrue(o instanceof LoadProfileReader);
            assertNotNull(arg);
            assertTrue(arg instanceof LoadProfileModel);
            
            LoadProfileModel lp = (LoadProfileModel) arg;
            assertEquals(Integer.valueOf(100 + startIdx), lp.getCreatedAt());
            assertEquals("TEST_" + startIdx, lp.getDeviceId().toString());
            assertEquals(Double.valueOf(1.0), lp.getData().iterator().next());      
            startIdx++;
        }      
    }
    
    @Test
    public final void consumeEventsFromATopicAndProcessThem() {
        final LoadProfileReader reader = new LoadProfileReader(mockConsumer);
        reader.addObserver(new EventProcessor());

        final Runnable pollTask = new Runnable() {
            private final List<Double> testData = new ArrayList<>();            
            {
                testData.add(1.0);                
            }

            private int pollCount;
            
            @Override
            public void run() {
                try {
                    // We need to "get out" the consumer form its infinite loop
                    // once all data were consumed.
                    if (pollCount == 10) {
                        reader.shutdown();                 
                    } else {
                        mockConsumer.schedulePollTask(this);
                        pollCount++;
                    }
                    
                    LoadProfileModel.Builder builder = LoadProfileModel.newBuilder();
                    LoadProfileModel lp = builder
                            .setCreatedAt(100 + pollCount)
                            .setDeviceId("TEST_" + pollCount)
                            .setData(testData)
                            .build();
                
                    mockConsumer.addRecord(new ConsumerRecord<String, byte[]>(
                            partition.topic(),
                            partition.partition(),
                            pollCount,
                            lp.getDeviceId().toString(),
                            serializeLP(lp)));
                } catch (IOException e) {
                    fail("Unexpected exception occurred in the poll controller thread.");
                } finally {
                    reader.shutdown();                 
                }
            }
        };
        
        mockConsumer.schedulePollTask(pollTask);
        reader.run();
    }
}
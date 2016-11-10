package rs.exproit.load_profile_generator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import rs.exproit.load_profile_generator.domain.Category;
import rs.exproit.load_profile_generator.domain.LoadProfileModel;

public class LoadProfilePartitionerTest {
    private static final String TEST_TOPIC = "test";
    private final LoadProfilePartitioner partitioner = new LoadProfilePartitioner();
    private final LoadProfileModel lp;
    
    public LoadProfilePartitionerTest() {
        List<Double> data = Arrays.asList(0.1, 200.2);
        lp = LoadProfileModel
                .newBuilder()
                .setCreatedAt(0)
                .setDeviceId("SM_05")
                .setConsumerCategory(Category.RESIDENTIAL)
                .setData(data)
                .build();        
    }
    
    @Test
    public final void getPartitionIndexForTheTopicWithASinglePartition() {
        assertEquals(0, partitioner.partition(TEST_TOPIC, lp.getDeviceId().toString(), lp, 1));
        lp.setDeviceId("SM_12");
        assertEquals(0, partitioner.partition(TEST_TOPIC, lp.getDeviceId().toString(), lp, 1));
        lp.setConsumerCategory(Category.INDUSTRIAL);
        assertEquals(0, partitioner.partition(TEST_TOPIC, lp.getDeviceId().toString(), lp, 1));
    }

    @Test
    public final void getPartitionIndexForTheTopicWithMultiplePartitions() {
        assertEquals(0, partitioner.partition(TEST_TOPIC, lp.getDeviceId().toString(), lp, 3));
        lp.setDeviceId("SM_12");
        assertEquals(1, partitioner.partition(TEST_TOPIC, lp.getDeviceId().toString(), lp, 3));
        lp.setConsumerCategory(Category.INDUSTRIAL);
        assertEquals(2, partitioner.partition(TEST_TOPIC, lp.getDeviceId().toString(), lp, 3));
    }
}

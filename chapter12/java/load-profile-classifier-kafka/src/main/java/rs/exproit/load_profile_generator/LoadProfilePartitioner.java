package rs.exproit.load_profile_generator;

import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import rs.exproit.load_profile_generator.domain.Category;
import rs.exproit.load_profile_generator.domain.LoadProfileModel;

final class LoadProfilePartitioner {
    public int partition(String topic, String key, LoadProfileModel value, int numPartitions) {
        int lastPartitionIdx = numPartitions - 1;
        try {
            if (value.getConsumerCategory() == Category.INDUSTRIAL) {
                return lastPartitionIdx;
            } else {
                if (lastPartitionIdx > 0) {
                    return (Math.abs(Utils.murmur2(key.getBytes()))) % lastPartitionIdx;
                } else {
                    return lastPartitionIdx;
                }
            }
        } catch (NullPointerException | ClassCastException e) {
            throw new InvalidRecordException("Key or value is missing or isn't valid.");
        }    
    }
}
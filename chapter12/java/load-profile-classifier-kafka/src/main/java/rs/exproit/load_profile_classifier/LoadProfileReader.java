package rs.exproit.load_profile_classifier;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Observable;
import java.util.Properties;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import rs.exproit.load_profile_generator.domain.LoadProfileModel;

final class LoadProfileReader extends Observable implements Runnable {
    static final String TOPIC_NAME = "load_profile";
    private static final String CONFIGURATION_FILE = "config/kafka-consumer.properties";
    
    private final Consumer<String, byte[]> consumer;
    
    LoadProfileReader() throws IOException {
        Properties config = new Properties();
        config.load(new FileReader(CONFIGURATION_FILE));
        consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Collections.singletonList(TOPIC_NAME), new RebalanceHandler());
    }

    LoadProfileReader(Consumer<String, byte[]> consumer) {
        this.consumer = consumer;
    }
    
    private final DatumReader<LoadProfileModel> eventReader = 
            new SpecificDatumReader<>(LoadProfileModel.class);
    
    private LoadProfileModel deserializeLP(byte[] lpData) throws IOException {
        final Decoder decoder = DecoderFactory.get().binaryDecoder(lpData, null);
        return eventReader.read(null, decoder);
    }
    
    private class RebalanceHandler implements ConsumerRebalanceListener {
        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            // Before being revoked from partitions make sure that offsets are saved.
            // This is the reason that we use a synchronous call here.
            consumer.commitSync();
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            // This method is useful to seek into the matching offset, when offsets are saved
            // outside of Kafka (for example, inside a database).
        }
    }    
    
    @Override
    public void run() {
        try {
            while (true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(Long.MAX_VALUE);                
                for (ConsumerRecord<String, byte[]> record : records) {
                    setChanged();
                    notifyObservers(deserializeLP(record.value()));
                }

                consumer.commitAsync();    
            }
        } catch (WakeupException e) {
            // This should be ignored, as it is a normal way to signal exit.
        } catch (IOException e) {
            // In production you should properly log the error.
            e.printStackTrace();
        } finally {
            try {
                // Before exiting we want to make sure that offsets are saved.
                // This is the reason that we use a synchronous call here.
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }
    
    public void shutdown() {
        consumer.wakeup();
    }    
}

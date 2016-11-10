package rs.exproit.load_profile_generator;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import rs.exproit.load_profile_generator.domain.LoadProfileModel;

final class LoadProfileWriter {
    static final String TOPIC_NAME = "load_profile";
    private static final String CONFIGURATION_FILE = "config/kafka-producer.properties";
    
    private final Producer<String, byte[]> producer;
    
    LoadProfileWriter() throws IOException {
        Properties config = new Properties();
        config.load(new FileReader(CONFIGURATION_FILE));
        producer = new KafkaProducer<>(config);
    }

    LoadProfileWriter(Producer<String, byte[]> producer) {
        this.producer = producer;
    }
    
    private final DatumWriter<LoadProfileModel> eventWriter = 
            new SpecificDatumWriter<>(LoadProfileModel.class);
    
    private byte[] serializeLP(LoadProfileModel lp) throws IOException {
        assert lp != null : "The load profile reference cannot be null";
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);

        eventWriter.write(lp, encoder);
        encoder.flush();
        out.close();
        
        return out.toByteArray();
    }
    
    private final LoadProfilePartitioner partitioner = new LoadProfilePartitioner();
    
    void sendLP(LoadProfileModel lp) 
            throws IOException, ExecutionException, InterruptedException {
        String deviceId = lp.getDeviceId().toString();
        int numPartitions = producer.partitionsFor(TOPIC_NAME).size();
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(
                TOPIC_NAME, 
                partitioner.partition(TOPIC_NAME, deviceId, lp, numPartitions), 
                deviceId, 
                serializeLP(lp));
        
        producer.send(record).get();
    }

    void shutdown() {
        producer.close();
    }    
}

package rs.exproit.load_profile_generator;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import rs.exproit.load_profile_generator.domain.Category;
import rs.exproit.load_profile_generator.domain.LoadProfileModel;
import rs.exproit.load_profile_generator.protocol.LPCreationRequest;
import rs.exproit.load_profile_generator.protocol.LoadProfileRPC;
import rs.exproit.load_profile_generator.protocol.ServiceError;

final class LoadProfileRPCImpl implements LoadProfileRPC {
    static final String DEVICE_ID_PREFIX = "SM_";
    private static final DatumWriter<LoadProfileModel> lpDatumWriter = 
            new SpecificDatumWriter<>(LoadProfileModel.class);
    private final File dataFile;

    LoadProfileRPCImpl(String dataFile) {
        this.dataFile = new File(dataFile);
    }
    
    private List<Double> doubleArrayToList(double[] array) {
        return DoubleStream
                .of(array)
                .mapToObj(Double::valueOf)
                .collect(Collectors.toList());
    }
    
    private final Random rnd = new Random(1L);
    private final Category[] consumerTypes = Category.values();

    private LoadProfileModel acquireLP() {
        double[] lpData = new double[4 * 24];
        for (int i = 0; i < lpData.length; i++) {
            lpData[i] = rnd.nextDouble() * 1000;
        }
        
        return LoadProfileModel
                .newBuilder()
                // Simulate LP creation for various days.
                .setCreatedAt(rnd.nextInt(Integer.MAX_VALUE))
                .setDeviceId(DEVICE_ID_PREFIX + rnd.nextInt(100))
                .setData(doubleArrayToList(lpData))
                // Simulate different category of consumers.
                .setConsumerCategory(consumerTypes[rnd.nextInt(consumerTypes.length)])
                .build();
    }
    
    public CharSequence lpCreate(LPCreationRequest request)
            throws AvroRemoteException, ServiceError {
        LoadProfileModel lpModel = acquireLP();
        lpModel.setLoadCondition(request.getLoadCondition());

        try (
            DataFileWriter<LoadProfileModel> dataFileWriter = new DataFileWriter<>(lpDatumWriter);
        ){
            if (dataFile.exists() && !dataFile.isDirectory()) {
                dataFileWriter.appendTo(dataFile);
            } else {
                dataFileWriter.setMeta("about", "Sample generated data file for chapter 11.");
                dataFileWriter.setCodec(CodecFactory.snappyCodec());
                dataFileWriter.create(lpModel.getSchema(), dataFile);
            }
            dataFileWriter.append(lpModel);
            return lpModel.toString();
        } catch (Exception e) {
            throw ServiceError
                    .newBuilder()
                    .setCode(1)
                    .setMessage$(e.getMessage())
                    .build();
        }
    }

    public void shutdown() {
    }
}

package rs.exproit.load_profile_generator;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.apache.avro.AvroRemoteException;

import rs.exproit.load_profile_generator.domain.Category;
import rs.exproit.load_profile_generator.domain.LoadProfileModel;
import rs.exproit.load_profile_generator.protocol.LPCreationRequest;
import rs.exproit.load_profile_generator.protocol.LoadProfileRPC;
import rs.exproit.load_profile_generator.protocol.ServiceError;

final class LoadProfileRPCImpl implements LoadProfileRPC {
    static final String DEVICE_ID_PREFIX = "SM_";

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
    
    private final LoadProfileWriter lpWriter;
    
    LoadProfileRPCImpl() throws IOException {
        lpWriter = new LoadProfileWriter();
    }

    LoadProfileRPCImpl(LoadProfileWriter lpWriter) {
        this.lpWriter = lpWriter;
    }
    
    public CharSequence lpCreate(LPCreationRequest request)
            throws AvroRemoteException, ServiceError {
        LoadProfileModel lpModel = acquireLP();
        lpModel.setLoadCondition(request.getLoadCondition());
        
        try {
            lpWriter.sendLP(lpModel);            
            return lpModel.toString();
        } catch (Exception e) {
            throw ServiceError
                    .newBuilder()
                    .setCode(1)
                    .setMessage$(e.getMessage())
                    .build();
        }
    }

    void shutdown() {
        lpWriter.shutdown();
    }
}

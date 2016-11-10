package rs.exproit.load_profile_generator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rs.exproit.load_profile_generator.protocol.LPCreationRequest;
import rs.exproit.load_profile_generator.protocol.LoadProfileRPC;

public class ClientServerTest {
    private static final int PORT = 65111;
    
    private App app;
    private NettyTransceiver client;

    @Before
    public void createClient() throws IOException {
        app = new App();
        app.start(PORT);
        client = new NettyTransceiver(new InetSocketAddress(PORT));
    }
    
    @After
    public void closeClient() {
        client.close();
        app.stop();
    }

    private LPCreationRequest createRequest(String loadCondition) {
        LPCreationRequest request = LPCreationRequest.newBuilder().build();        
        if (loadCondition != null) {
            request.setLoadCondition(loadCondition);
        }
        return request;
    }
    
    @Test
    public void triggerLPCreationsAndValidateThatDataIsSaved() throws IOException {
        LoadProfileRPC proxy = SpecificRequestor.getClient(LoadProfileRPC.class, client);
        
        String response;
        for (int i = 0; i < 100; i++) {
            response = 
                    proxy
                     .lpCreate(createRequest("http://example.org/api/Controller/LoadConditions/" + i))
                     .toString();
            assertNotNull(response);
            assert(response).contains(
                    "\"loadCondition\": \"http://example.org/api/Controller/LoadConditions/" + i + "\"");
        }
        response = proxy.lpCreate(createRequest(null)).toString();
        assertNotNull(response);
        assert(response).contains("\"loadCondition\": null");
    }
}

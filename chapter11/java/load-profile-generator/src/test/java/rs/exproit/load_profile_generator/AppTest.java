package rs.exproit.load_profile_generator;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class AppTest {
    private static final String TEST_DATA_FILE ="dummy.avro";
    final App app;
    
    public AppTest() throws IOException {
        app = new App(new LoadProfileRPCImpl(TEST_DATA_FILE));
    }
    
    @Test
    public void startCheckPortAndStopTheService() throws IOException {
        app.start(65111);
        assertEquals(65111, app.port());
        app.stop();
    }
    
    @Test(expected = IllegalStateException.class)
    public void tryingToGetThePortNumberForANonRunningService() {
        app.port();
    }
}

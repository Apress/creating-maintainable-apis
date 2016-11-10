package rs.exproit.load_profile_generator;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class SmartMeterAppTest {
    final App app;
    
    public SmartMeterAppTest() throws IOException {
        app = new App();
    }
    
    @Test
    public void startCheckPortAndStopTheService() throws IOException {
        app.start(65111);
        assertEquals(65111, app.port());
        app.stop();
    }
    
    @Test(expected=IllegalStateException.class)
    public void tryingToGetThePortNumberForANonRunningService() {
        app.port();
    }
}

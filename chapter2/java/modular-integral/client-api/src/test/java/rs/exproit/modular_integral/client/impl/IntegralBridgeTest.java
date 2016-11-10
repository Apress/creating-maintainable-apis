package rs.exproit.modular_integral.client.impl;

import org.junit.Test;

import rs.exproit.modular_integral.client.impl.IntegralClientBridge;

public class IntegralBridgeTest {
    private final IntegralClientBridge client = new IntegralClientBridge();
    
    @Test(expected = IllegalArgumentException.class)
    public final void calculateWithoutFunction() {
        client.directSummation(null, 1.0, 2.0, 0.1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public final void calculateWithWrongDx() {
        client.directSummation(x -> 0.0, 1.0, 2.0, -0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void calculateWithWrongInterval() {
        client.directSummation(x -> 0.0, 2.0, 1.0, 0.1);
    }

    @Test(expected = IllegalStateException.class)
    public final void calculateWithoutProvider() {
        client.directSummation(x -> 0.0, 1.0, 2.0, 0.1);
    }
}

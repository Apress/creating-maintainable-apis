package rs.exproit.modular_integral.provider.impl.directsummation;

import static org.junit.Assert.*;

import org.junit.Test;

import rs.exproit.modular_integral.provider.api.DirectSummation;

public class DirectSummationProviderTest {
    private final DirectSummation portfolio = new DirectSummation(0.1);
    private final DirectSummationProvider provider = new DirectSummationProvider();
    
    @Test(expected = IllegalArgumentException.class)
    public final void calculateWithoutFunction() {
        provider.calculate(null, 1.0, 2.0, portfolio);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public final void calculateWithoutPortfolio() {
        provider.calculate(x -> 0.0, 1.0, 2.0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void calculateWithWrongInterval() {
        provider.calculate(x -> 0.0, 2.0, 1.0, portfolio);
    }

    @Test
    public final void calculate() {
        assertEquals(1.0, provider.calculate(x -> 0.0, 1.0, 2.0, portfolio), 0.1);
    }

    @Test
    public final void getPortfolioType() {
        assertTrue(DirectSummation.class.isAssignableFrom(provider.getPortfolioType()));
    }
}

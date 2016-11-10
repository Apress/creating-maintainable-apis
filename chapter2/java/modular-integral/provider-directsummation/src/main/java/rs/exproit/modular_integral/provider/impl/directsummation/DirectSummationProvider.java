package rs.exproit.modular_integral.provider.impl.directsummation;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

import rs.exproit.modular_integral.provider.api.DirectSummation;
import rs.exproit.modular_integral.provider.api.IntegralPortfolio;
import rs.exproit.modular_integral.provider.api.IntegralSPI;

/**
 * A concrete implementation of the Direct Summation provider.
 * 
 * @author Ervin Varga
 * @since 1.0
 */
@Component
public final class DirectSummationProvider implements IntegralSPI {
    @Override
    public double calculate(Function<Double, Double> f, double a, double b,
            IntegralPortfolio spec) {
        if (b <= a || spec == null || f == null) {
            throw new IllegalArgumentException("Invalid input arguments");
        }
        
        // Dummy implementation, you may want to replace this with real one.
        final DirectSummation ds = (DirectSummation) spec;
        System.out.println("Received: a=" + a + ", b=" + b + ", dx=" + ds.getDx());
        return 1.0;
    }

    @Override
    public Class<? extends IntegralPortfolio> getPortfolioType() {
        return DirectSummation.class;
    }
}

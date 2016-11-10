package rs.exproit.modular_integral.client.impl;

import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import rs.exproit.modular_integral.client.api.IntegralClient;
import rs.exproit.modular_integral.provider.api.DirectSummation;
import rs.exproit.modular_integral.provider.api.IntegralPortfolio;
import rs.exproit.modular_integral.provider.api.IntegralSPI;

/**
 * Implements the API for clients, and serves as a bridge toward provider API.
 * It tracks provider services, and allows clients to call into them.
 * 
 * @author Ervin Varga
 * @since 1.0
 */
@Component
public final class IntegralClientBridge implements IntegralClient {
    /**
     * List of service objects.
     *
     * This field is managed by the Felix SCR and updated
     * with the current set of available integral provider services.
     * At least one integral provider service is required.
     */
    @Reference(policy=ReferencePolicy.DYNAMIC, cardinality=ReferenceCardinality.AT_LEAST_ONE)
    private volatile List<IntegralSPI> providerList;

    @Override
    public double directSummation(Function<Double,Double> f, double a, double b, double dx) {
        if (b <= a || dx <= 0.0 || f == null) {
            throw new IllegalArgumentException("Invalid input arguments");
        }
        
        final IntegralSPI provider = findProvider(DirectSummation.class);
        if (provider != null) {
            return provider.calculate(f, a, b, new DirectSummation(dx));
        } else {
            throw new IllegalStateException("Cannot find a provider for this method");
        }
    }
    
    /**
     * Searches active providers to find the desired one.
     * 
     * @param portfolioType the class of the target portfolio type.
     * @return a reference to the proper provider, or {@code null}.
     */
    private IntegralSPI findProvider(Class<? extends IntegralPortfolio> portfolioType) {
        // Put the current set of services in a local field, as the field providerList 
        // might be modified concurrently.
        final List<IntegralSPI> providerListCopy = providerList;
        
        if (providerListCopy != null) {
            for (IntegralSPI provider : providerListCopy) {
                if (provider.getPortfolioType().isAssignableFrom(portfolioType)) {
                    return provider;
                }
            }
        }
        return null;
    }
}

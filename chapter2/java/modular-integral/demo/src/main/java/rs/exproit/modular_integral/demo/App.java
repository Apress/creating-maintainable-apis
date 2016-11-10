package rs.exproit.modular_integral.demo;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import rs.exproit.modular_integral.client.api.IntegralClient;

/**
 * A very simple demo showing how to use the client API.
 *
 * @author Ervin Varga
 * @since 1.0
 */
@Component
public final class App {
    @Reference
    private volatile IntegralClient client;
    
    // You should not follow this rather trivial approach to run your
    // logic from the activate method.
    @Activate
    void calculateIntegral() {
        try {
            System.out.println("Calculating integral: " + client.directSummation(x -> 1.0, 1.0, 2.0, 0.1));
        } catch (IllegalStateException ex) {
            System.err.println("The requested provider isn't active.");
        }
    }
}

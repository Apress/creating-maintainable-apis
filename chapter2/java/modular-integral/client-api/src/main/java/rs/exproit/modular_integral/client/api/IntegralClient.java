package rs.exproit.modular_integral.client.api;

import java.util.function.Function;

/**
 * This is the client API consumed by users of this project.
 * 
 * @author Ervin Varga
 * @since 1.0
 */
public interface IntegralClient {
    /**
     * Calculates the definite integral of a function between the limits a and b 
     * using the direct summation method.
     * 
     * @param f the integrand.
     * @param a the lower bound of the limit.
     * @param b the upper bound of the limit.
     * @param dx a small step size for iterating over the specified interval.
     * @return the numerical approximation of the definite integral.
     * @throws IllegalArgumentException if b <= a or dx <= 0 or f is null.
     * @throws IllegalStateException if there is no provider implementing this method.
     */
    double directSummation(Function<Double,Double> f, double a, double b, double dx);
}
package rs.exproit.modular_integral.provider.api;

import java.util.function.Function;

/**
 * Provider API for calculating an integral of a function.
 * 
 * @author Ervin Varga
 * @since 1.0
 */
public interface IntegralSPI {
    /**
     * Calculates the definite integral of a function between the limits a and b.
     * 
     * @param f the integrand.
     * @param a the lower bound of the limit.
     * @param b the upper bound of the limit.
     * @param spec the algorithm and its associated parameters.
     * @return the numerical approximation of the definite integral.
     * @throws IllegalArgumentException if b <= a or f is null or spec is null. 
     */
    double calculate(Function<Double,Double> f, double a, double b, 
                     IntegralPortfolio spec);
    
    /**
     * Returns back the type of portfolio accepted by this provider.
     * 
     * @return the Class denoting the type of the accepted portfolio.
     */
    Class<? extends IntegralPortfolio> getPortfolioType();
}
package rs.exproit.modular_integral.provider.api;

/**
 * The portfolio for the Direct Summation method.
 * 
 * @author Ervin Varga
 * @since 1.0
 */
public final class DirectSummation implements IntegralPortfolio {
    private final double dx;
    
    /**
     * Creates a new immutable instance of this class.
     * 
     * @param dx a small step size for iterating over the specified interval.
     * @throws IllegalArgumentException if dx <= 0.      
     */
    public DirectSummation(double dx) {
        if (dx <= 0) {
            throw new IllegalArgumentException(
                    "The step size must be greater than zero.");
        }
        
        this.dx = dx;
    }
    
    public double getDx() {
        return dx;
    }
}

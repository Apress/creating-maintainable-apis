/**
 * The portfolio for the Direct Summation method.
 */
public final class DirectSummation extends IntegralPortfolio {
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

package rs.exproit.modular_integral.provider.api;

/**
 * The portfolio for the Romberg's Method.
 * 
 * @author Ervin Varga
 * @since 1.0
 */
public final class RombergsMethod implements IntegralPortfolio {
    private final int n;
    private final int m;
    
    /**
     * Creates a new immutable instance of this class.
     * 
     * @param n a positive integer representing the first dimension in the formula.
     * @param m a positive integer representing the second dimension in the formula.
     * @throws IllegalArgumentException if n <= 0 or m <= 0.
     */
    public RombergsMethod(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException(
                    "The dimensions must be positive integers.");
        }
        
        this.n = n;
        this.m = m;
    }
    
    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
}

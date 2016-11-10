/**
 * The portfolio for the Simpson's Rule method.
 */
public final class SimpsonsRule extends IntegralPortfolio {
    private final int n;
    
    /**
     * Creates a new immutable instance of this class.
     * 
     * @param n an even positive integer representing the number of summands.
     * @throws IllegalArgumentException if n <= 0 or not even.
     */
    public SimpsonsRule(int n) {
        if (n <= 0 || (n % 2 != 0)) {
            throw new IllegalArgumentException(
                    "The number of summands must be a positive even integer.");
        }
        
        this.n = n;
    }
    
    public int getN() {
        return n;
    }
}

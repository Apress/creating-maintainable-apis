package rs.exproit.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import rs.exproit.util.RandomizedBag;

/**
 * This client application reads in k unique strings from a command line (forming a set), 
 * and prints out k subsets of them in a random order.
 * 
 * @author Ervin Varga
 * @since 1.0
 */
public final class ClientApplication {
    /**
     * This is an API to build the output, which is based on the Builder pattern.
     * 
     * @param <T> the type of output to generate.
     */
    public interface OutputBuilder<T> {
        void startOutput();
        void addContent(Set<String> subset);
        T finalizeOutput();
    }
    
    private static final class ConsoleOutputBuilder implements OutputBuilder<String> {
        private final StringBuilder buffer = new StringBuilder();

        @Override
        public void startOutput() {
            buffer.append("Selected subsets of an input set in random order:\n");
        }

        @Override
        public void addContent(Set<String> subset) {
            buffer.append(subset.toString());
            buffer.append('\n');
        }

        @Override
        public String finalizeOutput() {
            return buffer.toString();
        }        
    };
    
    private static Class<? extends OutputBuilder<?>> activeOutputBuilder = ConsoleOutputBuilder.class;
    private static final ClientApplication app = new ClientApplication();
    
    /**
     * Sets the active output builder for this application.
     * 
     * @param newOutputBuilder a class definition of the new output builder.
     */
    public static <T> void setOutputBuilder(Class<? extends OutputBuilder<T>> newOutputBuilder) {
        activeOutputBuilder = newOutputBuilder;
    }
    
    private Set<Set<String>> producePowerSet(String[] inputSeq) {
        assert inputSeq != null && !(inputSeq.length == 0);
        Set<String> inputSet = new HashSet<>(Arrays.asList(inputSeq));
        return new PowerSet(inputSet).subsets();
    }
    
    private RandomizedBag<Set<String>> randomizePowerSet(Set<Set<String>> powerSet) {
        assert powerSet != null;        
        return new RandomizedBag<Set<String>>(powerSet);
    }
    
    @SuppressWarnings("unchecked")
    private String generateConsoleOutput(String[] inputSeq) {
        assert inputSeq != null && !(inputSeq.length == 0);        
        RandomizedBag<Set<String>> rndPowerSet = randomizePowerSet(producePowerSet(inputSeq));
        try {
            OutputBuilder<String> output = (OutputBuilder<String>) activeOutputBuilder.newInstance();
            output.startOutput();
            for (int i = 0; i < inputSeq.length; i++) {
                output.addContent(rndPowerSet.remove());
            }
            return output.finalizeOutput();
        } catch (Exception e) {
            return e.toString();
        }
    }
    
    /**
     * Main entry point of this client application.
     * 
     * @param args the strings forming an input set. They should be separated by space.
     * @throws NullPointerException if the input parameter is {@code null}.
     * @throws IllegalArgumentException if the input argument list is empty.
     */
    public static void main(String[] args) {
        if (args == null) {
            throw new NullPointerException("The input argument cannot be null");
        }
        
        if (args.length == 0) {
            throw new IllegalArgumentException("The input argument list cannot be empty");
        }
        
        System.out.println(app.generateConsoleOutput(args));
    }
}

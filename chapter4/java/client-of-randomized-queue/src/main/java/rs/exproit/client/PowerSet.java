package rs.exproit.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class PowerSet implements Iterable<Set<String>> {
    final static Set<String> EMPTY_SET = new HashSet<String>();

    private final Set<Set<String>> powerSet;
    
    public PowerSet(Set<String> inputSet) {
        powerSet = generateSubsets(inputSet);
    }
    
    private Set<Set<String>> generateSubsets(Set<String> set) {
        assert set != null;
        
        if (set.isEmpty()) {
            Set<Set<String>> emptySubset = new HashSet<>();
            emptySubset.add(EMPTY_SET);
            return emptySubset;
        } else {
            String firstElement = getFirstElement(set);
            Set<String> remainingElements = getRemainingElements(set);
            Set<Set<String>> smallerSubsets = generateSubsets(remainingElements);

            Set<Set<String>> subsets = new HashSet<>();
            subsets.addAll(smallerSubsets);
            for (Set<String> s : smallerSubsets) {
                Set<String> currentSubset = new HashSet<String>();
                currentSubset.addAll(s);
                currentSubset.add(firstElement);
                subsets.add(currentSubset);
            }
            return subsets;
        }
    }
    
    private String getFirstElement(Set<String> set) {
        return set.iterator().next();
    }
    
    private Set<String> getRemainingElements(Set<String> set) {
        assert !set.isEmpty();
        
        Set<String> remainingElements = new HashSet<>(set);
        Iterator<String> iter = remainingElements.iterator();
        iter.next();
        iter.remove();
        return remainingElements;
    }

    @Override
    public Iterator<Set<String>> iterator() {
        return powerSet.iterator();
    }
    
    /**
     * Returns the power set created during construction of this instance.
     * 
     * @return the generated power set.
     */
    // NOTE: This is not a classical getter, as the power set is an output of this class.
    public Set<Set<String>> subsets() {
        return Collections.unmodifiableSet(powerSet);
    }
}

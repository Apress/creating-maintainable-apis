package rs.exproit.client;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class PowerSetTest {
    private final HashSet<Set<String>> expectedPowerSet = new HashSet<>();    
    private final String[] inputSet = new String[] { "A", "B", "C" };
    
    @Before
    public void fillInTheExpectedSubsets() {
        expectedPowerSet.add(PowerSet.EMPTY_SET);    
        expectedPowerSet.add(new HashSet<String>(Arrays.asList("A")));
        expectedPowerSet.add(new HashSet<String>(Arrays.asList("B")));
        expectedPowerSet.add(new HashSet<String>(Arrays.asList("C")));
        expectedPowerSet.add(new HashSet<String>(Arrays.asList("A", "B")));
        expectedPowerSet.add(new HashSet<String>(Arrays.asList("A", "C")));
        expectedPowerSet.add(new HashSet<String>(Arrays.asList("B", "C")));
        expectedPowerSet.add(new HashSet<String>(Arrays.asList("A", "B", "C")));
    }
    
    @Test
    public void generateAllSubsetsOfAnEmptySet() {
        PowerSet subset = new PowerSet(new HashSet<String>());
        Iterator<Set<String>> subsetIter = subset.iterator();
        assertEquals(PowerSet.EMPTY_SET, subsetIter.next());
        assertFalse(subsetIter.hasNext());
    }

    @Test
    public void generateAllSubsetsOfANonEmptySet() {
        PowerSet powerSet = new PowerSet(new HashSet<String>(Arrays.asList(inputSet)));
        Iterator<Set<String>> subsetIter = powerSet.iterator();        
        while (subsetIter.hasNext()) {
            Set<String> currentSubset = subsetIter.next();
            assertTrue(expectedPowerSet.contains(currentSubset));
            subsetIter.remove();
            expectedPowerSet.remove(currentSubset);
        }
    }
    
    @Test
    public void retrieveThePowerSet() {
        PowerSet powerSet = new PowerSet(new HashSet<String>(Arrays.asList(inputSet)));
        assertTrue(powerSet.subsets().containsAll(expectedPowerSet));
    }
}

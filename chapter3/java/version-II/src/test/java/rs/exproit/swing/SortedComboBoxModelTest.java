package rs.exproit.swing;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class SortedComboBoxModelTest {
    private final static String[] testItems = new String[] {"A", "Y", "X", "D", "C"};
    private final static String[] testItemsCopy = testItems.clone();

    @Before
    public void assureTestItemsAreIntact() {
        assertArrayEquals("Original array modified in some of the tests", 
                testItemsCopy, testItems);
    }
    
    @Test
    public final void testNoArgConstructor() {
        final SortedComboBoxModel<String> cbox = new SortedComboBoxModel<>();
        assertNotNull(cbox);
    }

    @Test
    public final void testConstructorWithArray() {
        final SortedComboBoxModel<String> cbox = new SortedComboBoxModel<>(testItems);
        
        verifySortedOrder(cbox);
    }

    @Test
    public final void testConstructorWithVector() {
        final Vector<String> items = new Vector<>();
        items.addAll(Arrays.asList(testItems));
        final SortedComboBoxModel<String> cbox = new SortedComboBoxModel<>(items);
        
        verifySortedOrder(cbox);
    }

    @Test
    public final void testAddingItems() {
        final SortedComboBoxModel<String> cbox = new SortedComboBoxModel<>();
        for (String item : testItems) {
            cbox.addElement(item);
        }

        verifySortedOrder(cbox);
    }
    
    @Test
    public final void testInsertingItems() {
        final SortedComboBoxModel<String> cbox = new SortedComboBoxModel<>();
        for (String item : testItems) {
            cbox.insertElementAt(item, 0);
        }

        verifySortedOrder(cbox);
    }

    private void verifySortedOrder(SortedComboBoxModel<String> cbox) {
        assertEquals("A", cbox.getElementAt(0));
        assertEquals("C", cbox.getElementAt(1));
        assertEquals("D", cbox.getElementAt(2));
        assertEquals("X", cbox.getElementAt(3));
        assertEquals("Y", cbox.getElementAt(4));
    }
}

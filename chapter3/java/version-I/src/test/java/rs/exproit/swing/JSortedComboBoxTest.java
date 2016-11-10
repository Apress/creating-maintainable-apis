package rs.exproit.swing;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import org.junit.Before;
import org.junit.Test;

public class JSortedComboBoxTest {
    private final static String[] testItems = new String[] {"A", "Y", "X", "D", "C"};
    private final static String[] testItemsCopy = testItems.clone();

    @Before
    public void assureTestItemsAreIntact() {
        assertArrayEquals("Original array modified in some of the tests", 
                testItemsCopy, testItems);
    }
    
    @Test
    public final void testNoArgConstructor() {
        final JSortedComboBox<String> cbox = new JSortedComboBox<>();
        assertNotNull(cbox);
    }

    @Test
    public final void testConstructorWithModel() {
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(testItems);
        final JSortedComboBox<String> cbox = new JSortedComboBox<>(model);
        
        verifySortedOrder(cbox);
    }
    
    @Test
    public final void testConstructorWithArray() {
        final JSortedComboBox<String> cbox = new JSortedComboBox<>(testItems);
        
        verifySortedOrder(cbox);
    }

    @Test
    public final void testConstructorWithVector() {
        final Vector<String> items = new Vector<>();
        items.addAll(Arrays.asList(testItems));
        final JSortedComboBox<String> cbox = new JSortedComboBox<>(items);
        
        verifySortedOrder(cbox);
    }

    @Test
    public final void testAddingItems() {
        final JSortedComboBox<String> cbox = new JSortedComboBox<>();
        for (String item : testItems) {
            cbox.addItem(item);
        }

        verifySortedOrder(cbox);
    }
    
    @Test
    public final void testInsertingItems() {
        final JSortedComboBox<String> cbox = new JSortedComboBox<>();
        for (String item : testItems) {
            cbox.insertItemAt(item, 0);
        }

        verifySortedOrder(cbox);
    }

    private void verifySortedOrder(JSortedComboBox<String> cbox) {
        assertEquals("A", cbox.getItemAt(0));
        assertEquals("C", cbox.getItemAt(1));
        assertEquals("D", cbox.getItemAt(2));
        assertEquals("X", cbox.getItemAt(3));
        assertEquals("Y", cbox.getItemAt(4));
    }
}

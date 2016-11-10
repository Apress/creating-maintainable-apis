package rs.exproit.swing;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class SortedComboBoxModel<E extends Comparable<E>> extends DefaultComboBoxModel<E> {
    public SortedComboBoxModel() {}
    
    public SortedComboBoxModel(E[] items) {
        super(sort(items.clone()));
    }

    @SuppressWarnings("unchecked")
    public SortedComboBoxModel(Vector<E> items) {
        super(sort((Vector<E>) items.clone()));
    }
    
    /**
     * Adds an item to this combo box model, while keeping its sorted order.
     * 
     * @param item the item to be added to this combo box model.
     */
    @Override
    public void addElement(E item) {
        final int insertionPoint = binarySearch(item);
        
        if (insertionPoint >= getSize()) {   
            super.addElement(item);
        } else {   
            super.insertElementAt(item, insertionPoint);
        }        
    }
    
    private int binarySearch(E item) {
        int low = 0;
        int high = getSize() - 1;
        int insertionPoint = -1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            E midVal = getElementAt(mid);

            if (midVal.compareTo(item) < 0) {
                low = mid + 1;
            } else if (midVal.compareTo(item) > 0) {
                high = mid - 1;
            } else {
                insertionPoint = mid;
                break;
            }
        }
        
        if (insertionPoint == -1) {
            insertionPoint = low;
        }        
        return insertionPoint;
    }

    /**
     * Adds an item to this combo box model, while keeping its sorted order.
     * 
     * @param item the item to be added to this combo box model.
     * @param index this is ignored, as it is meaningless.
     */    
    @Override
    public void insertElementAt(E item, int index) {
        addElement(item);
    }
        
    private static <E extends Comparable<E>> E[] sort(E[] items) {
        Arrays.sort(items);
        return items;
    }

    private static <E extends Comparable<E>> Vector<E> sort(Vector<E> items) {
        Collections.sort(items);
        return items;
    }
}

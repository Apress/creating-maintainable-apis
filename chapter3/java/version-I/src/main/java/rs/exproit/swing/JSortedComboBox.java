package rs.exproit.swing;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class JSortedComboBox<E extends Comparable<E>> extends JComboBox<E> {
    public JSortedComboBox() {}
    
    public JSortedComboBox(ComboBoxModel<E> aModel) {
        this(toVector(aModel));
    }
    
    public JSortedComboBox(E[] items) {
        super(sort(items.clone()));
    }

    @SuppressWarnings("unchecked")
    public JSortedComboBox(Vector<E> items) {
        super(sort((Vector<E>) items.clone()));
    }
    
    /**
     * Adds an item to this combo box, while keeping its sorted order.
     * 
     * @param item the item to be added to this combo box.
     */
    @Override
    public void addItem(E item) {
        final int insertionPoint = binarySearch(item);
        
        if (insertionPoint >= getItemCount()) {   
            super.addItem(item);
        } else {   
            super.insertItemAt(item, insertionPoint);
        }        
    }
    
    private int binarySearch(E item) {
        int low = 0;
        int high = getItemCount() - 1;
        int insertionPoint = -1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            E midVal = getItemAt(mid);

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
     * Adds an item to this combo box, while keeping its sorted order.
     * 
     * @param item the item to be added to this combo box.
     * @param index this is ignored, as it is meaningless.
     */    
    @Override
    public void insertItemAt(E item, int index) {
        addItem(item);
    }
        
    private static <E extends Comparable<E>> Vector<E> toVector(ComboBoxModel<E> aModel) {
        final Vector<E> items = new Vector<>(aModel.getSize());

        for (int i = 0; i < aModel.getSize(); i++) {
            items.add(aModel.getElementAt(i));
        }
        return items;
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

package rs.exproit.swing;

import javax.swing.ComboBoxModel;

/**
 * A sorted combo box model, which is assumed to be mutable.
 */
public interface SortedComboBoxModel<E> extends ComboBoxModel<E> {
    /**
     * Adds an item to the model. The implementation of this method should notify all 
     * registered ListDataListeners that the element has been added.
     * 
     * @param element the element to be added.
     */
    void addElement(E element);
    
    /**
     * Removes an element from the model. The implementation of this method should should 
     * notify all registered ListDataListeners that the element has been removed.
     * 
     * @param element the element to be removed.
     */
    void removeElement(E element);
    
    /**
     * Removes an element at a given index. The implementation of this method should 
     * notify all registered ListDataListeners that the element has been removed.
     * 
     * @param index the location of the element to be removed.
     */
    void removeElementAt(int index);
}

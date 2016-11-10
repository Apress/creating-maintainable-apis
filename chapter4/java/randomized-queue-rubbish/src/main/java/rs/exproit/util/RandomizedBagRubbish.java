package rs.exproit.util;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@SuppressWarnings("serial")
public final class RandomizedBagRubbish<E> extends AbstractCollection<E> 
        implements Serializable {
    private final List<E> elems = new ArrayList<>();

    public RandomizedBagRubbish() { 
    }

    public RandomizedBagRubbish(long seed) {
    }
    
    public RandomizedBagRubbish(Collection<E> c) {
        elems.addAll(c);
    }
    
    public RandomizedBagRubbish(long seed, Collection<E> c) {
        this(c);
        Collections.shuffle(elems, new Random(seed));
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }

        elems.add(e);
        return true;
    }

    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove an element from an empty bag.");
        }

        return elems.remove(0);
    }

    public Iterator<E> iterator() {
        return elems.listIterator(); 
    }

    @Override
    public int size() {
        return elems.size();
    }
}
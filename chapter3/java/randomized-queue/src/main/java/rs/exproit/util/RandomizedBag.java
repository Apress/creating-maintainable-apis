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

/**
 * Implements the generic randomized "queue" ADT as described at
 * {@link http://www.cs.princeton.edu/courses/archive/fall05/cos226/assignments/queues.html}.
 * The randomization process uses a linear congruential random number generator. This may be
 * configured with a seed value, to reproduce the random number sequence.

 * @author Ervin Varga
 *
 * @param <E> the type of elements contained inside this bag.
 */
public final class RandomizedBag<E> extends AbstractCollection<E> 
        implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final List<E> elems = new ArrayList<>();
    private final Random rnd;

    public RandomizedBag() { 
        rnd = new Random();
    }

    public RandomizedBag(long seed) {
        rnd = new Random(seed);
    }
    
    public RandomizedBag(Collection<E> c) {
        this();
        addAndshuffleElementsInPlace(c);
    }
    
    public RandomizedBag(long seed, Collection<E> c) {
        this(seed);
        addAndshuffleElementsInPlace(c);
    }

    private void addAndshuffleElementsInPlace(Collection<E> newElems) {
        assert newElems != null;

        elems.addAll(newElems);
        Collections.shuffle(elems, rnd);
    }
    
    /**
     * Adds a new element to this collection. The collection permits duplicates, but doesn't
     * allow {@code null} elements.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }

        elems.add(e);

        // Shuffle the last added element with a random one from the queue.
        final int idx = rnd.nextInt(elems.size());
        elems.set(elems.size() - 1, elems.get(idx));
        elems.set(idx, e);
        return true;
    }

    /**
     * Deletes and returns an element from the bag, uniformly at random.
     * 
     * @return the deleted element from this bag.
     * @throws NoSuchElementException if the bag is empty.
     */
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove an element from an empty bag.");
        }

        return elems.remove(0);
    }

    public Iterator<E> iterator() {
        List<E> elemsCopy = new ArrayList<>(elems);
        Collections.shuffle(elemsCopy, rnd);
        return elemsCopy.listIterator(); 
    }

    @Override
    public int size() {
        return elems.size();
    }
}
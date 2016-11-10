package rs.exproit.util;

import java.io.*;
import java.util.*;

/**
 * Implements the generic randomized "queue" ADT as described 
 * <a href="http://www.cs.princeton.edu/courses/archive/fall05/cos226/assignments/queues.html">here</a>.
 * The randomization process uses a linear congruential random number generator. This may be
 * configured with a seed value, to reproduce the random number sequence.
 * 
 * All operations are executed approximatively in a constant amortized time (remove requires a linear time).
 *
 * @author Ervin Varga
 *
 * @param <E> the type of elements contained inside this bag.
 */
public final class RandomizedBag<E> extends AbstractCollection<E> 
        implements Externalizable {
    private static final String serialVersion = "1.0";
    
    private final List<E> elems = new ArrayList<>();
    private Random rnd;

    public RandomizedBag() { 
        rnd = new Random();
    }

    public RandomizedBag(long seed) {
        rnd = new Random(seed);
    }
    
    public RandomizedBag(Collection<E> c) {
        this();
        if (c == null) {
            throw new NullPointerException();
        }
        addAndshuffleElementsInPlace(c);
    }
    
    public RandomizedBag(long seed, Collection<E> c) {
        this(seed);
        if (c == null) {
            throw new NullPointerException();
        }
        addAndshuffleElementsInPlace(c);
    }

    private void addAndshuffleElementsInPlace(Collection<E> newElems) {
        assert newElems != null;
        addAll(newElems);
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
        return elems.listIterator();
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(elems, 
                Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.NONNULL); 
    }

    @Override
    public int size() {
        return elems.size();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(serialVersion);
        // Save the whole state of our random number generator (peeking into it via
        // reflection to get the seed isn't advised at all).
        out.writeObject(rnd);
        // Write out all the contained items.
        out.writeInt(size());
        for (E item : this) {
            out.writeObject(item);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        // Currently, we ignore the serial version number, but in future it will be important.
        in.readUTF();
        // Restore the random number generator.
        rnd = (Random) in.readObject();
        // Read in all the saved items.
        int numItems = in.readInt();
        for (int i = 0; i < numItems; i++) {
            Object item = in.readObject();
            elems.add((E) item);
        }
    }
}
package rs.exproit.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("serial")
public class RandomizedQueue<E> extends LinkedList<E> {
    private Random rnd;

    public RandomizedQueue() {
       rnd = createRandom();
    }

    public RandomizedQueue(Collection<E> c) {
        super(c);
        rnd = createRandom();
        Collections.shuffle(this, rnd);
    }

    public Random createRandom() {
        return new Random();
    }
    
    public void setRandom(Random rnd) {
        this.rnd = rnd;
    }
}
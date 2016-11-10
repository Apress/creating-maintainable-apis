package rs.exproit.util;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class RandomizedQueueTest {
    @SuppressWarnings("serial")
    private static class MyRandomizedQueue extends RandomizedQueue<Integer> {
        public MyRandomizedQueue() {}

        public MyRandomizedQueue(Collection<Integer> c) {
            super(c);
        }

        @Override
        public Random createRandom() {
            return new Random(1L);
        }
    };
    
    private static final List<Integer> baseItems = new LinkedList<>();
    private static final List<Integer> shuffledBaseItems = new LinkedList<>();
    private static final Random rnd = new Random(1L);
    
    @BeforeClass
    public static void setupBaseItems() {
        for (int i = 0; i < 10; i++) {
            baseItems.add(i);
            shuffledBaseItems.add(i);
        }
        Collections.shuffle(shuffledBaseItems, rnd);
    }
    
    @Test
    public void testConstructorWithoutCollection() {
        RandomizedQueue<Integer> queue = new MyRandomizedQueue();
        assertNotNull(queue);
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testConstructorWithCollection() {
        RandomizedQueue<Integer> queue = new MyRandomizedQueue(baseItems);
        assertNotNull(queue);
        assertEquals(baseItems.size(), queue.size());
        assertArrayEquals(shuffledBaseItems.toArray(), queue.toArray());
    }
}
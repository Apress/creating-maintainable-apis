package rs.exproit.util;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.IsNot.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class RandomizedBagTest {
    private static final List<Integer> baseItems = new ArrayList<>();
    
    @BeforeClass
    public static void setupBaseItems() {
        for (int i = 0; i < 10; i++) {
            baseItems.add(i);
        }
    }
    
    @Test
    public void testConstructorWithoutCollection() {
        RandomizedBagRubbish<Integer> bag = new RandomizedBagRubbish<>();
        assertNotNull(bag);
        assertTrue(bag.isEmpty());
    }

    @Test
    public void testConstructorWithCollection() {
        RandomizedBagRubbish<Integer> bag = new RandomizedBagRubbish<>(baseItems);
        assertNotNull(bag);
        assertEquals(baseItems.size(), bag.size());
    }

    @Test
    public void testConstructorWithCollectionAndSeed() {
        RandomizedBagRubbish<Integer> bag1 = new RandomizedBagRubbish<>(1, baseItems);
        RandomizedBagRubbish<Integer> bag2 = new RandomizedBagRubbish<>(1, baseItems);
        Object[] bag1Content = bag1.toArray();
        Object[] bag2Content = bag2.toArray();    
        assertArrayEquals(bag1Content, bag2Content);

        bag1 = new RandomizedBagRubbish<>(1, baseItems);
        bag1Content = bag1.toArray();
        bag2 = new RandomizedBagRubbish<>(2, baseItems);
        bag2Content = bag2.toArray();
        assertThat(bag1Content, not(equalTo(bag2Content)));
    }

    @Test(expected = NullPointerException.class)
    public void addNull() {
        RandomizedBagRubbish<Integer> bag = new RandomizedBagRubbish<>();
        bag.add(null);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyBag() {
        RandomizedBagRubbish<Integer> bag = new RandomizedBagRubbish<>();
        bag.remove();
    }    
    
    @Test
    public void addElementsAndIterateOverThem() {
        RandomizedBagRubbish<Integer> bag1 = new RandomizedBagRubbish<>(3);
        for (Integer e : baseItems) {
            bag1.add(e);
        }
        Integer[] bag1Content = new Integer[baseItems.size()];
        int idx = 0;
        for (Integer e : bag1) {
            bag1Content[idx++] = e;
        }

        RandomizedBagRubbish<Integer> bag2 = new RandomizedBagRubbish<>(3);
        for (Integer e : baseItems) {
            bag2.add(e);
        }
        Integer[] bag2Content = new Integer[baseItems.size()];
        idx = 0;
        for (Integer e : bag2) {
            bag2Content[idx++] = e;
        }
        assertArrayEquals(bag1Content, bag2Content);
    }
    
    @Test
    public void removeElementRandomly() {
        RandomizedBagRubbish<Integer> bag1 = new RandomizedBagRubbish<>(1, baseItems);
        RandomizedBagRubbish<Integer> bag2 = new RandomizedBagRubbish<>(1, baseItems);
        assertEquals(bag1.remove(), bag2.remove());
       
        bag1 = new RandomizedBagRubbish<>(1, baseItems);
        bag2 = new RandomizedBagRubbish<>(2, baseItems);
        assertNotEquals(bag1.remove(), bag2.remove());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSerialization() throws Exception {
        RandomizedBagRubbish<Integer> bag1 = new RandomizedBagRubbish<>(1, baseItems);
        byte[] bag1Memento;
        try (
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(buffer);
        ) {
            objectStream.writeObject(bag1);
            bag1Memento = buffer.toByteArray();
        }
        
        RandomizedBagRubbish<Integer> bag2;
        try (
            ByteArrayInputStream buffer = new ByteArrayInputStream(bag1Memento);
            ObjectInputStream objectStream = new ObjectInputStream(buffer);
        ) {
            Object obj = objectStream.readObject();
            bag2 = RandomizedBagRubbish.class.cast(obj);
        }
        
        Object[] bag1Content = bag1.toArray();
        Object[] bag2Content = bag2.toArray();    
        assertArrayEquals(bag1Content, bag2Content);        
    }
    
    @Test
    public void verifyThatIteratorHasRemove() {
        RandomizedBagRubbish<Integer> bag1 = new RandomizedBagRubbish<>(baseItems);
        Iterator<Integer> iter = bag1.iterator();
        assertTrue(iter.hasNext());
        assertNotNull(iter.next());
        // This should not throw an exception.
        iter.remove();
    }
}

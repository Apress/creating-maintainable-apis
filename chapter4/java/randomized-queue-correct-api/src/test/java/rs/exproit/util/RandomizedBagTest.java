package rs.exproit.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.*;
import java.util.*;
import org.junit.*;

public class RandomizedBagTest {
    private static final long SEED_ONE = 1L;
    private static final List<Integer> baseItems = new ArrayList<>();
    private static final List<Integer> baseItemsCopy = new ArrayList<>();
    private static final List<Integer> shuffledBaseItemsWithSeedOne = new ArrayList<>();
    
    @BeforeClass
    public static void setupBaseItems() {
        for (int i = 0; i < 10; i++) {
            baseItems.add(i);
            baseItemsCopy.add(i);
        }
        shuffledBaseItemsWithSeedOne.addAll(Arrays.asList(6, 8, 0, 2, 5, 7, 1, 4, 9, 3));
    }
    
    @Before
    public void verifyThatBaseItemsAreIntact() {
        assertEquals("base items should be intact", baseItemsCopy, baseItems);
    }
    
    @Test(expected = NullPointerException.class)
    public void tryToCreateABagWithNullInputCollection() {
        new RandomizedBag<>(null);
    }
    
    @Test(expected = NullPointerException.class)
    public void tryToCreateABagWithNullInputCollectionAndAPredefinedSeed() {
        new RandomizedBag<>(SEED_ONE, null);
    }

    @Test
    public void createAnEmptyBag() {
        RandomizedBag<Integer> bag = new RandomizedBag<>();
        assertNotNull("bag should exist", bag);
        assertTrue("bag should be empty", bag.isEmpty());
    }

    @Test
    public void createAnEmptyBagWithAPredefinedSeed() {
        RandomizedBag<Integer> bag = new RandomizedBag<>(SEED_ONE);
        assertNotNull("bag should exist", bag);
        assertTrue("bag should be empty", bag.isEmpty());
    }
    
    @Test
    public void createANonEmptyBag() {
        RandomizedBag<Integer> bag = new RandomizedBag<>(baseItems);
        assertNotNull("bag should exist", bag);
        assertEquals("bag's size equals collection's size", baseItems.size(), bag.size());
        assertTrue("bag contains all items", bag.containsAll(baseItems));
        assertThat("bag should be shuffled", baseItems.toArray(), is(not(equalTo(bag.toArray()))));
    }

    @Test
    public void createANonEmptyBagWithAPredefinedSeed() {
        RandomizedBag<Integer> bag = new RandomizedBag<>(SEED_ONE, baseItems);
        assertNotNull("bag should exist", bag);
        assertEquals("bag's size equals collection's size", baseItems.size(), bag.size());
        assertThat("bag should be shuffled with a given seed", 
                shuffledBaseItemsWithSeedOne.toArray(), is(equalTo(bag.toArray())));
    }

    @Test(expected = NullPointerException.class)
    public void tryToAddANullItem() {
        RandomizedBag<Integer> bag = new RandomizedBag<>();
        bag.add(null);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void tryToRemoveFromAnEmptyBag() {
        RandomizedBag<Integer> bag = new RandomizedBag<>();
        bag.remove();
    }    
    
    @Test
    public void addItemsToABag() {
        RandomizedBag<Integer> bag = new RandomizedBag<>();
        for (Integer e : baseItems) {
            bag.add(e);
        }
        assertEquals("bag's size equals collection's size", baseItems.size(), bag.size());
        assertTrue("bag contains all items", bag.containsAll(baseItems));
        assertThat("bag should be shuffled", baseItems.toArray(), is(not(equalTo(bag.toArray()))));
    }
    
    @Test
    public void removeItemsFromABag() {
        RandomizedBag<Integer> bag = new RandomizedBag<>(SEED_ONE, baseItems);
        for (Integer e : shuffledBaseItemsWithSeedOne) {
            assertEquals("bag's head should follow the shuffling", e, bag.remove());
        }
    }
    
    @Test
    public void saveAndReadFromAnObjectStreamUsingStandardSerialization() throws Exception {
        RandomizedBag<Integer> originalBag = new RandomizedBag<>(SEED_ONE, baseItems);
        RandomizedBag<Integer> streamBag = readFromStream(saveIntoStream(originalBag));        
        assertArrayEquals("deserialized bag should equal the original", 
                originalBag.toArray(), streamBag.toArray());        
    }
    
    @SuppressWarnings("unchecked")
    private RandomizedBag<Integer> readFromStream(byte[] bagMemento) throws ClassNotFoundException, IOException {
        try (
            ByteArrayInputStream buffer = new ByteArrayInputStream(bagMemento);
            ObjectInputStream objectStream = new ObjectInputStream(buffer);
        ) {
            Object obj = objectStream.readObject();
            return RandomizedBag.class.cast(obj);
        }
    }

    private byte[] saveIntoStream(RandomizedBag<? extends Serializable> bag) throws IOException {
        byte[] bagMemento;
        try (
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(buffer);
        ) {
            objectStream.writeObject(bag);
            bagMemento = buffer.toByteArray();
        }
        return bagMemento;
    }
    
    @Test
    public void iterateOverItemsOfABag() {
        RandomizedBag<Integer> bag = new RandomizedBag<>(SEED_ONE, baseItems);
        Iterator<Integer> bagIterator = bag.iterator();
        
        for (int i = 0; i < shuffledBaseItemsWithSeedOne.size(); i++) {
            assertEquals("bag's iterator should follow the shuffling",
                    shuffledBaseItemsWithSeedOne.get(i), bagIterator.next());
        }
    }
    
    @Test(expected = IllegalStateException.class)
    public void tryToRemoveAnItemFromABagViaItsIteratorWithoutFirstGettingTheNextItem() {
        RandomizedBag<Integer> bag = new RandomizedBag<>();
        Iterator<Integer> bagIterator = bag.iterator();
        bagIterator.remove();
    }

    @Test
    public void removeItemsFromABagViaItsIterator() {
        RandomizedBag<Integer> bag = new RandomizedBag<>(SEED_ONE, baseItems);
        Iterator<Integer> bagIterator = bag.iterator();

        bagIterator.next();
        bagIterator.remove();
        assertEquals("bag's size should be reduced after remove",
                baseItems.size() - 1, bag.size());
        for (int i = 1; i < shuffledBaseItemsWithSeedOne.size(); i++) {
            assertEquals("bag's iterator should follow the shuffling",
                    shuffledBaseItemsWithSeedOne.get(i), bagIterator.next());
        }
    }
    
    @Test
    public void iterateOverItemsOfABagInParallel() {
        RandomizedBag<Integer> bag = new RandomizedBag<>(SEED_ONE, baseItems);
        Spliterator<Integer> spliter = bag.spliterator();
        
        assertNotNull("should have a spliterator", spliter);
        assertTrue("should be SIZED", spliter.hasCharacteristics(Spliterator.SIZED));
        assertTrue("should be SUBSIZED", spliter.hasCharacteristics(Spliterator.SUBSIZED));
        assertTrue("should be NONNULL", spliter.hasCharacteristics(Spliterator.NONNULL));
        assertEquals("should have a proper size estimate", bag.size(), spliter.estimateSize());
        
        Spliterator<Integer> partition = spliter.trySplit();
        assertTrue("partition should be bigger or equal than half size of the original", 
                partition.estimateSize() >= bag.size() / 2);
    }
}

package by.innowise.custom_linked_list;

import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    @Test
    void testAddFirst_toEmptyList() {
        list.addFirst(10);
        assertEquals(1, list.size());
        assertEquals(10, list.getFirst());
        assertEquals(10, list.getLast());
    }

    @Test
    void testAddFirst_toNonEmptyList() {
        list.addFirst(20);
        list.addFirst(10);
        assertEquals(2, list.size());
        assertEquals(10, list.getFirst());
        assertEquals(20, list.getLast());
    }

    @Test
    void testAddLast_toEmptyList() {
        list.addLast(30);
        assertEquals(1, list.size());
        assertEquals(30, list.getFirst());
        assertEquals(30, list.getLast());
    }

    @Test
    void testAddLast_toNonEmptyList() {
        list.addLast(10);
        list.addLast(20);
        assertEquals(2, list.size());
        assertEquals(10, list.getFirst());
        assertEquals(20, list.getLast());
    }

    @Test
    void testAdd_atFirstIndex() {
        list.add(0, 10);
        list.add(0, 5);
        assertEquals(2, list.size());
        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
    }

    @Test
    void testAdd_atLastIndex() {
        list.add(0, 10);
        list.add(1, 20);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void testAdd_atMiddleIndex() {
        list.addLast(10);
        list.addLast(30);
        list.add(1, 20);
        assertEquals(3, list.size());
        assertEquals(20, list.get(1));
    }

    @Test
    void testGetFirst_fromEmptyList() {
        assertThrows(NoSuchElementException.class,
                () -> list.getFirst());
    }

    @Test
    void testGetLast_fromEmptyList() {
        assertThrows(NoSuchElementException.class,
                () -> list.getLast());
    }

    @Test
    void testRemoveFirst_fromNonEmptyList() {
        list.addLast(10);
        list.addLast(20);
        list.removeFirst();
        assertEquals(1, list.size());
        assertEquals(20, list.getFirst());
    }

    @Test
    void testRemoveFirst_fromEmptyList() {
        assertThrows(NoSuchElementException.class,
                () -> list.removeFirst());
    }

    @Test
    void testRemoveLast_fromNonEmptyList() {
        list.addLast(10);
        list.addLast(20);
        list.removeLast();
        assertEquals(1, list.size());
        assertEquals(10, list.getLast());
    }

    @Test
    void testRemoveLast_fromEmptyList() {
        assertThrows(NoSuchElementException.class,
                () -> list.removeLast());
    }

    @Test
    void testRemove_atFirstIndex() {
        list.addLast(10);
        list.addLast(20);
        list.remove(0);
        assertEquals(1, list.size());
        assertEquals(20, list.getFirst());
    }

    @Test
    void testRemove_atLastIndex() {
        list.addLast(10);
        list.addLast(20);
        list.remove(1);
        assertEquals(1, list.size());
        assertEquals(10, list.getLast());
    }

    @Test
    void testRemove_atMiddleIndex() {
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.remove(1);
        assertEquals(2, list.size());
        assertEquals(30, list.get(1));
    }

    @Test
    void testGet_validIndex() {
        list.addLast(10);
        list.addLast(20);
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void testGet_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(-1));
    }

    @Test
    void testGet_indexOutOfBounds() {
        list.addLast(10);
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(1));
    }

    @Test
    void testSize_afterMultipleOperations() {
        assertEquals(0, list.size());
        list.addFirst(10);
        assertEquals(1, list.size());
        list.addLast(20);
        assertEquals(2, list.size());
        list.removeFirst();
        assertEquals(1, list.size());
        list.removeLast();
        assertEquals(0, list.size());
    }
}

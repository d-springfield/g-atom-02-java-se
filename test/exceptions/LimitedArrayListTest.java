package exceptions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LimitedArrayListTest {

    @Test
    void defaultConstructorTest() {
        assertDoesNotThrow(() -> new LimitedArrayList());
    }

    @Test
    void capacityConstructorTest() {
        int maxSize = LimitedArrayList.MAX_POSSIBLE_SIZE;
        assertDoesNotThrow(() -> new LimitedArrayList(0));
        assertDoesNotThrow(() -> new LimitedArrayList(maxSize - 1));
        assertDoesNotThrow(() -> new LimitedArrayList(maxSize));

        assertThrows(TooLargeSizeOfCollection.class, () -> new LimitedArrayList(maxSize + 1));
    }

    @Test
    void collectionConstructorTest() {
        int maxSize = LimitedArrayList.MAX_POSSIBLE_SIZE;
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < maxSize + 1; ++i) {
            list.add(i);
        }

        assertDoesNotThrow(() -> new LimitedArrayList<Integer>(list.subList(0, 1)));
        assertDoesNotThrow(() -> new LimitedArrayList<Integer>(list.subList(0, maxSize - 1)));
        assertDoesNotThrow(() -> new LimitedArrayList<Integer>(list.subList(0, maxSize)));

        assertThrows(TooLargeSizeOfCollection.class, () -> new LimitedArrayList<Integer>(list.subList(0, maxSize + 1)));
    }

    @Test
    void testAdd() {
        int maxSize = LimitedArrayList.MAX_POSSIBLE_SIZE;
        List<Integer> list = new LimitedArrayList<>();

        for (int i = 0; i < maxSize; ++i) {
            int val = i;
            assertDoesNotThrow(() -> list.add(val));
        }

        assertEquals(list.size(), maxSize);
        assertThrows(TooLargeSizeOfCollection.class, () -> list.add(new Random().nextInt()));
        assertEquals(list.size(), maxSize);
    }

    @Test
    void testAddIndex() {
        int maxSize = LimitedArrayList.MAX_POSSIBLE_SIZE;
        List<Integer> list = new LimitedArrayList<>();

        for (int i = 0; i < maxSize; ++i) {
            int val = i;
            assertDoesNotThrow(() -> list.add(val, val));
        }

        assertEquals(list.size(), maxSize);
        assertThrows(TooLargeSizeOfCollection.class, () -> list.add(maxSize, new Random().nextInt()));
        assertThrows(TooLargeSizeOfCollection.class, () -> list.add(maxSize + 1, new Random().nextInt()));
        assertThrows(TooLargeSizeOfCollection.class, () -> list.add(maxSize - 1, new Random().nextInt()));
        assertThrows(TooLargeSizeOfCollection.class, () -> list.add(0, new Random().nextInt()));
        assertEquals(list.size(), maxSize);
    }

    @Test
    void testAddAll() {
        int maxSize = LimitedArrayList.MAX_POSSIBLE_SIZE;
        List<Integer> maxSizeList = new LimitedArrayList<>();
        for (int i = 0; i < maxSize; ++i) {
            int val = i;
            assertDoesNotThrow(() -> maxSizeList.add(val));
        }

        List<Integer> list1 = new LimitedArrayList<>();
        assertDoesNotThrow(() -> list1.addAll(maxSizeList));
        assertThrows(TooLargeSizeOfCollection.class, () -> list1.addAll(Arrays.asList(1)));

        List<Integer> list2 = new LimitedArrayList<>();
        assertDoesNotThrow(() -> list2.addAll(maxSizeList.subList(0, maxSize / 2)));
        assertDoesNotThrow(() -> list2.addAll(maxSizeList.subList(maxSize / 2, maxSize)));
        assertThrows(TooLargeSizeOfCollection.class, () -> list2.addAll(Arrays.asList(1)));


        List<Integer> tempList = new ArrayList<>(maxSizeList);
        tempList.add(1);
        List<Integer> list3 = new LimitedArrayList<>();
        assertThrows(TooLargeSizeOfCollection.class, () -> list3.addAll(tempList));
    }
}
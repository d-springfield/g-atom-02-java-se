package exceptions;

import java.util.ArrayList;
import java.util.Collection;

public class LimitedArrayList<T> extends ArrayList<T> {
    public final static int MAX_POSSIBLE_SIZE = 10;

    public LimitedArrayList(int initialCapacity) {
        super(validateCapacity(initialCapacity));
    }

    public LimitedArrayList() {
        super();
    }

    public LimitedArrayList(Collection<? extends T> c) {
        super(validateCollection(c));
    }

    @Override
    public boolean add(T t) {
        if (size() == MAX_POSSIBLE_SIZE) {
            throw new TooLargeSizeOfCollection();
        }

        return super.add(t);
    }

    @Override
    public void add(int index, T element) {
        if (index >= MAX_POSSIBLE_SIZE || size() == MAX_POSSIBLE_SIZE) {
            throw new TooLargeSizeOfCollection();
        }

        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (size() + c.size() > MAX_POSSIBLE_SIZE) {
            throw new TooLargeSizeOfCollection();
        }
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (size() + c.size() > MAX_POSSIBLE_SIZE) {
            throw new TooLargeSizeOfCollection();
        }
        return super.addAll(index, c);
    }

    private static int validateCapacity(int capacity) {
        if (capacity > MAX_POSSIBLE_SIZE) {
            throw new IllegalArgumentException();
        }
        return capacity;
    }

    private static <T> Collection<? extends T> validateCollection(Collection<? extends T> c) {
        if (c.size() > MAX_POSSIBLE_SIZE) {
            throw new TooLargeSizeOfCollection();
        }
        return c;
    }

    public int getMaxSize() {
        return MAX_POSSIBLE_SIZE;
    }
}

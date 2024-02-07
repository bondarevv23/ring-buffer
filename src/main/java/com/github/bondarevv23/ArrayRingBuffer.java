package com.github.bondarevv23;

public class ArrayRingBuffer<T> implements RingBuffer<T> {
    private final T[] buffer;
    private int leftPtr = 0;
    private int rightPtr = 1;
    private boolean full = false;

    @SuppressWarnings("unchecked")
    public ArrayRingBuffer(int size) {
        if (size <= 1) {
            throw new IllegalArgumentException("not allowed size of buffer");
        }
        this.buffer = (T[]) new Object[size];
    }

    @Override
    public synchronized void put(T element) {
        buffer[prev(rightPtr)] = element;
        rightPtr = next(rightPtr);
        if (full) {
            leftPtr = next(leftPtr);
        }
        if (next(leftPtr) == rightPtr) {
            full = true;
        }
    }

    @Override
    public synchronized T get() {
        if (!full && next(leftPtr) == rightPtr) {
            throw new EmptyBufferException();
        }
        T element = buffer[leftPtr];
        buffer[leftPtr] = null;
        leftPtr = next(leftPtr);
        full = false;
        return element;
    }

    private int next(int ptr) {
        int n = buffer.length - 1;
        return (ptr + 1) % n;
    }

    private int prev(int ptr) {
        int n = buffer.length - 1;
        return (n + ptr - 1) % n;
    }
}

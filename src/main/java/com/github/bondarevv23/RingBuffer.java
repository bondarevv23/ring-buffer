package com.github.bondarevv23;

public interface RingBuffer <T> {
    void put(T element);

    T get();
}

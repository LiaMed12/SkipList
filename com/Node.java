package com;

public class Node<T extends Comparable<T>> {
    private T value;
    Node<T> left, right, up, down;

    Node(T value) {
        this.value = value;
        left = right = up = down = null;
    }

    public T getValue() {
        return value;
    }

    void setValue(T in) {
        this.value = in;
    }

}

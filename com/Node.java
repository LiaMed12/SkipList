package com;

public class Node<T extends Comparable<T>> {
    private T value;
    public Node left, right, up, down;

    public Node(T value) {
        this.value = value;
        left = right = up = down = null;
    }

    public T getValue() {
        return value;
    }

    public T setValue(T in) {
        return this.value = in;
    }

}

package com;

import java.util.Iterator;

public class SkipListIterator<T extends Comparable<T>> extends SkipList<T> implements Iterator<T> {
     private SkipList<T> list;
     private Node<T> current;

    SkipListIterator(SkipList<T> list) {
        this.list = list;
        this.current = list.valueSearchDownNode(list.getHead());
    }

    @Override
    public boolean hasNext() {
        return current.right.getValue() != null;
    }

    @Override
    public T next() {
       current = current.right;
       return  current.getValue();
    }

    @Override
    public void remove() {
        list.remove(current.getValue());
    }
}

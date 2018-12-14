package com;


import java.util.*;

public class SkipList<T extends Comparable<T>> extends AbstractCollection<T> implements Set<T> {
    private Node head;
    private Node tail;
    private int height, maxNumberOfItems;
    private static final double COIN_FLIP_LIKELIHOOD = 0.5;
    private Node current;

    public Node setCurrent(Node current) {
        return this.current = current;
    }

    public SkipList() {
        current = null;
        Node posInfinity = new Node(null);
        Node negInfinity = new Node(null);

        head = posInfinity;
        tail = negInfinity;

        posInfinity.right = negInfinity;
        negInfinity.left = posInfinity;

        maxNumberOfItems = 0;
        height = 1;

    }

    public Node getHead() {
        return head;
    }

    //Возвращает самый нижний узел
    public Node valueSearchDownNode(Node k) {
        while (true) {
            if (k.down != null) {
                k = k.down;
            } else {
                break;
            }
        }
        return k;
    }

    public Node valueSearch(T k) {
        Node point = head;
        while (true) {
            while (point.right.getValue() != tail.getValue() && point.right.getValue().compareTo(k) <= 0) {
                point = point.right;
            }
            if (point.down != null) {
                point = point.down;
            } else
                break;
        }
        setCurrent(point);
        return point;
    }

    //Вставка дополнительного уровня
    private Node<T> insertAfterAbove(Node p, Node<T> q, T k) {
        Node<T> el = new Node<>(k);

        el.left = p;
        el.right = p.right;
        el.down = q;

        p.right.left = el;
        p.right = el;
        q.up = el;

        return el;
    }


    private T put(T k) {
        Node searchResults = current;
        if (k.equals(searchResults.getValue())) {
            return (T) searchResults.getValue();
        }
        Node<T> insertElement = new Node<>(k);
        insertElement.left = searchResults;
        insertElement.right = searchResults.right;
        searchResults.right.left = insertElement;
        searchResults.right = insertElement;
        int i = 1;

        while (Math.random() < COIN_FLIP_LIKELIHOOD) {
            if (i >= height) {
                height++;

                Node pInf = new Node(null);
                Node nInf = new Node(null);

                pInf.right = nInf;
                pInf.down = head;
                nInf.left = pInf;
                nInf.down = tail;

                head.up = pInf;
                tail.up = nInf;
                head = pInf;
                tail = nInf;
            }
            while (searchResults.up == null) {
                searchResults = searchResults.left;
            }
            searchResults = searchResults.up;
            insertElement = insertAfterAbove(searchResults, insertElement, k);

            i++;

        }
        maxNumberOfItems = maxNumberOfItems + 1;
        return null;
    }

    private void deletion(T k) {
        while (true) {
            Node result = current;
            if (!k.equals(result.getValue())) {
                maxNumberOfItems--;
                return;
            } else {
                result.right.left = result.left;
                result.left.right = result.right;

                result.setValue(null);
                while (result.up != null) {
                    result = result.up;
                    result.down = null;
                    result.right.left = result.left;
                    result.left.right = result.right;

                    result.setValue(null);
                }
            }
        }
    }

    @Override
    public int size() {
        return maxNumberOfItems;
    }

    @Override
    public boolean isEmpty() {
        return (maxNumberOfItems == 0);
    }

    @Override
    public boolean contains(Object o) {
        T e = (T) o;
        Node node = valueSearch(e);
        return node.getValue() != null && node.getValue().equals(e);
    }

    @Override
    public Iterator<T> iterator() {
        return new SkipListIterator<>(this);
    }

    @Override
    public boolean add(T t) {
        if (contains(t)) {
            return false;
        }
        put(t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }
        deletion((T) o);
        return true;
    }
}
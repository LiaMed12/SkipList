package com;


import java.util.*;

public class SkipList<T extends Comparable<T>> extends AbstractCollection<T> implements SortedSet<T> {
    private Node<T> head;
    private Node<T> tail;
    private int height, maxNumberOfItems;
    private static final double COIN_FLIP_LIKELIHOOD = 0.5;
    private Node<T> current;

    private void setCurrent(Node<T> current) {
        this.current = current;
    }

    public SkipList() {
        current = null;
        Node<T> posInfinity = new Node<>(null);
        Node<T> negInfinity = new Node<>(null);

        head = posInfinity;
        tail = negInfinity;

        posInfinity.right = negInfinity;
        negInfinity.left = posInfinity;

        maxNumberOfItems = 0;
        height = 1;

    }

    Node<T> getHead() {
        return head;
    }

    Node<T> getTail() {
        return tail;
    }

    //Возвращает самый нижний узел
    Node<T> valueSearchDownNode(Node<T> k) {
        while (true) {
            if (k.down != null) {
                k = k.down;
            } else {
                break;
            }
        }
        return k;
    }

    public Node<T> valueSearch(T k) {
        Node<T> point = head;
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
    private Node<T> insertAfterAbove(Node<T> p, Node<T> q, T k) {
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
        Node<T> searchResults = current;
        if (k.equals(searchResults.getValue())) {
            return searchResults.getValue();
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

                Node<T> pInf = new Node<>(null);
                Node<T> nInf = new Node<>(null);

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
            Node<T> result = current;
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
        Node<T> node = valueSearch(e);
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

    @Override
    public Comparator<? super T> comparator() {
        throw new NullPointerException();
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SkipListSet(this, fromElement, toElement).subset();
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SkipListSet(this, toElement).headSet();
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SkipListSet(this, fromElement).tailSet();
    }

    @Override
    public T first() {
        Node<T> fir = valueSearchDownNode(head);
        return fir.right.getValue();
    }

    @Override
    public T last() {
        Node<T> las = valueSearchDownNode(tail);
        return las.left.getValue();
    }

    @Override
    public int hashCode() {
        int h = 0;
        Iterator<T> i = iterator();
        while (i.hasNext()) {
            T obj = i.next();
            if (obj != null)
                h += obj.hashCode();
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Set))
            return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }
}
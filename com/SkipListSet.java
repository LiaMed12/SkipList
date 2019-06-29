package com;

import java.util.SortedSet;


class SkipListSet<T extends Comparable<T>>{
    private SkipList<T> list;
    private SkipList<T> sort = new SkipList<>();
    private T from;
    private T to;

    SkipListSet(SkipList<T> ts, T fromElement, T toElement) {
        this.list = ts;
        this.from = fromElement;
        this.to = toElement;
    }

    SkipListSet(SkipList<T> ts, T Element) {
        this.list = ts;
        this.to = Element;
    }


    SortedSet<T> subset() {
        Node<T> point = list.valueSearch(from);
        Node<T> k = list.valueSearch(to);
        while (point.getValue() != k.getValue()) {
            sort.add(point.getValue());
            point = point.right;
        }
        return sort;
    }

    SortedSet<T> headSet() {
        Node<T> point = list.valueSearchDownNode(list.getHead()).right;
        while (true) {
            if (point.getValue().compareTo(to) < 0) {
                sort.add(point.getValue());
                point = point.right;
            } else if (point.right.getValue() == list.getTail().getValue()) {
                break;
            } else {
                point = point.right;
            }
        }
        return sort;
    }

    SortedSet<T> tailSet( ) {
        Node<T> point = list.valueSearch(to);
        Node<T> k = list.valueSearchDownNode(list.getTail());
        while (true) {
            if (point.getValue() == k.getValue()) {
                break;
            }
            else if (point.getValue().compareTo(to) >= 0) {
                sort.add(point.getValue());
                point = point.right;
            }  else {
                point = point.right;
            }
        }
        return sort;
    }
}



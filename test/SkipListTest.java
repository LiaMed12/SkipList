package test;

import com.SkipList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import static junit.framework.TestCase.*;

public class SkipListTest {
    @Test
    public void add() {
        SkipList<Integer> list = new SkipList<>();
        assertEquals(0, list.size());

        assertTrue(list.add(1));
        assertTrue(list.add(4));
        assertTrue(list.add(10));
        //не должен добавлять элемент, который уже имеется
        assertFalse(list.add(1));

        assertEquals(3, list.size());
        //Проверка на нахождения чисел добавленных в list
        assertEquals(1, (int) list.valueSearch(1).getValue());
        assertEquals(4, (int) list.valueSearch(4).getValue());
        assertEquals(10, (int) list.valueSearch(10).getValue());
    }

    @Test
    public void remove() {
        SkipList<Integer> list = new SkipList<>();
        list.add(1);
        list.add(9);
        assertEquals(2, list.size());

        assertTrue(list.remove(1));

        //Не должен удалять то, чего нет
        assertFalse(list.remove(10));

        assertEquals(1, list.size());
        //Проверка на то, что этого элемента нет
        assertNull(list.valueSearch(1).getValue());
        list.add(2);
        list.remove(9);
        //Проверка на то, что этого элемента нет (в данном случае он не нашел число 9, поэтому возвращает часло стоящее перед ним)
        assertEquals(2, (int) list.valueSearch(9).getValue());
    }

    @Test
    public void isEmpty() {
        SkipList<Integer> list = new SkipList<>();
        list.add(-12);
        //list не пуст
        assertFalse(list.isEmpty());

        list.remove(-12);

        //list пуст
        assertTrue(list.isEmpty());
    }

    @Test
    public void contains() {
        SkipList<Integer> list = new SkipList<>();
        list.add(-12);
        assertTrue(list.contains(-12));
        //1 не содержится в list
        assertFalse(list.contains(1));
    }

    //Проверка SkipListIterator
    @Test
    public void hasNextAndNext() {
        SkipList<Integer> list = new SkipList<>();
        ArrayList<Integer> m = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        m.add(1);
        m.add(2);
        m.add(3);
        Iterator<Integer> listIter = list.iterator();
        Iterator<Integer> mIter = m.iterator();
        while (listIter.hasNext()) {
            assertEquals(mIter.next(), listIter.next());
        }
    }

    @Test
    public void toArray() {
        SkipList<Integer> list = new SkipList<>();
        list.add(-3);
        list.add(2);
        list.add(8);
        list.add(-10);
        Object[] m = list.toArray();
        StringBuilder result = new StringBuilder();
        for (Object s : m) {
            result.append(s).append(" ");
        }
        assertEquals("-10 -3 2 8 ", result.toString());
    }

    @Test
    public void containsAll() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(12);
        SkipList<Integer> list2 = new SkipList<>();
        list2.add(45);
        assertTrue(list.containsAll(list2));
        list.add(1);
        list2.add(2);
        assertFalse(list.containsAll(list2));
    }

    @Test
    public void addAll() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        SkipList<Integer> list2 = new SkipList<>();
        list2.add(4);
        assertTrue(list.addAll(list2));
        assertEquals(3, list.size());
    }

    @Test
    public void clear() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void retainAll() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        list.add(3);
        SkipList<Integer> list2 = new SkipList<>();
        list2.add(4);
        list2.add(1);
        list2.add(3);
        list.retainAll(list2);
        assertEquals(1, list.size());
    }

    @Test
    public void removeAll() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        list.add(3);
        SkipList<Integer> list2 = new SkipList<>();
        list2.add(4);
        list2.add(1);
        list2.add(3);
        list.removeAll(list2);
        assertEquals(2, list.size());
    }

    @Test
    public void first() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        list.add(3);
        assertEquals(-1, (int) list.first());
    }

    @Test
    public void last() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        list.add(3);
        assertEquals(45, (int) list.last());
    }

    @Test
    public void subset() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        list.add(3);
        list.add(5);
        list.add(8);
        SortedSet<Integer> n = list.subSet(3, 45);
        SortedSet<Integer> m = new TreeSet<>();
        m.add(45);
        m.add(-1);
        m.add(3);
        m.add(5);
        m.add(8);
        assertEquals(m.subSet(3, 45), n);
        assertEquals(m, list);

    }

    @Test
    public void headset() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        list.add(3);
        list.add(5);
        list.add(8);
        SortedSet<Integer> n = list.headSet(5);
        SortedSet<Integer> m = new TreeSet<>();
        m.add(45);
        m.add(-1);
        m.add(3);
        m.add(5);
        m.add(8);
        assertEquals(m.headSet(5), n);
        assertEquals(m, list);
    }

    @Test
    public void tailset() {
        SkipList<Integer> list = new SkipList<>();
        list.add(45);
        list.add(-1);
        list.add(3);
        list.add(5);
        list.add(8);
        SortedSet<Integer> n = list.tailSet(5);
        SortedSet<Integer> m = new TreeSet<>();
        m.add(45);
        m.add(-1);
        m.add(3);
        m.add(5);
        m.add(8);
        assertEquals(m.tailSet(5), n);
        assertEquals(m, list);
    }

    @Test
    public void hachcodeAndEquals() {
        SkipList<Integer> x = new SkipList<>();
        SkipList<Integer> y = new SkipList<>();
        x.add(12);
        x.add(8);
        y.add(12);
        y.add(8);
        assertEquals(x.equals(y), y.equals(x));
        assertEquals(x.hashCode(), y.hashCode());
        x.add(1);
        y.add(23);
        assertEquals(x.equals(y), y.equals(x));
        assertFalse(x.hashCode() == y.hashCode());
    }

}

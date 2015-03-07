package com.example.michal.smssync;

/**
 * Created by Michal on 4. 3. 2015.
 */
/**
 * Fronta - implementovana jako spojovy seznam
 */
public class Queue {
    private Node first;
    private Node last;
    private int size;

    public Queue() {
        this.size = 0;
    }

    /**
     * Prida na konec fronty prvek - asymptoticka sloztitost O(1)
     * @param i prvek k vlozeni
     */
    public void addLast(int i) {
        Node n = new Node(i);
        if(getSize() == 0) {
            this.first = n;
            this.last = n;
        } else {
            this.last.next = n;
            this.last = n;
        }
        size++;
    }

    /**
     * Odebere z fronty prvni prvek - asymptoticka sloztitost O(1)
     * @return prvni prvek
     */
    public int deteteFirst() {
        if(getSize() == 0) throw new IllegalStateException("Fronta je prazdna");
        int value = first.value;
        first = first.next;
        size--;
        return value;
    }
    /**
     * Vrati prvni prvek fronty
     * @return prvni prvek
     */
    public int getFirst() {
        if(getSize() == 0) throw new IllegalStateException("Fronta je prazdna");
        return first.value;
    }
    /**
     * Getter na delku
     * @return delka fronty
     */
    public int getSize() {
        return size;
    }

    /**
     * Dotaz pra prazdnost
     * @return true, pokud je fronta prazdna
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Klasicka toString metoda, vraci textovou reprezentaci objektu
     * @return textova reprezentace objektu
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node curr = first;
        for(int i = 0;  i < this.size; i++) {
            builder.append(curr.value).append(" ");
            curr = curr.next;
        }
        return builder.toString();
    }

    /**
     * Vnitrni reprezentace prvku
     */
    private class Node {
        private int value;
        private Node next;
        private Node(int value) {
            this.value = value;
        }
    }
}

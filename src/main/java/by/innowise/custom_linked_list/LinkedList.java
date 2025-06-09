package by.innowise.custom_linked_list;

import java.util.NoSuchElementException;

public class LinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public int size() {
        return this.size;
    }

    public void addFirst(T element) {
        Node<T> first = this.first;
        Node<T> newNode = new Node<>(null, element, first);
        this.first = newNode;
        if (first == null) {
            this.last = newNode;
        } else {
            first.prev = newNode;
        }
        this.size++;
    }

    public void addLast(T element) {
        Node<T> last = this.last;
        Node<T> newNode = new Node<>(last, element, null);
        this.last = newNode;
        if (last == null) {
            this.first = newNode;
        } else {
            last.next = newNode;
        }
        this.size++;
    }

    public void add(int index, T element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + this.size);
        }

        if (index == this.size) {
            addLast(element);
        } else {
            Node<T> next = getNode(index);
            Node<T> prev = next.prev;
            Node<T> node = new Node<>(prev, element, next);

            next.prev = node;
            if (prev == null) {
                this.first = node;
            } else {
                prev.next = node;
            }
            this.size++;
        }
    }

    public T getFirst() {
        checkListEmpty(this.first);
        return this.first.value;
    }

    public T getLast() {
        checkListEmpty(this.last);
        return this.last.value;
    }

    public void removeFirst() {
        checkListEmpty(this.first);

        Node<T> next = this.first.next;
        this.first.value = null;
        this.first.next = null;
        this.first = next;

        if (next == null) {
            this.last = null;
        } else {
            next.prev = null;
        }
        this.size--;
    }

    public void removeLast() {
        checkListEmpty(this.last);

        Node<T> prev = this.last.prev;
        this.last.value = null;
        this.last.prev = null;
        this.last = prev;

        if (prev == null) {
            this.first = null;
        } else {
            prev.next = null;
        }
        this.size--;
    }

    public void remove(int index) {
        checkIndex(index);

        Node<T> node = getNode(index);
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            this.first = next;
        } else {
            node.prev = null;
            prev.next = next;
        }

        if (next == null) {
            this.last = prev;
        } else {
            node.next = null;
            next.prev = prev;
        }

        node.value = null;

        this.size--;
    }

    public T get(int index) {
        checkIndex(index);

        return getNode(index).value;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + this.size);
        }
    }

    private void checkListEmpty(Node<T> node) {
        if (node == null) {
            throw new NoSuchElementException("List is empty");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < this.size / 2) {
            current = this.first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = this.last;
            for (int i = this.size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}

package implementations;

import utilities.Iterator;
import utilities.ListADT;
import java.util.NoSuchElementException;

public class MyDLL<E> implements ListADT<E> {
    int size;
    Node<E> head;
    Node<E> tail;
    private int cursor = 0;    // For Iterator functionality
    private int lastRet = -1;  // Tracks the last returned element

    public MyDLL(){
        size = 0;
        head = tail = null;
    }

    public Node<E> getHead() {
        return head;
    }

    public void setHead(Node<E> head) {
        this.head = head;
    }

    public Node<E> getTail() {
        return tail;
    }

    public void setTail(Node<E> tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) {
        if (toAdd == null){
            throw new NullPointerException();
        }
        if (index > size){
            throw new IndexOutOfBoundsException();
        }

        Node<E> newNode = new Node<>(toAdd);
        // checking if the list is empty
        if ((head == null && tail == null) || index == size){
            add(newNode.getData());
            return true;
        }
        else if (index == 0 && head != null){
            head.setPrevious(newNode);
            newNode.setNext(head);
            head = newNode;
        }
        else{
            // keep track of the nodes index
            int i = 0;
            Node<E> current = head;

            while (current != null){
                if (i == index){
                    newNode.setPrevious(current.getPrevious());
                    newNode.setNext(current);
                    current.getPrevious().setNext(newNode);
                    current.setPrevious(newNode);
                }
                current  = current.getNext();
                i++;
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) {
        if (toAdd == null){
            throw new NullPointerException();
        }

        Node<E> newNode = new Node<>(toAdd);
        // checking if the head and tail is empty
        if (head == null && tail == null){
            head = tail = newNode;
        }else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null){
            throw new NullPointerException();
        }

        E[] toAddArray = (E[]) toAdd.toArray();

        for (int i = 0; i < toAddArray.length; i++) {
            add(toAddArray[i]);
        }
        return true;
    }

    @Override
    public E get(int index) {
        if (index >= size){
            throw new IndexOutOfBoundsException();
        }

        Node<E> current = head;
        // tracking the elements in the list by index
        int i = 0;

        while (current != null){
            if (index == i){
                return current.getData();
            }
            current = current.getNext();
            i++;
        }
        return null;
    }

    @Override
    public E remove(int index) {
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        E removedNodeData = null;

        int i = 0;
        Node<E> current = head;

        while (current != null){
            if (i == index){
                if (size == 1){
                    removedNodeData = head.getData();
                    head = tail = null;
                    size--;
                    break;
                }else if (current == head){
                    removedNodeData = current.getData();
                    current.getNext().setPrevious(null);
                    head = current.getNext();
                    size--;
                    break;
                }
                else if (current == tail) {
                    removedNodeData = current.getData();
                    current.getPrevious().setNext(null);
                    tail = current.getPrevious();
                    size--;
                    break;
                }else {
                    removedNodeData = current.getData();
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size--;
                    break;
                }
            }
            current = current.getNext();
            i++;
        }
        return removedNodeData;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null){
            throw new NullPointerException();
        }
        E removedNodeData = null;

        int i = 0;
        Node<E> current = head;

        while (current != null){
            if (toRemove == current.getData()){
                if (size == 1){
                    removedNodeData = head.getData();
                    head = tail = null;
                    size--;
                    break;
                }else if (current == head){
                    removedNodeData = current.getData();
                    current.getNext().setPrevious(null);
                    head = current.getNext();
                    size--;
                    break;
                }
                else if (current == tail) {
                    removedNodeData = current.getData();
                    current.getPrevious().setNext(null);
                    tail = current.getPrevious();
                    size--;
                    break;
                }else {
                    removedNodeData = current.getData();
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size--;
                    break;
                }
            }
            current = current.getNext();
            i++;
        }
        return removedNodeData;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (index >= size){
            throw new IndexOutOfBoundsException();
        }
        if (toChange == null){
            throw new NullPointerException();
        }

        Node<E> current = head;
        // tracking the elements in the list by index
        int i = 0;

        while (current != null){
            if (index == i){
                E updatedValue = current.getData();
                current.setData(toChange);
                return updatedValue;
            }
            current = current.getNext();
            i++;
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null){
            throw new NullPointerException();
        }

        Node<E> current = head;
        // tracking the elements in the list by index
        int i = 0;

        while (current != null){
            if (toFind == current.getData()){
                return true;
            }
            current = current.getNext();
            i++;
        }
        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {

        Node<E> current = head;
        // tracking the elements in the list by index
        int i = 0;

        while (i < toHold.length){
            if (current == null){
                toHold[i] = null;
            }else {
                toHold[i] = current.getData();
                current = current.getNext();
            }
            i++;
        }

        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];

        Node<E> current = head;
        // tracking the elements in the list by index
        int i = 0;

        while (current != null){
            arr[i] = current.getData();
            current = current.getNext();
            i++;
        }

        return arr;
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }

    private class DLLIterator implements Iterator<E>{

        @Override
        public boolean hasNext() {
            int i = 0;
            Node<E> current = head;
            while (current != null){
                if (i == cursor){
                    return true;
                }
                current = current.getNext();
                i++;
            }
            return false;
        }

        @Override
        public E next() {
            int i = 0;
            Node<E> current = head;
            while (current != null){
                if (i == cursor){
                    E returnedData = current.getData();
                    lastRet = cursor++;
                    return returnedData;
                }
                current = current.getNext();
                i++;
            }
            throw new NoSuchElementException();
        }
    }


}

package implementations;

import utilities.Iterator;
import utilities.QueueADT;
import exceptions.EmptyQueueException;

public class MyQueue<E> implements QueueADT<E> {
    MyDLL<E> queue;
    int fixedCapacity;

    public MyQueue(int fixedCapacity) {
        this.queue = new MyDLL<>();
        this.fixedCapacity = fixedCapacity;
    }

    public MyQueue() {
        this.queue = new MyDLL<>();
        this.fixedCapacity = 0;
    }

    @Override
    public void enqueue(E toAdd) {
        if (toAdd == null){
            throw new NullPointerException();
        }
        queue.add(toAdd);
    }

    @Override
    public E dequeue() {
        if (queue.getSize() == 0){
            throw new EmptyQueueException();
        }
        return  queue.remove(0);
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (queue.getSize() == 0){
            throw new EmptyQueueException();
        }
        return  queue.getHead().getData();
    }

    @Override
    public void dequeueAll() {
        queue.clear();
    }

    @Override
    public boolean isEmpty() {
        return queue.getSize() == 0;
    }

    @Override
    public boolean contains(E toFind) {
        if (toFind == null){
            throw new NullPointerException();
        }
       return queue.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        Node<E> current = queue.getHead();
        int position = 0;
        while (current != null){
            if (current.getData() == toFind){
                return position + 1;
            }
            current = current.getNext();
            position++;
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public boolean equals(QueueADT<E> that) {
        Object[] queueArray = toArray();
        Object[] thatArray = that.toArray();
        boolean isEqual = false;
        if (queueArray.length == thatArray.length){
            for (int i = 0; i < queueArray.length; i++) {
                if (queueArray[i] != thatArray[i]){
                    isEqual = false;
                    break;
                }
                isEqual = true;
            }
        }
        return isEqual;
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public E[] toArray(E[] holder)  {
        if (holder == null){
            throw new NullPointerException();
        }
        return queue.toArray(holder);
    }

    @Override
    public boolean isFull() {
        return queue.getSize() >= fixedCapacity;
    }

    @Override
    public int size() {
        return queue.getSize();
    }

    public void displayDetail(){
        Node<E> current = queue.head;

        while (current != null){
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
}

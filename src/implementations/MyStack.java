package implementations;

import utilities.Iterator;
import utilities.StackADT;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class MyStack<E> implements StackADT<E> {
    private MyArrayList<E> stack;
    private int top = 0;
    private int fixedLength = -1;
    private int cursor = -1;    // For Iterator functionality
    private int lastRet;

    public MyStack() {
        stack =  new MyArrayList<>();
    }

    public MyStack(int fixedLength) {
        stack =  new MyArrayList<>();
        this.fixedLength = fixedLength;
    }


    @Override
    public void push (E element){
        if (element == null){
            throw new NullPointerException();
        }
        stack.add(element);
        top++;
    }

    @Override
    public E peek (){
        if (top == 0){
            throw new EmptyStackException();
        }
        E peekedObj = stack.get(top - 1);
        return peekedObj;
    }

    @Override
    public E pop(){
        if (top == 0){
            throw new EmptyStackException();
        }

        E poppedElement = stack.get(--top);
        stack.remove(top);

        return poppedElement;
    }

    @Override
    public void clear() {
        stack.clear();
        top = 0;
    }

    @Override
    public Object[] toArray() {
        Object[] original = stack.toArray();

        // creating an array that stores the elements using the LIFO concept
        Object[] reversed = new Object[original.length];

        for (int lastReadIndex = original.length - 1; lastReadIndex >= 0 ; lastReadIndex--) {
            reversed[(original.length-1) - lastReadIndex] = original[lastReadIndex];
        }

        return reversed;
    }

    @Override
    public E[] toArray(E[] holder) {
        if (holder == null){
            throw new NullPointerException();
        }

        Object[] original = stack.toArray();

        // creating an array that stores the elements using the LIFO concept
        Object[] reversed = new Object[original.length];

        for (int lastReadIndex = original.length - 1; lastReadIndex >= 0 ; lastReadIndex--) {
            reversed [(original.length-1) - lastReadIndex] = original[lastReadIndex];
        }

        // copying the reversed elements to the holder array
        for (int i = 0; i < holder.length; i++) {
            if (i >= stack.size()){
                holder[i] = null;
            }
            holder[i] = (E) reversed[i];
        }
        return holder;
    }

    @Override
    public boolean contains(E toFind) {
        if (toFind == null){
            throw new NullPointerException();
        }
        return stack.contains(toFind);
    }

    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

    @Override
    public boolean equals(StackADT<E> that) {
        Object[] compare1 =  toArray();
        Object[] compare2 = that.toArray();

        boolean theSame = false;

        if (compare1.length == compare2.length){
            theSame = true;

            // comparing each element in the array
            for (int i = 0; i < compare1.length; i++) {
                if (compare1[i] == compare2[i]){
                    theSame = true;
                }else {
                    theSame = false;
                    break;
                }
            }
        }

        return theSame;
    }

    @Override
    public boolean stackOverflow() {
        return top == fixedLength;
    }

    @Override
    public boolean isEmpty(){
        return top == 0;
    }

    @Override
    public int size(){
        return stack.size();
    }

    @Override
    public int search(E element){
        E[] stackArray = (E[]) stack.toArray();

        // i keeps track of the index of the elements in the array
        int i;
        boolean found = false;

        for (i = stackArray.length - 1; i >= 0; i--){
            if(stackArray[i] == element){
                found = true;
                break;
            }
        }

        if (found){
            return top - i;
        }
        return -1;
    }

    // creating the iterator inner class
    private class StackIterator implements Iterator<E>{
        @Override
        public boolean hasNext() {
            if (cursor == -1){
                cursor = top - 1;
            }
            return cursor >= 0;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastRet = cursor--;
            return stack.get(lastRet);
        }
    }
}

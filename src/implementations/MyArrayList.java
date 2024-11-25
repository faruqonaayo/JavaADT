package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;

public class MyArrayList<E> implements ListADT<E> {
    E[] arr;
    int size;
    private int cursor = 0;    // For Iterator functionality
    private int lastRet = -1;  // Tracks the last returned element

    public MyArrayList() {
        // initializing the underlying array length to 10
        this.arr = (E[]) new Object[10];
        // initializing the size to 0
        this.size = 0;
    }

    public void displayAll(){
        for (int i = 0; i < size; i++) {
            System.out.println(arr[i]);
        }
    }

    @Override
    public int size(){
        return  size;
    }

    @Override
    public void clear(){
        // setting the values back to the default initialized values
        this.arr = (E[]) new Object[10];
        this.size = 0;
    }

    @Override
    public boolean add( int index, E toAdd ){
        // checking if element to add is null
        if (toAdd == null){
            throw new NullPointerException();
        }

        // checking to see if the index to put the element is out of underlying array bound
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        // checking if the arraylist is not full
        if (index == size){
            arr[size++] = toAdd;
            return true;
        }else if (arr.length > size){
            // adding all elements before the index to the new array
            for (int i = size-1; i >= index; i--) {
                arr[i + 1] = arr[i];
            }
            size++;
            arr[index] = toAdd;
            return  true;
        }

        E[] newArray = (E[]) new Object[arr.length * 2];

        // adding all elements before the index to the new array
        for (int i = 0; i < index; i++) {
            newArray[i] = arr[i];
        }

        //insert the new element
        newArray[index] = toAdd;

        // inserting the rest of the elements in the old array
        for (int i = index; i < size; i++) {
            newArray[i + 1] = arr[i];
        }

        //making arr to the new array
        arr = newArray;
        size++;
        return true;
    }

    @Override
    public boolean add( E toAdd ){
        // checking if element to add is null
        if (toAdd == null){
            throw new NullPointerException();
        }

        // checking if the arraylist is not full
        if (arr.length > size){
            arr[size++] = toAdd;
            return true;
        }

        // if the array list is full
        // a new array to accommodate the new element is created
        E[] newArray = (E[]) new Object[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            newArray[i] = arr[i];
        }
        newArray[size++] = toAdd;

        //making arr to the new array
        arr = newArray;
        return true;
    }



    @Override
    public boolean addAll( ListADT<? extends E> toAdd ){
        if (toAdd == null){
            throw new NullPointerException();
        }


        for (int i = 0; i < toAdd.size(); i++) {
            add(toAdd.get(i));
        }
        return true;
    }

    @Override
    public E get( int index ){
        if (index >= size){
            throw new IndexOutOfBoundsException();
        }

        return arr[index];
    }

    @Override
    public E remove( int index ){
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }

        E removedElement = arr[index];

        for (int i = 0; i < size; i++) {
            // shifting all the elements after the index to be removed to the left
            if (i > index){
                arr[i - 1] = arr[i];
            }else if (i == index){
                arr[index] = null;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public E remove( E toRemove ){
        if (toRemove == null){
            throw new NullPointerException();
        }

        int toRemoveIndex = -1;
        for (int i = 0; i < size ; i++) {
            if (arr[i] == toRemove){
                toRemoveIndex = i;
            }
        }

        // return null if the element is not found
        if (toRemoveIndex == -1){
            return null;
        }

        // if the element was remove return the removed element
        return remove(toRemoveIndex);
    }

    @Override
    public E set( int index, E toChange ){
        if (toChange == null){
            throw new NullPointerException();
        }
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }

        E changedElement = arr[index];

        arr[index] = toChange;

        return changedElement;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains( E toFind ){
        if (toFind == null){
            throw new NullPointerException();
        }

        int toFindIndex = -1;
        for (int i = 0; i < size ; i++) {
            if (arr[i] == toFind){
                toFindIndex = i;
            }
        }

        return toFindIndex != -1;
    }

    @Override
    public E[] toArray( E[] toHold ){
        if (toHold == null){
            throw new NullPointerException();
        }

        for (int i = 0; i < toHold.length; i++) {
            if (i >= arr.length){
                toHold[i] = null;
            }
            toHold[i] = arr[i];
        }
        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] retArray = new Object[size];

        for (int i = 0; i < size; i++) {
            retArray[i] = get(i);
        }
        return retArray;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    // creating the iterator inner class
    private class ArrayListIterator implements Iterator<E>{
        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastRet = cursor++;
            return arr[lastRet];
        }
    }
}

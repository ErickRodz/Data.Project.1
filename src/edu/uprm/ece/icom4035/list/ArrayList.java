package edu.uprm.ece.icom4035.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {
	
	private static final int initcap = 5; 
	private static final int captoar = 3; 
	private static final int maxemtypos = 3;
	private E[] element; 
	private int size; 

	@SuppressWarnings("unchecked")
	public ArrayList() { 
		element = (E[]) new Object[initcap]; 
		size = 0; 
	} 

	@Override
	public Iterator<E> iterator() {
		return new ALIterator<E>(this);
	}
	
	private class ALIterator<E> implements Iterator<E> {
		private ArrayList<E> arr;
		private int count = 0;
		public ALIterator(ArrayList<E> aList) {
			this.arr = aList;
		}

		@Override
		public boolean hasNext() {
			if(count < size()) return true;
			return false;
		}
		@Override
		public E next() {
			if(!this.hasNext())throw new NoSuchElementException("There are no more elements");
			return arr.get(count++);
		}	
	}
	
	
	@Override
	public void add(E obj) {
		if(element.length == size) {changeCapacity(captoar);}
		element[size] = obj;
		size++;
	}

	@Override
	public void add(int index, E obj) {
		if(index>size|| index<0) throw new IndexOutOfBoundsException("Invalid Index");
		if(this.size == element.length) {changeCapacity(captoar);}
		if(index <size) {moveDataToRight(index, size-1);}
		element[index] = obj;
		size++;	
	}

	@Override
	public boolean remove(E obj) {
		
		for(int i = 0; i < size-1; i ++) {
			if(element[i] == obj) {
				element[i] = null;
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(int index) {
		if(index>size|| index<0) throw new IndexOutOfBoundsException("Invalid Index");
		if(index < size){ moveDataToLeft(index+1,size-1); }
		element[size-1] = null;
		size--;
		if(element.length - size > maxemtypos) { changeCapacity(-captoar);  }
		return true;
	}

	@Override
	public int removeAll(E obj) {
		int count = 0;
		
		for(int i = 0 ; i < size-1; i ++) {
			if(element[i] == obj) {
				element[i] = null;
				size--;
				count++;
			}
		}
		return count;
	}

	@Override
	public E get(int index) {
		if(index > size - 1 || index < 0) {throw new IndexOutOfBoundsException("get: Invalid index = " + index );}
		return element[index];
	}

	@Override
	public E set(int index, E obj) {
		if(index > size - 1 || index < 0) {throw new IndexOutOfBoundsException("set: Invalid index = " + index );}
		E oldobj = element[index];
		element[index] = obj;
		return oldobj;
	}

	@Override
	public E first() {
		if(size == 0) { return null; }
		return element[0];
	}

	@Override
	public E last() {
		if(size == 0) { return null; }
		return element[size-1];
	}

	@Override
	public int firstIndex(E obj) {
		int count = 0;
		for(E p: this) {
			if(p.equals(obj)) return count;
			count++;
		}
		return -1;
	}

	@Override
	public int lastIndex(E obj) {
		for (int i = size-1; i > 0; i --) {
			if(element[i] == obj) {return i;}
		}
		return -1;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() { return (size() == 0); }

	@Override
	public boolean contains(E obj) {
		
		for( int i = 0; i < size-1; i ++) {
			if(element[i].equals(obj)) {return true;}
		}
		return false;
	}

	@Override
	public void clear() {
		for(int i = 0; i < size-1; i++) {
			element[i] = null;
		}
		element = (E[]) new Object[initcap];
		size = 0;	
	}
	
	//Reused blocks of code from previous labs
	private void moveDataToRight(int low, int sup) { 
		for (int pos = sup; pos >= low; pos--)
			element[pos+1] = element[pos]; 
	}
	private void moveDataToLeft(int low, int sup) { 
		for (int pos = low; pos <= sup; pos++)
			element[pos-1] = element[pos]; 
	}
	private void changeCapacity(int change) { 
		int newCapacity = element.length + change; 
		@SuppressWarnings("unchecked")
		E[] newElement = (E[]) new Object[newCapacity]; 
		for (int i=0; i<size; i++) { 
			newElement[i] = element[i]; 
			element[i] = null; 
		} 
		element = newElement; 
	}

}

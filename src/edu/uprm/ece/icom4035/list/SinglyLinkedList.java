package edu.uprm.ece.icom4035.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E>{
	private Node<E> header; 
	private int size; 
	public SinglyLinkedList() {
		header = new Node<E>();
		size = 0;
	}
	
	
	
	@Override
	public Iterator<E> iterator() {  return new SLLIterator<>(this);  }

	@SuppressWarnings("hiding")
	private class SLLIterator<E> implements Iterator<E> {
		
		private SinglyLinkedList<E> base;
		private int count = 0;
		
		public SLLIterator(SinglyLinkedList<E> list) {this.base = list;}
		
		@Override
		public boolean hasNext(){
			if(count < size()) {return true;}
			return false;
			}
		
		@Override
		public E next() {
			if(!this.hasNext())throw new NoSuchElementException("There are no more elements");
				return base.get(count++);}
	}

	@Override
	public void add(E obj) {
		Node<E> crr = header;
		while(crr.getNext()!= null) {crr = crr.getNext();}
		crr.setNext(new Node<E> (obj));
		size++;
			}
		
	

	@Override
	public void add(int index, E obj) {
		int counter = 0;
		Node<E> nta = new Node<E>(obj);
		Node<E> crr = header;
		
		if(index == 0) {  
			nta.setNext(header.getNext());
			header.setNext(nta);
		}else {
			while( counter < index-1) {
				crr = crr.getNext();
				counter++;
				}
				nta.setNext(crr.getNext());
				crr.setNext(nta);
			}
			size++;
		}

	@Override
	public boolean remove(E obj) {
		if(size == 0) {return false;}
		Node<E> prv = header;
		Node<E> crr = header.getNext();
		while(crr.getNext() != null && crr.getNext()!= obj) {
			prv = crr;
			crr = crr.getNext();
			if(crr.getElement().equals(obj)) {
				prv.setNext(crr.getNext());
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(int index) {
		if(size == 0 || index > size || index < 0) {throw new IndexOutOfBoundsException("Index out of bounds");}
		int count = 0;
		Node<E> prv = header;
		Node<E> ntr = header.getNext();
		while(count < index) { 
			prv = ntr;
			ntr = ntr.getNext();
			count++;
			}
		prv.setNext(ntr.getNext());
		size--;
		return true;
	}

	@Override
	public int removeAll(E obj) {
		int count = 0;
		Node<E> crr = header.getNext();
		Node<E> prv = header;
		while(crr.getNext() != null) {
			if(crr.getElement().equals(obj)) {
				prv.setNext(crr.getNext());
				crr = prv.getNext();
				size--;
				count++;
			}else {
			prv = crr;
			crr = crr.getNext();
			}
		}
		return count;
	}

	@Override
	public E get(int index) {
		if(index < 0 || index > size || size == 0) {throw new IndexOutOfBoundsException("Index out of Bounds");}
		int count = 0;
		Node<E> etg = header.getNext();
		while(count != index) {
			etg = etg.getNext();
			count++;
		}
		return etg.getElement();
	}

	@Override
	public E set(int index, E obj) {
		if(index < 0 || index > size || size == 0) {throw new IndexOutOfBoundsException("Index out of Bounds");}
		int count = 0;
		Node<E> prv = header;
		Node<E> vtr = header.getNext();
		while(count < index && vtr.getNext() != null) {
			prv = vtr;
			vtr = vtr.getNext();
			count++;
		}
		E etr = vtr.getElement();
		prv.setNext(new Node<E>(obj, vtr.getNext()));		
		return etr;
	}

	@Override
	public E first() {
		if(size == 0) {return null;}
		return header.getNext().getElement();
	}

	@Override
	public E last() {
		if(size == 0) {return null;}
		Node<E> etr = header.getNext();
		while(etr.getNext() != null) {etr = etr.getNext();}
		
		return etr.getElement();
	}

	@Override
	public int firstIndex(E obj) {
		if(size == 0){return -1;}
		
		int count = 0;
		Node<E> ntf = header.getNext();
		while(ntf.getNext()!= null) {
			if(ntf.getElement().equals(obj)) { return count; }
			ntf = ntf.getNext();
			count++;
		}
		if(ntf.getElement().equals(obj)) {
			return count;
		}
		return -1;
	}

	@Override
	public int lastIndex(E obj) {
		if(size == 0){return -1;}
		int litk = 0;
		int count = 0;
		Node<E> crr = header.getNext();
		Node<E> itr = header.getNext();
		while(crr.getNext() != null) {
			if(crr.getElement().equals(obj)) {
				itr = crr;
				litk = count;
				count++;
				crr = crr.getNext();
			}else {
				count++;
				crr = crr.getNext();
			}
		}
		if(itr.getElement().equals(obj)) {
			return litk;
		}
		return -1;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if(size == 0) {return true;}
		return false;
	}

	@Override
	public boolean contains(E obj) {
		Node<E> ntf = header.getNext();
		while(ntf.getNext() != null) {
			if(ntf.getElement().equals(obj)) {return true;}
			ntf = ntf.getNext();
			}
		return false;
	}

	@Override
	public void clear() {
		header.setNext(null);
		size = 0;
	}
	
	
	private static class Node<E>{
		private E element;
		private Node<E> next;
		public Node() {
			element = null;
			next = null;
		}
		public Node(E element) {
			this.element = element;
			next = null;
		}
		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}

		public E getElement() {return element;}
		public void setElement(E newElement) {this.element = newElement;}

		public Node<E> getNext() {return next;}
		public void setNext(Node<E> newNext) {this.next = newNext;}
	}
}









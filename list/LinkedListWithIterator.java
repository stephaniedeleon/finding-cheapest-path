
//Date: 			May 15, 2020
//Description:	
//				LinkedListWithIterator class 
//				which implements ListWithIteratorInterface.
//				Used in Vertex class.
//

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListWithIterator<T> implements ListWithIteratorInterface<T> {
	
	private Node firstNode;
	private int numberOfEntries;
	
	public LinkedListWithIterator() {
		
		firstNode = null;
		numberOfEntries = 0;
	}
	
	public void add(T newEntry) {

		Node newNode = new Node(newEntry);

		if(isEmpty())
			firstNode = newNode;
		else {
			Node lastNode = getNodeAt(numberOfEntries); 
			lastNode.setNextNode(newNode);
		}
		numberOfEntries++;
	}


	public void add(int givenPosition, T newEntry) {

		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries + 1)){
		
			Node newNode = new Node(newEntry);

			if(givenPosition == 1) {

				newNode.setNextNode(firstNode);
				firstNode = newNode;

			} else {

				Node nodeBefore = getNodeAt(givenPosition-1);
				Node nodeAfter = nodeBefore.getNextNode();
				newNode.setNextNode(nodeAfter);
				nodeBefore.setNextNode(newNode);
			}
			numberOfEntries++;

		} else 
			throw new IndexOutOfBoundsException("Illegal Position");
	}


	public T remove(int givenPosition) {

		T result = null;
		if((givenPosition >= 1) && (givenPosition <= numberOfEntries)){

			if(givenPosition == 1) {

				result = firstNode.getData();
				firstNode = firstNode.getNextNode();

			} else {

				Node nodeBefore = getNodeAt(givenPosition-1);
				Node nodeToRemove = nodeBefore.getNextNode();
				result = nodeToRemove.getData();
				Node nodeAfter = nodeToRemove.getNextNode();
				nodeBefore.setNextNode(nodeAfter);
			}
			numberOfEntries--;
			return result;

		} else 
			throw new IndexOutOfBoundsException("Illegal Position");
	}


	public void clear() {

		firstNode = null;
		numberOfEntries = 0;
	}


	public T replace(int givenPosition, T newEntry) {

		if((givenPosition >= 1) && (givenPosition <= numberOfEntries)){

			Node desiredNode = getNodeAt(givenPosition);
			T originalEntry = desiredNode.getData();
			desiredNode.setData(newEntry);
			return originalEntry;

		} else 
			throw new IndexOutOfBoundsException("Illegal Position");
	}


	public T getEntry(int givenPosition) {

		if((givenPosition >= 1) && (givenPosition <= numberOfEntries)){

			return getNodeAt(givenPosition).getData();

		} else 
			throw new IndexOutOfBoundsException("Illegal Position");
	}


	public T[] toArray() {

		throw new java.lang.UnsupportedOperationException();
	}


	public boolean contains(T anEntry){

		boolean found = false;
		Node currentNode = firstNode;

		while(!found && (currentNode != null)) {
			if(anEntry.equals(currentNode.getData()))
				found = true;
			else 
				currentNode = currentNode.getNextNode();
		}
		return found;
	}


	public int getLength() {

		return numberOfEntries;
	}


	public boolean isEmpty() {

		boolean result;

		if(numberOfEntries == 0)
			result = true;
		else 
			result = false;

		return result;
	}


	private Node getNodeAt(int givenPosition) {

		Node currentNode = firstNode;

		for (int counter = 1; counter < givenPosition; counter++)
			currentNode = currentNode.getNextNode();

		return currentNode;
	}
	
	
	public Iterator<T> iterator() {
		
		return new IteratorForLinkedList();
	}

	
	public Iterator<T> getIterator() {
		
		return iterator();
	}
	
	
	private class IteratorForLinkedList implements Iterator<T> {
		
		private Node nextNode;
		
		private IteratorForLinkedList() {
			
			nextNode = firstNode;
		}
		
		public T next() {
			
			T result;
			
			if (hasNext()) {
				result = nextNode.getData();
				nextNode = nextNode.getNextNode();
			} else
				throw new NoSuchElementException("Illegal call to next().");
			
			return result;
		}

		
		public boolean hasNext() {
			
			return nextNode!=null;
		}
		
		
		public void remove() {
			
			throw new UnsupportedOperationException();
		}
	}
	
	
	private class Node {
		
		private T data;
		private Node next;
		
		private Node (T dataPortion) {
			this(dataPortion, null);
		}
		
		private Node (T dataPortion, Node nextNode) { //Two parts: data and reference to nextNode
			data = dataPortion;
			next = nextNode;
		}

		public T getData() {
			
			return data;
		}
		
		
		public void setData(T newData) {
			
			data = newData;
		}
		
		
		public Node getNextNode() {
			
			return next;
		}
		
		
		public void setNextNode(Node newNode) {
			
			next = newNode;
		}	
	}

}

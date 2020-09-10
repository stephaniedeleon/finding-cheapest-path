
//Date: 			May 15, 2020
//Description:	
//				ArrayStack class which implements StackInterface.
//

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<T> implements StackInterface<T> {
	
	private T[] stack;
	private int topIndex;
	public static final int DEFAULT_CAPACITY = 25;

	
	public ArrayStack() {
		
		this(DEFAULT_CAPACITY);
	}
	
	
	public ArrayStack(int initialCapacity) {
		
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[])new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
	}
	
	
	@Override
	public void push(T newEntry) {
		
		ensureCapacity();
		stack[++topIndex] = newEntry;
	}
	
	private void ensureCapacity() {

		if (topIndex == stack.length - 1) {
			int newLength = 2 * stack.length;
			stack = Arrays.copyOf(stack, newLength);
		}
	}

	
	@Override
	public T pop() {
		
		if (isEmpty())
			throw new EmptyStackException();
		else { 
			T data = peek();
			stack[topIndex--] = null;
			return data;
		}		
	}

	
	@Override
	public T peek() {
		
		if (isEmpty())
			throw new EmptyStackException();
		else
			return stack[topIndex];
	}

	
	@Override
	public boolean isEmpty() {
		
		return topIndex < 0;
	}

	
	@Override
	public void clear() {
		
		topIndex = -1;
	}

}

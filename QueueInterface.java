
//Date: 		May 15, 2020
//Description:	
//				Queue Interface used in GraphAlgorithmsInterface and DirectedGraph.
//

public interface QueueInterface<T> {
	
	/** Adds a new entry to the back of this queue.
	 * @param anEntry An object to be added.
	 */
	public void enqueue(T anEntry);

	/** Removes and returns the entry at the front of this queue.
	 * @return The object at the front of the queue.
	 * @throws EmptyQueueException if the queue is empty before the operation.
	 */
	public T dequeue();

	/** Retrieves the entry at the front of this queue.
	 * @return The object at the front of the queue.
	 * @throws EmptyQueueException if the queue is empty before the operation.
	 */
	public T getFront();

	/** Detects whether this queue is empty.
	 * @return True if the queue is empty, or false otherwise.
	 */
	public boolean isEmpty();

	/** Removes all entries from this queue.
	 */
	public void clear();

}

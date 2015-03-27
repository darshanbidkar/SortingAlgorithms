// Ver 0.1:  Sat, Feb 28.  Initial description.
// Ver 1.0:  Tue, Mar 03.  Added more comments.  Modified decreaseKey().

/**
 * Class denoting a priority queue
 * 
 * @author darshanbidkar
 *
 * @param <T>
 *            Generic Type
 */
public class PriorityQueueIndexed<T extends Comparable<? super T> & PQIndex> {
	private T[] queue;

	// Denotes the number of elements present in the PQ
	private int size = 0;

	/**
	 * Build a priority queue with a given array q
	 */
	PriorityQueueIndexed(T[] q) {
		this.queue = q;
		size = q.length - 1;
		this.buildHeap();
	}

	/**
	 * Creates an empty priority queue of given maximum size
	 */
	PriorityQueueIndexed(int n) {
		this.queue = (T[]) new Comparable[n];
		size = n;
	}

	/**
	 * Adds an element to the priority queue
	 * 
	 * @param x
	 *            element to be added
	 */
	void insert(T x) {
		add(x);
	}

	/**
	 * Adds an element to the priority queue
	 * 
	 * @param x
	 *            element to be added
	 */
	void add(T x) {
		this.queue[0] = x;
		this.size++;
		this.queue[size] = x;
		this.percolateUp(size);
	}

	/**
	 * Removes an element from the priority queue
	 * 
	 * @return element removed
	 */
	T remove() {
		return deleteMin();
	}

	/**
	 * Removes an element from the priority queue
	 * 
	 * @return element removed
	 */
	T deleteMin() {
		T returnVal = this.queue[1];
		this.queue[1] = this.queue[size];
		this.size--;
		this.percolateDown(1);
		return returnVal;
	}

	/**
	 * restores heap order property after the priority of x has decreased
	 */
	void decreaseKey(T x) {
		this.queue[0] = x;
		percolateUp(x.getIndex());
	}

	/**
	 * Returns the minimum element in the priority queue
	 * 
	 * @return the minimum element
	 */
	T min() {
		return this.size > 0 ? this.queue[1] : null;
	}

	/**
	 * Priority of element at index i of queue has decreased. It may violate
	 * heap order. Restore heap property
	 */
	void percolateUp(int i) {
		int hole = i;
		T x = this.queue[i];
		while (this.queue[hole >>> 1].compareTo(x) > 0) {
			this.queue[hole] = this.queue[hole >>> 1];
			this.queue[hole].putIndex(hole);
			hole = hole >>> 1;
		}
		this.queue[hole] = x;
		x.putIndex(hole);
	}

	/**
	 * Creates heap order for sub-heap rooted at node i. Precondition: Heap
	 * order may be violated at node i, but its children are roots of heaps that
	 * are fine. Restore heap property
	 * 
	 * @param i
	 *            the index
	 */
	void percolateDown(int i) {
		int child = 2 * i;
		if (child == size) // Only left child
		{
			if (this.queue[child].compareTo(this.queue[i]) < 0) {
				T temp = this.queue[child];
				this.queue[child] = this.queue[i];
				this.queue[i] = temp;

				this.queue[i].putIndex(i);
				this.queue[child].putIndex(child);
			}
		}
		if (child < size) // Process left and right child
		{
			if (this.queue[child].compareTo(this.queue[child + 1]) < 0) {
				// Left child is smaller
				if (this.queue[child].compareTo(this.queue[i]) < 0) {
					T temp = this.queue[child];
					this.queue[child] = this.queue[i];
					this.queue[i] = temp;

					this.queue[i].putIndex(i);
					this.queue[child].putIndex(child);
					this.percolateDown(child);
				}
			} else if (this.queue[child + 1].compareTo(this.queue[i]) < 0) {
				T temp = this.queue[child + 1];
				this.queue[child + 1] = this.queue[i];
				this.queue[i] = temp;

				this.queue[i].putIndex(i);
				this.queue[child + 1].putIndex(child + 1);
				this.percolateDown(child + 1);
			}
		}
	}

	/**
	 * Creates a heap. Precondition: none. Array may violate heap order in many
	 * places.
	 */
	void buildHeap() {
		int i = (int) Math.ceil(new Double(size).doubleValue() / 2);
		while (i > 0) {
			this.percolateDown(i);
			i--;
		}
	}

	/**
	 * Returns whether the priority queue is empty or not
	 * 
	 * @return true if empty, false if not
	 */
	boolean isEmpty() {
		return size == 0;
	}

}
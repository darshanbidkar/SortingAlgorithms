import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Class representing a SkipList
 * 
 * @author darshanbidkar
 * @param <T>
 *            Representing generic definition
 *
 */
public class SkipListImpl<T extends Comparable<? super T>> implements
		SkipList<T> {

	// Size of node
	private int NODE_SIZE;

	// Total elements in list
	private int LIST_SIZE;

	// Instance representing the head of skip list
	private final SkipListNode<T> head;

	// Instance representing the iterator node
	private SkipListNode<T> iteratorNode;

	/**
	 * Default constructor to perform initial operations
	 */
	public SkipListImpl() {
		this.NODE_SIZE = 5;
		this.LIST_SIZE = 0;
		this.head = new SkipListNode(Long.MIN_VALUE, this.NODE_SIZE);
	}

	/**
	 * Main function that processes various cases for testing skip list
	 * 
	 * @param args
	 *            input params. Usually expecting a name of file
	 */

	public static void main(String[] args) {
		Scanner sc = null;
		String operation = "";
		long operand = 0;
		int modValue = 9907;
		long resultTree = 0, resultList = 0;
		long startTimeList, startTimeTree, endTimeTree, endTimeList, elapsedTimeTree, elapsedTimeList;

		if (args.length > 0) {
			File file = new File(args[0]);
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			sc = new Scanner(System.in);
		}

		// Initialize the tree set and skiplist.
		TreeSet<Long> tree = new TreeSet<Long>();
		SkipListImpl<Long> mSkipList = new SkipListImpl<Long>();

		startTimeTree = System.currentTimeMillis();

		// Read the file entries . Operation <operand>
		while (!((operation = sc.next()).equals("End"))) {
			switch (operation) {
			case "Insert":
			case "Add":
				operand = sc.nextLong();
				tree.add(operand);
				resultTree = (resultTree + 1) % modValue;
				break;
			case "Find":
			case "Contains":
				operand = sc.nextLong();
				if (tree.contains(operand)) {
					resultTree = (resultTree + 1) % modValue;
				}
				break;
			case "Delete":
			case "Remove":
				operand = sc.nextLong();
				if (tree.remove(operand)) {
					resultTree = (resultTree + 1) % modValue;
				}
				break;
			}
		}

		endTimeTree = System.currentTimeMillis();
		elapsedTimeTree = endTimeTree - startTimeTree;

		if (args.length > 0) {
			File file = new File(args[0]);
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			sc = new Scanner(System.in);
		}

		startTimeList = System.currentTimeMillis();

		// Read the file entries . Operation <operand>
		while (!((operation = sc.next()).equals("End"))) {
			switch (operation) {
			case "Insert":
			case "Add":
				operand = sc.nextLong();
				mSkipList.add(operand);
				resultList = (resultList + 1) % modValue;
				break;
			case "Find":
			case "Contains":
				operand = sc.nextLong();
				if (mSkipList.contains(operand)) {
					resultList = (resultList + 1) % modValue;
				}
				break;
			case "Delete":
			case "Remove":
				operand = sc.nextLong();
				if (mSkipList.remove(operand)) {
					resultList = (resultList + 1) % modValue;
				}
				break;
			}
		}

		endTimeList = System.currentTimeMillis();
		elapsedTimeList = endTimeList - startTimeList;

		System.out.println("Result after Tree operations: " + resultTree + " "
				+ elapsedTimeTree);
		System.out.println("Result after SkipList operation:	" + resultList
				+ " " + elapsedTimeList);
	}

	/**
	 * Print function to print the skiplist. Never called throughout the program
	 * but can be used to test the output of skiplist
	 * 
	 * @param head
	 *            The head of skiplist
	 */
	private void print(SkipListNode<T> head) {
		SkipListNode<T> counter;
		for (int i = this.NODE_SIZE; i >= 0; i--) {
			System.out.println();
			for (counter = this.head; counter != null; counter = counter.next[i]) {
				System.out.print(counter.data + " ---> ");
			}
		}
	}

	// --- Overrided methods ---

	/**
	 * Adds a new node to the skiplist at the right position
	 * 
	 * @param x
	 *            the element to be added
	 */
	@Override
	public void add(T x) {
		if (this.contains(x))
			return;
		int nodeLevel = 0;
		Random random = new Random();

		// Randomly select level of new node
		while (random.nextBoolean() == true)
			nodeLevel++;

		// If level of new node is greater than existing max level, resize
		if (nodeLevel > this.NODE_SIZE) {
			this.NODE_SIZE = nodeLevel;
			SkipListNode<T> newNext[] = (SkipListNode<T>[]) new SkipListNode[nodeLevel + 1];
			int i;
			for (i = 0; i < this.head.next.length; i++)
				newNext[i] = this.head.next[i];
			this.head.next = newNext;
		}

		// Find right position of x and insert
		SkipListNode<T> newNode = new SkipListNode<T>(x, nodeLevel);
		SkipListNode<T> counter = this.head;
		for (int i = this.NODE_SIZE; i >= 0; i--) {
			for (; counter.next[i] != null
					&& counter.next[i].data.compareTo(x) < 0; counter = counter.next[i])
				;
			if (i <= nodeLevel) {
				newNode.next[i] = counter.next[i];
				counter.next[i] = newNode;
			}
		}
		this.LIST_SIZE++;
	}

	/**
	 * Finds the least node greater than or equal to x
	 * 
	 * @param x
	 *            Data whose ceil has to be computed
	 * @return Data with value equal to ceil of x or null if not any
	 */
	@Override
	public T ceiling(T x) {
		if (!this.isEmpty()) {
			SkipListNode<T> counter = this.head;
			for (int i = this.NODE_SIZE; i >= 0; i--) {
				for (; counter.next[i] != null
						&& counter.next[i].data.compareTo(x) < 0; counter = counter.next[i])
					;
			}
			if (counter.next[0] != null)
				return counter.next[0].data;
		}
		return null;
	}

	/**
	 * Checks for presence of input in the list
	 * 
	 * @param x
	 *            data to be searched
	 * @return true if x is present in list, false if not
	 */
	@Override
	public boolean contains(T x) {
		if (!this.isEmpty()) {
			SkipListNode<T> counter = this.head;
			for (int i = this.NODE_SIZE; i >= 0; i--) {
				for (; counter.next[i] != null
						&& counter.next[i].data.compareTo(x) < 0; counter = counter.next[i])
					;
				if (counter.next[i] != null
						&& counter.next[i].data.compareTo(x) == 0)
					return true;
			}
		}
		return false;
	}

	/**
	 * Returns data present at specified index
	 * 
	 * @param n
	 *            index in the skiplist
	 * @return null if no such index is present or data present at n
	 */
	@Override
	public T findIndex(int n) {
		if (!this.isEmpty() && n <= this.LIST_SIZE && n > 0) {
			SkipListNode<T> counter = this.head.next[0];
			int index = 1;
			while (index < n) {
				index++;
				counter = counter.next[0];
			}
			return counter.data;
		}
		return null;
	}

	/**
	 * Returns first element of the list
	 * 
	 * @return The first element or null if list is empty
	 */
	@Override
	public T first() {
		if (!this.isEmpty())
			return this.head.next[0].data;
		return null;
	}

	/**
	 * Returns greatest element less than or equal to x
	 * 
	 * @param x
	 *            data whose floor has to be computed
	 * @return floor of x or null if no such element
	 */
	@Override
	public T floor(T x) {
		if (!this.isEmpty()) {
			SkipListNode<T> counter = this.head;
			for (int i = this.NODE_SIZE; i >= 0; i--) {
				for (; counter.next[i] != null
						&& counter.next[i].data.compareTo(x) <= 0; counter = counter.next[i])
					;
			}
			return counter.data;
		}
		return null;
	}

	/**
	 * checks if the list is empty
	 * 
	 * @return true if list is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.head.next[0] == null;
	}

	/**
	 * Returns iterator of the list
	 * 
	 * @return Instance of iterator
	 */
	@Override
	public Iterator<T> iterator() {
		this.iteratorNode = this.head.next[this.LIST_SIZE];
		return null;
	}

	/**
	 * Returns the last element of the list
	 * 
	 * @return the last element or null if list is empty
	 */
	@Override
	public T last() {
		if (this.isEmpty())
			return null;
		SkipListNode<T> counter = this.head;
		for (int i = this.NODE_SIZE; i >= 0; i--) {
			for (; counter.next[i] != null; counter = counter.next[i])
				;
		}
		return counter.data;
	}

	/**
	 * Rebuilds the skiplist into a perfect skiplist
	 */
	@Override
	public void rebuild() {
	}

	/**
	 * Removes an element from the skiplist
	 * 
	 * @param x
	 *            element to be removed
	 * @return true if x is removed, false otherwise
	 */
	@Override
	public boolean remove(T x) {
		boolean isFound = false;
		if (!this.isEmpty() && this.contains(x)) {
			SkipListNode<T> counter = this.head;
			for (int i = this.NODE_SIZE; i >= 0; i--) {
				for (; counter.next[i] != null
						&& counter.next[i].data.compareTo(x) < 0; counter = counter.next[i])
					;
				if (counter.next[i] != null
						&& counter.next[i].data.compareTo(x) == 0) {
					// Element found
					counter.next[i] = counter.next[i].next[i];
					isFound = true;
				}
			}
		}
		if (isFound)
			this.LIST_SIZE--;
		return isFound;
	}

	/**
	 * An integer representing the size of skiplist
	 * 
	 * @return Returns the size of skiplist
	 */
	@Override
	public int size() {
		return this.LIST_SIZE;
	}

}

/**
 * Class representing a skiplist node
 * 
 * @author darshanbidkar
 *
 * @param <T>
 *            Generic definition
 */
class SkipListNode<T extends Comparable<? super T>> {
	T data;
	SkipListNode<T> next[];

	/**
	 * Constructor that initialises data and level of the current node
	 * 
	 * @param data
	 *            data that the new node will have
	 * @param level
	 *            level of the node
	 */
	public SkipListNode(T data, int level) {
		this.data = data;
		this.next = (SkipListNode<T>[]) new SkipListNode[level + 1];
	}
}
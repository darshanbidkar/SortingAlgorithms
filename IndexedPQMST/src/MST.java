import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Denotes a class that computes the Minimum Spanning Tree using Prim's
 * algorithm
 * 
 * @author darshanbidkar
 *
 */
public class MST {
	static final int Infinity = Integer.MAX_VALUE;
	private static int phase = 0;
	private static long startTime, endTime, elapsedTime;

	/**
	 * Computes the minimum spanning tree and returns the minimum weight
	 * 
	 * @param g
	 *            the input graph on which prim's algorithm to run
	 * @return the minimum weight of the spanning tree
	 */
	static int PrimMST(Graph g) {

		g.initialize();
		int wmst = 0;
		// Create the priority queue index
		PriorityQueueIndexed<Graph.Vertex> pQIndexed = new PriorityQueueIndexed<Graph.Vertex>(
				g.V);

		// Initialise source
		Graph.Vertex src = g.V[1];
		src.weight = 0;

		Graph.Vertex u;
		while (!pQIndexed.isEmpty()) {
			// Get min
			u = pQIndexed.deleteMin();
			u.seen = true;
			// Add weight
			wmst += u.weight;
			// Process each adjacent notes
			for (Graph.Edge e : u.Adj) {
				Graph.Vertex v = e.otherEnd(u);
				if (!v.seen && e.Weight < v.weight) {
					// Found an edge with min weight
					v.parent = u;
					v.weight = e.Weight;
					pQIndexed.decreaseKey(v);
				}
			}
		}

		return wmst;
	}

	/**
	 * Main function
	 * 
	 * @param args
	 *            requires a command line argument specifying the input file
	 */
	public static void main(String[] args) {
		Scanner in;
		try {
			in = new Scanner(new File(args[0]));
			Graph g = Graph.readGraph(in);
			timer();
			int minWeight = PrimMST(g);
			timer();
			System.out.println(minWeight);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// --- Running Time and Space Functions

	/**
	 * Computes the running time
	 */
	public static void timer() {
		if (phase == 0) {
			startTime = System.currentTimeMillis();
			phase = 1;
		} else {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			System.out.println("Time: " + elapsedTime + " msec.");
			memory();
			phase = 0;
		}
	}

	/**
	 * Computes the memory requirements
	 */
	public static void memory() {
		long memAvailable = Runtime.getRuntime().totalMemory();
		long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		System.out.println("Memory: " + memUsed / 1000000 + " MB / "
				+ memAvailable / 1000000 + " MB.");
	}
}

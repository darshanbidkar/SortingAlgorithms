import java.util.Arrays;
import java.util.Random;

/**
 * 
 */

/**
 * @author darshanbidkar
 * 
 */
public class SortAnalysis {

	private static int size = 2000000;
	// private static int size = 15;
	private static int phase = 0;
	private static long startTime, endTime, elapsedTime;
	private final static int SORT_COND = 30;

	/**
	 * 
	 */
	public SortAnalysis() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] iarr = new Integer[size], cArr = new Integer[size], qArr = new Integer[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			iarr[i] = new Integer(rand.nextInt(10 * size));
		}
		cArr = Arrays.copyOf(iarr, iarr.length);
		qArr = Arrays.copyOf(iarr, iarr.length);

		System.out.println("\n\nSort analysis: Java");
		timer();
		Arrays.sort(cArr);
		timer();

		System.out.println("\n\nQuick sort Analysis: ");
		timer();
		SortAnalysis.quickSort(qArr, 0, qArr.length - 1);
		timer();

		System.out.println("\n\nMerge sort Analysis: ");
		timer();
		SortAnalysis.mergeSort(iarr, 0, iarr.length - 1, cArr);
		timer();
	}

	public static <T extends Comparable<? super T>> void mergeSort(T[] arr,
			int p, int r, T[] aux) {
		int n = r - p + 1;
		if (n <= SortAnalysis.SORT_COND) {
			SortAnalysis.insertionSort(arr, p, r);
		} else {
			int q = (p + r) / 2;
			SortAnalysis.mergeSort(arr, p, q, aux);
			SortAnalysis.mergeSort(arr, q + 1, r, aux);
			SortAnalysis.merge(arr, p, q, r, aux);
		}
	}

	public static <T extends Comparable<? super T>> void merge(T[] arr, int p,
			int q, int r, T[] aux) {
		int z;
		for (z = p; z <= r; z++)
			aux[z] = arr[z];
		int i, j, k;
		i = p;
		j = q + 1;
		for (k = p; k <= r; k++) {
			if ((j > r) || (i <= q && aux[i].compareTo(aux[j]) <= 0))
				arr[k] = aux[i++];
			else
				arr[k] = aux[j++];
		}
	}

	/**
	 * Source: Wikipedia `
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<? super T>> void insertionSort(T[] arr,
			int p, int r) {
		int i, j;
		for (i = p + 1; i <= r; i++) {
			j = i;
			while (j > p && arr[j - 1].compareTo(arr[j]) > 0) {
				SortAnalysis.exchange(arr, j, j - 1);
				j--;
			}
		}
	}

	public static <T extends Comparable<? super T>> void quickSort(T[] arr,
			int p, int r) {
		int n = r - p + 1;
		if (n <= SortAnalysis.SORT_COND) {
			SortAnalysis.insertionSort(arr, p, r);
		} else {
			int q = SortAnalysis.partition(arr, p, r);
			SortAnalysis.quickSort(arr, p, q - 1);
			SortAnalysis.quickSort(arr, q + 1, r);
		}
	}

	public static <T> void exchange(T[] arr, int i, int j) {
		T temp;
		temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static <T extends Comparable<? super T>> int partition(T[] arr,
			int p, int r) {
		int i = new Random().nextInt(r - p) + p;
		// Exchange arr[i] with arr[r]
		SortAnalysis.exchange(arr, i, r);
		T x = arr[r];
		i = p - 1;
		for (int j = p; j < r; j++) {
			if (arr[j].compareTo(x) <= 0) {
				i++;
				SortAnalysis.exchange(arr, i, j);
			}
		}
		SortAnalysis.exchange(arr, i + 1, r);
		return i + 1;
	}

	// --- Running Time and Space Functions

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

	public static void memory() {
		long memAvailable = Runtime.getRuntime().totalMemory();
		long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		System.out.println("Memory: " + memUsed / 1000000 + " MB / "
				+ memAvailable / 1000000 + " MB.");
	}

}

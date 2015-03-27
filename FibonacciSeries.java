import java.util.Scanner;

/**
 * 
 */

/**
 * @author darshanbidkar
 * 
 */
public class FibonacciSeries {

	// To measure time
	private static int phase = 0;
	private static long startTime, endTime, elapsedTime;

	/**
	 * 
	 */
	public FibonacciSeries() {

	}

	public void getFibInterativeSeries(long n, int p) {
		int fib = 0, fib1 = 0, fib2 = 1;
		for (long i = 2; i <= n; i++) {
			fib = (fib1 + fib2) % p;
			fib1 = fib2;
			fib2 = fib;
		}
	}

	public void getFibDACSeries(long n, int p) {
		long x[][] = { { 1, 1 }, { 1, 0 } };
		long temp[][] = this.power(x, n - 1, p);
		System.out.println(temp[0][0]);
	}

	private long[][] power(long[][] x, long n, int p) {
		if (n == 1) {
			// Base case
			return x;
		} else {
			long temp[][] = this.power(this.calculate(x, x, p), n / 2, p);
			if (n % 2 == 0) // even
				return temp;
			else
				// odd
				return this.calculate(temp, x, p);
		}
	}

	private long[][] calculate(long[][] x, long[][] y, int p) {
		int cols = x[0].length;
		int rows = y.length;

		if (rows != cols) {
			System.out
					.println("Error: cannot multiply matrices. incompatible size");
		}

		int limit = rows;
		rows = x.length;
		cols = y[0].length;

		long ans[][] = new long[rows][cols];
		// intialise array
		for (int i = 0; i < rows; i++) {
			ans[i] = new long[cols];
		}

		// multiply matrices
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int sum = 0, k;
				for (k = 0; k < limit; k++) {
					sum += (x[i][k] * y[k][j]) % p;
					sum %= p;
				}
				ans[i][j] = sum;
			}
		}
		return ans;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FibonacciSeries mSeries = new FibonacciSeries();
		int p;
		long n;
		if (args.length > 0 && args.length == 2) {
			n = Long.parseLong(args[0]);
			p = Integer.parseInt(args[1]);
		} else {
			System.out.print("\nEnter input (n, p): ");
			Scanner scanner = new Scanner(System.in);
			n = scanner.nextLong();
			p = scanner.nextInt();
			scanner.close();
		}
		timer();
		mSeries.getFibDACSeries(n, p);
		System.out.println("DAC: ");
		timer();
		System.out.println("Linear: ");
		timer();
		mSeries.getFibInterativeSeries(n, p);
		timer();
	}

	public static void memory() {
		long memAvailable = Runtime.getRuntime().totalMemory();
		long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		// System.out.println("Memory: " + memUsed / 1000000 + " MB / "
		// + memAvailable / 1000000 + " MB.");
	}

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
}

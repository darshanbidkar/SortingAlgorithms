import java.util.Random;

/**
 * 
 */

/**
 * @author darshanbidkar
 *
 */
public class MaxSubArraySum {

	/**
	 * 
	 */
	public MaxSubArraySum() {

	}

	public SubArraySum calculate(int input[], SubArraySum info) {
		if (info.p == info.r) {
			info.s = input[info.p];
			return info;
		}
		int q = (info.p + info.r) / 2;
		SubArraySum leftSub = calculate(input, new SubArraySum(info.p, q, 0));
		SubArraySum rightSub = calculate(input, new SubArraySum(q + 1, info.r,
				0));
		int lMax = input[q];
		int lSum = input[q];
		int lSi = q;
		for (int i = q - 1; i >= info.p; i--) {
			lSum += input[i];
			if (lSum > lMax) {
				lMax = lSum;
				lSi = i;
			}
		}
		int rMax = input[q + 1];
		int rSum = input[q + 1];
		int rPj = q + 1;
		for (int j = q + 2; j <= info.r; j++) {
			rSum += input[j];
			if (rSum > rMax) {
				rMax = rSum;
				rPj = j;
			}
		}
		int xs = lMax + rMax;
		if (leftSub.s >= max(rightSub.s, xs))
			return leftSub;
		else if (rightSub.s >= xs)
			return rightSub;
		else
			return new SubArraySum(lSi, rPj, xs);

	}

	int max(int i, int j) {
		return i > j ? i : j;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int input[] = { 3, 5, 2 };
		MaxSubArraySum obj = new MaxSubArraySum();
		SubArraySum s = new SubArraySum(0, input.length - 1, 0);
		System.out.println(obj.calculate(input, s).s);
	}
}

class SubArraySum {
	public SubArraySum() {
	}

	public SubArraySum(int p, int r, int s) {
		this.p = p;
		this.r = r;
		this.s = s;
	}

	int p, r, s;
}

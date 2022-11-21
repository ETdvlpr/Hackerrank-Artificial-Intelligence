/**
 * https://www.hackerrank.com/challenges/botcleanr
 */
package bot_clean.stochastic;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Solver {
	private Scanner sc;
	private PrintStream output;

	public void solve() {
		sc = new Scanner(System.in);
		output = new PrintStream(System.out);

		String[] loc = sc.nextLine().split(" ");

		ArrayList<String> grid = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			grid.add(sc.nextLine());
		}
		output.println(getNextMove(Integer.parseInt(loc[1]), Integer.parseInt(loc[0]), grid));
	}

	private int distance(int ax, int ay, int bx, int by) {
		return Math.abs(ax - bx) + Math.abs(ay - by);
	}

	private String getNextMove(int posc, int posr, ArrayList<String> grid) {
		int min = Integer.MAX_VALUE, minX = posc, minY = posr;

		for (int y = 0; y < grid.size(); y++) {
			for (int x = 0; x < grid.get(y).length(); x++) {
				if (grid.get(y).charAt(x) == 'd') {
					int distance = distance(posc, posr, x, y);
					if (distance < min) {
						min = distance;
						minX = x;
						minY = y;
					}
				}
			}
		}
		if (minX != posc) {
			return posc < minX ? "RIGHT" : "LEFT";
		} else if (minY != posr) {
			return posr < minY ? "DOWN" : "UP";
		}
		return "CLEAN";
	}
}

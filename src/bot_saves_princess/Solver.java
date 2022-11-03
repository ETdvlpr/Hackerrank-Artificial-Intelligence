/**
 * https://www.hackerrank.com/challenges/saveprincess
 */
package bot_saves_princess;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Solver {
	private Scanner sc = new Scanner(System.in);
	private ArrayList<String> grid;
	private PrintStream output = new PrintStream(System.out);

	public void solve() {
		readInput();
		int mx, my, px, py;
		mx = my = px = py = 0;
		for (int i = 0; i < grid.size(); i++) {
			String row = grid.get(i);
			if (row.contains("m")) {
				my = i;
				mx = row.indexOf("m");
			}
			if (row.contains("p")) {
				py = i;
				px = row.indexOf("p");
			}
		}
		for (int i = 0; i < Math.abs(my - py); i++) {
			output.println(my < py ? "DOWN" : "UP");
		}
		for (int i = 0; i < Math.abs(mx - px); i++) {
			output.println(mx < px ? "RIGHT" : "LEFT");
		}
	}

	private void readInput() {
		int n = Integer.parseInt(sc.nextLine());
		grid = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			grid.add(sc.nextLine());
		}
	}
}

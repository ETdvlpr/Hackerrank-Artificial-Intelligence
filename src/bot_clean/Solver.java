/**
 * https://www.hackerrank.com/challenges/botclean
 */
package bot_clean;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Solver {
	private Scanner sc;
	private PrintStream output;
	private ArrayList<String> grid;
	private Location robot;

	public class Location {
		int x, y;

		public Location(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public void solve() {
		sc = new Scanner(System.in);
		output = new PrintStream(System.out);
		readInput();
		getNextMove();
	}

	private int distance(Location a, Location b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	private void readInput() {
		String[] loc = sc.nextLine().split(" ");
		robot = new Location(Integer.parseInt(loc[1]), Integer.parseInt(loc[0]));

		grid = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			grid.add(sc.nextLine());
		}
	}

	private void getNextMove() {
		int min = Integer.MAX_VALUE;
		Location closest = robot;

		for (int y = 0; y < grid.size(); y++) {
			for (int x = 0; x < grid.get(y).length(); x++) {
				if (grid.get(y).charAt(x) == 'd') {
					int distance = distance(robot, new Location(x, y));
					if (distance < min) {
						min = distance;
						closest = new Location(x, y);
					}
				}
			}
		}
		if (closest.x == robot.x && closest.y == robot.y) {
			output.println("CLEAN");
		} else if (closest.x != robot.x) {
			output.println(robot.x < closest.x ? "RIGHT" : "LEFT");
		} else if (closest.y != robot.y) {
			output.println(robot.y < closest.y ? "DOWN" : "UP");
		}
	}
}

/**
 * https://www.hackerrank.com/challenges/botcleanv2
 */
package bot_clean.partially_observable;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Solver {
	private Scanner sc;
	private PrintStream output;
	private ArrayList<String> memmory;
	private ArrayList<String> grid;
	private Location robot;
	private String memmoryFileName = "memmory.dat";
	private double exploreRate = 0.5;

	public void solve() {
		sc = new Scanner(System.in);
		output = new PrintStream(System.out);

		readInput();
		readFromMemmory();
		updateMemmory();
		writeToMemmory();

		output.println(getNextMove());
	}

	public class Location {
		int x, y;

		public Location(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return String.format("(%d,%d)", x, y);
		}
	}

	private void readInput() {
		String[] loc = sc.nextLine().split(" ");
		robot = new Location(Integer.parseInt(loc[1]), Integer.parseInt(loc[0]));

		grid = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			grid.add(sc.nextLine());
		}
	}

	private int distance(Location a, Location b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	private String getNextMove() {
		int minDirtDistance = Integer.MAX_VALUE;
		Location closestDirt = new Location(robot.x, robot.y);

		for (int y = 0; y < memmory.size(); y++) {
			for (int x = 0; x < memmory.get(y).length(); x++) {
				if (memmory.get(y).charAt(x) == 'd') {
					int distance = distance(robot, new Location(x, y));
					if (distance < minDirtDistance) {
						minDirtDistance = distance;
						closestDirt = new Location(x, y);
					}
				}
			}
		}
		if (minDirtDistance == 0) {
			return "CLEAN";
		}

		int minUndiscoveredDistance = Integer.MAX_VALUE;
		Location closestUnknown = new Location(robot.x, robot.y);
		for (int y = 0; y < memmory.size(); y++) {
			for (int x = 0; x < memmory.get(y).length(); x++) {
				if (memmory.get(y).charAt(x) == 'o') {
					int distance = distance(robot, new Location(x, y));
					if (distance < minUndiscoveredDistance) {
						minUndiscoveredDistance = distance;
						closestUnknown = new Location(x, y);
					}
				}
			}
		}

		if (exploreRate * minDirtDistance > minUndiscoveredDistance) {
			return navigateTo(closestUnknown);
		} else {
			return navigateTo(closestDirt);
		}
	}

	private String navigateTo(Location newLocation) {
		if (newLocation.x != robot.x) {
			return robot.x < newLocation.x ? "RIGHT" : "LEFT";
		} else if (newLocation.y != robot.y) {
			return robot.y < newLocation.y ? "DOWN" : "UP";
		}
		return "CLEAN";
	}

	private void updateMemmory() {
		if (memmory.isEmpty()) {
			memmory = new ArrayList<>(grid);
		}
		ArrayList<String> newMemmory = new ArrayList<>();
		for (int i = 0; i < grid.size(); i++) {
			String row = "";
			for (int j = 0; j < grid.get(i).length(); j++) {
				row += (grid.get(i).charAt(j) != 'o') ? grid.get(i).charAt(j) : memmory.get(i).charAt(j);
			}
			newMemmory.add(row);
		}
		memmory = newMemmory;
	}

	private void readFromMemmory() {
		memmory = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(memmoryFileName));
			for (String line : reader.lines().toArray(String[]::new)) {
				memmory.add(line);
			}
			reader.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	private void writeToMemmory() {
		try (PrintStream out = new PrintStream(new FileOutputStream(memmoryFileName))) {
			for (String text : memmory) {
				out.println(text);
			}
			out.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
}

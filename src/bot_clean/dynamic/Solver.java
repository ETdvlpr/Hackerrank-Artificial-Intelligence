/**
 * https://www.hackerrank.com/challenges/botcleanlarge
 * code adopted from Justin Fly @justinfly
 */
package bot_clean.dynamic;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
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

		int distance(Location other) {
			return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Location loc = (Location) o;
			return x == loc.x && y == loc.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public String toString() {
			return String.format("(%d,%d)", x, y);
		}
	}

	final class Path implements Comparable<Path> {

		private final Location target;

		private final Location current;

		private final int distance;

		private final HashSet<Location> visited = new HashSet<>();

		public Path(Path parent, Location current) {
			this(parent.target, current, parent.distance + parent.current.distance(current));
			visited.addAll(parent.visited);
		}

		public Path(Location target, Location current, int distance) {
			this.target = target;
			this.current = current;
			this.distance = distance;
			visited.add(current);
		}

		@Override
		public int compareTo(Path that) {
			if (this.distance > that.distance) {
				return -1;
			} else if (this.distance < that.distance) {
				return 1;
			}
			return 0;
		}

		boolean visited(Location Location) {
			return visited.contains(Location);
		}

		public Location getTarget() {
			return new Location(target.x, target.y);
		}
	}

	private final List<Location> dirtyCells = new LinkedList<>();
	private final Queue<Path> closest = new PriorityQueue<>();
	private Location target;

	public void visitStart() {
		if (!dirtyCells.isEmpty()) {
			dirtyCells.clear();
		}
	}

	public void visit(int x, int y, char type) {
		if (type == 'd') {
			Location translation = new Location(x, y);
			dirtyCells.add(translation);
			closest.add(new Path(translation, translation, robot.distance(translation)));
			if (dirtyCells.size() > 20) {
				while (closest.size() > 1) {
					closest.poll();
				}
			} else if (dirtyCells.size() > 12) {
				while (closest.size() > 2) {
					closest.poll();
				}
			} else {
				while (closest.size() > 3) {
					closest.poll();
				}
			}
		}
	}

	public void visitEnd() {
		if (closest.size() == 1) {
			target = closest.poll().getTarget();
		} else {
			Path winner = null;
			int maxExpand = closest.size();
			LinkedList<Path> open = new LinkedList<>(closest);
			closest.clear();
			while (!open.isEmpty()) {
				Path nextToExpand = open.pollLast();
				for (Location filthyCell : dirtyCells) {
					if (nextToExpand.visited(filthyCell)) {
						continue;
					}
					Path e = new Path(nextToExpand, filthyCell);
					closest.add(e);
					if (closest.size() > maxExpand) {
						closest.poll();
					}
				}
				if (!closest.isEmpty()) {
					open.addAll(closest);
					closest.clear();
				} else if (winner == null || winner.compareTo(nextToExpand) < 0) {
					winner = nextToExpand;
				}
			}
			target = winner != null ? winner.getTarget() : null;
		}
	}

	public void solve() {
		sc = new Scanner(System.in);
		output = new PrintStream(System.out);
		readInput();
		visitStart();
		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(0).length(); j++) {
				visit(j, i, grid.get(i).charAt(j));
			}
		}
		visitEnd();
		if(target != null) {
			output.println(getDirection(robot,target));
		}
	}

	private void readInput() {
		String[] loc = sc.nextLine().split(" ");
		String[] gridSize = sc.nextLine().split(" ");
		robot = new Location(Integer.parseInt(loc[1]), Integer.parseInt(loc[0]));

		grid = new ArrayList<>();
		for (int i = 0; i < Integer.parseInt(gridSize[0]); i++) {
			grid.add(sc.nextLine());
		}
	}

	private String getDirection(Location a, Location b) {
		if(a.equals(b)) return "CLEAN";
		if (b.x != a.x) {
			return a.x < b.x ? "RIGHT" : "LEFT";
		}
		return a.y < b.y ? "DOWN" : "UP";
	}
}

import bot_clean.Solver;

/**
 * 
 * @author dave
 *
 */
public class Main {
	public static void main(String[] args) {
		Solver sol = new Solver();
		try {
			sol.solve();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

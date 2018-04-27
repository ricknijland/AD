import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	
	private static String node1 = "Wexford", node2 = "Doolin"; // Makes testing easier

	public static void main(String[] args) throws FileNotFoundException {
		List<String> avoidNodes = new ArrayList<>();
		avoidNodes.add("Waterford");
		
		Paths path = new Paths(avoidNodes);
		
		path.findQuickestPath(node1, node2);
		path.findShortestPath(node1, node2);

	}
}

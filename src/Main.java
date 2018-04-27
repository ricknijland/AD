import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	
	private static String node1 = "Dublin", node2 = "Galway"; // Makes testing easier

	public static void main(String[] args) throws FileNotFoundException {
		List<String> avoidNodes = new ArrayList<>();
		List<String> avoidLinks = new ArrayList<>();
		List<String> waypoints = new ArrayList<>();
		
		waypoints.add("Waterford");
		waypoints.add("Cork");
		
		Paths path = new Paths(avoidNodes, avoidLinks, waypoints);
		
		path.findQuickestPath(node1, node2);
		path.findShortestPath(node1, node2);

	}
}

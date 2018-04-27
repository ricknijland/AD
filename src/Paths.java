import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class Paths {

	// Variables
	private List<GraphNode<?>> avoidNodes;
	private List<String> avoidLinks, waypoints;
	protected CityList nodes;
	private Dijkstra dijkstra = new Dijkstra();
	
	// Default Constructor
	public Paths() { 
		try {
			nodes = new CityList();
		}catch(FileNotFoundException ex){}
	}
	
	// Constructor that takes a list of cities to avoid and instantiates CityList
	public Paths(List<String> avoidNodes, List<String> avoidLinks, List<String> waypoints) {
		try {
			nodes = new CityList();
		}catch(FileNotFoundException ex){}
		
		this.avoidNodes = new ArrayList<>();
		for(String node : avoidNodes) {
			this.avoidNodes.add(nodes.get(node)); // Add all nodes to be avoided into list
		}
		this.avoidLinks = new ArrayList<>();
		this.avoidLinks.addAll(avoidLinks); // Add all links to be avoided into list
		this.waypoints = new ArrayList<>();
		this.waypoints.addAll(waypoints); // Add all waypoints to hit on route into list
	}
	
	// Method to find quickest route
	public CostedPath findQuickestPath(String startNode, String destNode) {
		CostedPath quickest = new CostedPath();
		
		if(!waypoints.isEmpty()) { // If there are waypoints that need to be hit on route
			List<CostedPath> paths = new ArrayList<>(); // List to hold all CostedPaths to be added together
			waypoints.add(0, startNode); // Add to front
			waypoints.add(destNode); // Add to end
			
			// Find the path using each waypoint
			for(int i = 1; i < waypoints.size(); i++) {
				paths.add(dijkstra.findQuickestPath(nodes.get(waypoints.get(i-1)), nodes.get(waypoints.get(i)).data, avoidNodes, avoidLinks));
			}
			// Add all paths to one valid path
			for(CostedPath path : paths) {
				if(!path.equals(paths.get(0))) path.pathList.remove(0); // Removes first node, since it will repeat with next CostedPath
				quickest.pathCost += path.pathCost;
				quickest.pathList.addAll(path.pathList);
			}
			
		}
		else { // If only 2 nodes to find route for
			quickest = dijkstra.findQuickestPath(nodes.get(startNode), destNode, avoidNodes, avoidLinks);
		}
		
//		// Print out nodes and links
//		for(int j = 0; j < quickest.pathList.size(); j++){ // Cycles thru each node
//			System.out.println(quickest.pathList.get(j).data); // Print out each node
//			for(int i = 0; i < quickest.pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
//				try{
//					if(quickest.pathList.get(j).adjList.get(i).destNode.equals(quickest.pathList.get(j+1))) // If this link brings us to next destNode
//						System.out.println("Take " + quickest.pathList.get(j).adjList.get(i).name + " for " + 
//							quickest.pathList.get(j).adjList.get(i).length + "km to " + quickest.pathList.get(j+1).data + "."); // Print out the chosen node (if only one route, then the 0 index will be that route)
//					}catch(IndexOutOfBoundsException ex){}
//			}
//		}
//		System.out.printf("Total time: %.2f minutes.\n\n", quickest.pathCost);
		
		return quickest;
	}
	
	// Method to find shortest route
	public CostedPath findShortestPath(String startNode, String destNode) {
		CostedPath shortest = new CostedPath();
		
		if(!waypoints.isEmpty()) { // If there are waypoints that need to be hit on route
			List<CostedPath> paths = new ArrayList<>(); // List to hold all CostedPaths to be added together
			waypoints.add(0, startNode); // Add to front
			waypoints.add(destNode); // Add to end
			
			// Find the path using each waypoint
			for(int i = 1; i < waypoints.size(); i++) {
				paths.add(dijkstra.findShortestPath(nodes.get(waypoints.get(i-1)), nodes.get(waypoints.get(i)).data, avoidNodes, avoidLinks));
			}
			// Add all paths to one valid path
			for(CostedPath path : paths) {
				if(!path.equals(paths.get(0))) path.pathList.remove(0); // Removes first node, since it will repeat with next CostedPath
				shortest.pathCost += path.pathCost;
				shortest.pathList.addAll(path.pathList);
			}
			
		}
		else { // If only 2 nodes to find route for
			shortest = dijkstra.findShortestPath(nodes.get(startNode), destNode, avoidNodes, avoidLinks);
		}
		// Print out nodes and links
//		for(int j = 0; j < shortest.pathList.size(); j++){ // Cycles thru each node
//			System.out.println(shortest.pathList.get(j).data); // Print out each node
//			for(int i = 0; i < shortest.pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
//				try{
//					if(shortest.pathList.get(j).adjList.get(i).destNode.equals(shortest.pathList.get(j+1))) // If this link brings us to next destNode
//						System.out.println("Take " + shortest.pathList.get(j).adjList.get(i).name + " for " + 
//								shortest.pathList.get(j).adjList.get(i).length + "km to " + shortest.pathList.get(j+1).data + "."); // Print out the chosen node (if only one route, then the 0 index will be that route)
//				}catch(IndexOutOfBoundsException ex){}
//			}
//		}
//		System.out.printf("Total distance: %.2f km.\n\n", shortest.pathCost);
		
		return shortest;
	}
	
}

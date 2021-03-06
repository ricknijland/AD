import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;

public class Paths {

	// Variables
	private List<GraphNode<?>> avoidNodes;
	private List<String> avoidLinks, waypoints;
	protected CityList nodes;
	private Dijkstra dijkstra = new Dijkstra();
	private CostedPath shortest, quickest;
	private List<CostedPath> multi;
	
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
		quickest = new CostedPath();
		
		// Check to throw exception
		if(nodes.get(startNode) == (null) || nodes.get(destNode) == (null)) {
			return null;
		}
		
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
		
		return quickest;
	}
	
	// Method to find quickest route
	public List<CostedPath> findMultiplePaths(String startNode, String destNode, int pathNum) {
		multi = new ArrayList<>();
		
		// Check to throw exception
		if(nodes.get(startNode) == (null) || nodes.get(destNode) == (null)) {
			return null;
		}
		
//		if(!waypoints.isEmpty()) { // If there are waypoints that need to be hit on route
//			List<CostedPath> paths = new ArrayList<>(); // List to hold all CostedPaths to be added together
//			waypoints.add(0, startNode); // Add to front
//			waypoints.add(destNode); // Add to end
//			
//			// Find the path using each waypoint
//			for(int i = 1; i < waypoints.size(); i++) {
//				paths.add(dijkstra.findQuickestPath(nodes.get(waypoints.get(i-1)), nodes.get(waypoints.get(i)).data, avoidNodes, avoidLinks));
//			}
//			// Add all paths to one valid path
//			for(CostedPath path : paths) {
//				if(!path.equals(paths.get(0))) path.pathList.remove(0); // Removes first node, since it will repeat with next CostedPath
//				multi.pathCost += path.pathCost;
//				multi.pathList.addAll(path.pathList);
//			}
//			
//		}
//		else { // If only 2 nodes to find route for
		multi = dijkstra.findMultiplePaths(nodes.get(startNode), destNode, avoidNodes, avoidLinks, pathNum);
//		}
		
		return multi;
	}
	
	// Method to find shortest route
	public CostedPath findShortestPath(String startNode, String destNode) {
		shortest = new CostedPath();
		
		// Check to throw exception
		if(nodes.get(startNode) == (null) || nodes.get(destNode) == (null)) {
			return null;
		}
		
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
		
		return shortest;
	}
	
	// Method for getting route for route Label
	public List<Label> getQuickestRouteString() {
		List<Label> labels = new ArrayList<>();
		
		// Print out nodes and links
		for(int j = 0; j < quickest.pathList.size(); j++) { // Cycles thru each node
			for(int i = 0; i < quickest.pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
				try{
					if(quickest.pathList.get(j).adjList.get(i).destNode.equals(quickest.pathList.get(j+1))) { // If this link brings us to next destNode
						labels.add(new Label("Take " + quickest.pathList.get(j).adjList.get(i).name + " for " + quickest.pathList.get(j).adjList.get(i).length +
							"km from " + quickest.pathList.get(j).data + " to " + quickest.pathList.get(j+1).data + ".")); // Add link to route
					}
				}catch(IndexOutOfBoundsException ex){}
			}
		}
		// Total then add to route Label in Main
		labels.add(new Label(""));
		labels.add(new Label("Total time: ~" + (int)quickest.pathCost + " minutes [~" + (int)quickest.pathCost/60 + " hour(s) and "+ (int)quickest.pathCost%60 + " minutes]."));
		
		return labels;
	}
		
	// Method to return list of directions for route
	public List<Label> getShortestRouteString() {
		List<Label> labels = new ArrayList<>();
		
		// Print out nodes and links
		for(int j = 0; j < shortest.pathList.size(); j++) { // Cycles thru each node
			for(int i = 0; i < shortest.pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
				try{
					if(shortest.pathList.get(j).adjList.get(i).destNode.equals(shortest.pathList.get(j+1))) { // If this link brings us to next destNode
						labels.add(new Label("Take " + shortest.pathList.get(j).adjList.get(i).name + " for " + shortest.pathList.get(j).adjList.get(i).length + 
							"km from " + shortest.pathList.get(j).data + " to " + shortest.pathList.get(j+1).data + ".")); // Add link to route
					}
				}catch(IndexOutOfBoundsException ex){}
			}
		}
		// Total then add to route Label in Main
		labels.add(new Label(""));
		labels.add(new Label("Total distance: ~" + (int)shortest.pathCost + " km."));
		
		return labels;
	}
	
	// Method to return list of directions for route
	public List<Label> getMultipleRoutesString(int pathNums) {
		List<Label> labels = new ArrayList<>();
		if(multi == null) {
			labels.add(new Label("Too many paths or couldn't find path."));
			return labels;
		}
		
		// Print out nodes and links
		for(int k = 0; k < pathNums; k++) {
			labels.add(new Label("Path " + (k+1) + ":"));
			for(int j = 0; j < multi.get(k).pathList.size(); j++) { // Cycles thru each node
				for(int i = 0; i < multi.get(k).pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
					try{
						if(multi.get(k).pathList.get(j).adjList.get(i).destNode.equals(multi.get(k).pathList.get(j+1))) { // If this link brings us to next destNode
							labels.add(new Label("Take " + multi.get(k).pathList.get(j).adjList.get(i).name + " for " + multi.get(k).pathList.get(j).adjList.get(i).length + 
								"km from " + multi.get(k).pathList.get(j).data + " to " + multi.get(k).pathList.get(j+1).data + ".")); // Add link to route
						}
					}catch(IndexOutOfBoundsException ex){}
				}
			}
			// Total then get next directions
			labels.add(new Label(""));
			labels.add(new Label("Total time: ~" + (int)multi.get(k).pathCost + " minutes [~" + (int)multi.get(k).pathCost/60 + " hour(s) and "+ (int)multi.get(k).pathCost%60 + " minutes]."));
			labels.add(new Label(""));
		}
		
		return labels;
	}
	
}

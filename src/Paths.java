import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Paths {

	// Variables
	private List<GraphNode<?>> avoidNodes;
	private CityList nodes;
	private Dijkstra dijkstra = new Dijkstra();
	private int node1, node2;
	
	// Default Constructor
	public Paths() { 
		try {
			nodes = new CityList();
		}catch(FileNotFoundException ex){}
		
		this.node1 = -1; 
		this.node2 = -1;
	}
	
	// Constructor that takes a list of cities to avoid and instantiates CityList
	public Paths(List<String> avoidNodes) {
		try {
			nodes = new CityList();
		}catch(FileNotFoundException ex){}
		
		this.node1 = -1; 
		this.node2 = -1;
		this.avoidNodes = new ArrayList<>();
		for(String node : avoidNodes) {
			this.avoidNodes.add(nodes.get(node));
		}
		
	}
	
	// Method to find quickest route
	public void findQuickestPath(String startNode, String destNode) {
		CostedPath quickest = dijkstra.findQuickestPath(nodes.get(startNode), nodes.get(destNode).data, avoidNodes);
		// In case path not found
		try {
			if(quickest.pathList.equals(null))
				return;
		}catch(NullPointerException ex) {
			System.out.println("Sorry, could not find a proper route.");
			return;
		}
		for(int j = 0; j < quickest.pathList.size(); j++){ // Cycles thru each node
			System.out.println(quickest.pathList.get(j).data); // Print out each node
			for(int i = 0; i < quickest.pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
				try{
					if(quickest.pathList.get(j).adjList.get(i).destNode.equals(quickest.pathList.get(j+1))) // If this link brings us to next destNode
						System.out.println("Take " + quickest.pathList.get(j).adjList.get(i).name + " for " + 
								quickest.pathList.get(j).adjList.get(i).length + "km to " + quickest.pathList.get(j+1).data + "."); // Print out the chosen node (if only one route, then the 0 index will be that route)
				}catch(IndexOutOfBoundsException ex){}
			}
		}
		System.out.printf("Total time: %.2f minutes.\n\n", quickest.pathCost);
	}
	
	// Method to find shortest route
	public void findShortestPath(String startNode, String destNode) {
		CostedPath shortest = dijkstra.findShortestPath(nodes.get(startNode), nodes.get(destNode).data, avoidNodes);
		try {
			if(shortest.pathList.equals(null))
				return;
		}catch(NullPointerException ex) {
			System.out.println("Sorry, could not find a proper route.");
			return;
		}
		for(int j = 0; j < shortest.pathList.size(); j++){ // Cycles thru each node
			System.out.println(shortest.pathList.get(j).data); // Print out each node
			for(int i = 0; i < shortest.pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
				try{
					if(shortest.pathList.get(j).adjList.get(i).destNode.equals(shortest.pathList.get(j+1))) // If this link brings us to next destNode
						System.out.println("Take " + shortest.pathList.get(j).adjList.get(i).name + " for " + 
								shortest.pathList.get(j).adjList.get(i).length + "km to " + shortest.pathList.get(j+1).data + "."); // Print out the chosen node (if only one route, then the 0 index will be that route)
				}catch(IndexOutOfBoundsException ex){}
			}
		}
		System.out.printf("Total distance: %.2f km.\n\n", shortest.pathCost);
	}
	
	
}

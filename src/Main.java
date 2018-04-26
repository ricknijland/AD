import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	
	private static String node1 = "Waterford", node2 = "Cork";

	public static void main(String[] args) throws FileNotFoundException {
		CityList nodes = new CityList(); // Holds list of cities and other type of nodes
		List<GraphLink> routes = new ArrayList<>(); // Holds different routes to same destination
		Dijkstra d = new Dijkstra();
		
		CostedPath shortest = d.findShortestPath(nodes.get(node1), nodes.get(node2).data);
		for(int j = 0; j < shortest.pathList.size(); j++){ // Cycles thru each node
			System.out.println(shortest.pathList.get(j).data); // Print out each node
			routes.clear(); // Clear data from before
			for(int i = 0; i < shortest.pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
				try{
					if(shortest.pathList.get(j).adjList.get(i).destNode.equals(shortest.pathList.get(j+1))) // If this link brings us to next destNode
						routes.add(shortest.pathList.get(j).adjList.get(i)); // Add to routes list
				}catch(IndexOutOfBoundsException ex){}
			}
			try{
				Collections.sort(routes, (p1, p2)-> (int)(p1.length - p2.length)); // Sorts by smallest time, so 0 index should be quickest
				System.out.println("Take " + routes.get(0).name + " for " + routes.get(0).length + "km to " + shortest.pathList.get(j+1).data + "."); // Print out the chosen node (if only one route, then the 0 index will be that route)
			}catch(IndexOutOfBoundsException ex){}
		}
		System.out.printf("Total distance: %.2f km.\n\n", shortest.pathCost);
		
		//Finds quickest path
		CostedPath quickest = d.findQuickestPath(nodes.get(node1), nodes.get(node2).data);
		routes = new ArrayList<>(); // Holds different routes to same destination
		for(int j = 0; j < quickest.pathList.size(); j++){ // Cycles thru each node
			System.out.println(quickest.pathList.get(j).data); // Print out each node
			routes.clear(); // Clear data from before
			for(int i = 0; i < quickest.pathList.get(j).adjList.size(); i++) { // Shuffle through different links in each nodes' adjList
				try{
					if(quickest.pathList.get(j).adjList.get(i).destNode.equals(quickest.pathList.get(j+1))) // If this link brings us to next destNode
						routes.add(quickest.pathList.get(j).adjList.get(i)); // Add to routes list
				}catch(IndexOutOfBoundsException ex){}
			}
			try{
				Collections.sort(routes, (p1, p2)-> (int)(p1.time - p2.time)); // Sorts by smallest time, so 0 index should be quickest
				System.out.println("Take " + routes.get(0).name + " for " + routes.get(0).length + "km to " + shortest.pathList.get(j+1).data + "."); // Print out the chosen node (if only one route, then the 0 index will be that route)
			}catch(IndexOutOfBoundsException ex){}
		}
		System.out.printf("Total time: %.2f minutes.", quickest.pathCost);

	}
}

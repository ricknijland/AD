import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CityList {

	// Variables
	private String[] CSVdata;
	private List<GraphNode<String>> nodes = new ArrayList<>();

	// Constructor that adds all nodes to nodes(readNodes()) and connect them via links
	public CityList() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("Resources\\MapOfIreland.csv"));
		readNodes(); // Call method to read in all nodes
		sc.nextLine(); //Skip first line of headers
		while(sc.hasNext()) {
			CSVdata = sc.nextLine().split(",");
			int link1 = -1, link2 = -1; // Holds index of two nodes to be connected
			for(int i = 0; i < nodes.size(); i++) {
				if(nodes.get(i).data.equals(CSVdata[2])) // If node data == first node to be connected
					link1 = i;
				if(nodes.get(i).data.equals(CSVdata[3])) // If node data == second node to be connected
					link2 = i;
			}
			// If one or both nodes weren't found
			if(link1 < 0 || link2 < 0) {
				System.out.println("ERROR: ONE OR BOTH NODES DO NOT EXIST");
				System.out.println(CSVdata[2] + "|" + CSVdata[3]);
				break;
			}
			// Connect two links
			else
				nodes.get(link1).connectToNodeUndirected(nodes.get(link2), Double.parseDouble(CSVdata[1]), CSVdata[0]);
		}
		sc.close();
	}
	
	
	// Create Nodes and Links for Map
	private void readNodes() throws FileNotFoundException{
		Scanner sc = new Scanner(new File("Resources\\ListOfNodes.csv"));
		sc.nextLine(); //Skip first line of headers
		while(sc.hasNext()) { 
			CSVdata = sc.nextLine().split(","); // Add contents of each row to String[]
			nodes.add(new GraphNode<String>(CSVdata[0], CSVdata[1])); // Add new node to object list
		}
		sc.close();
	}
	
	// Getter for nodes
	public GraphNode<?> get(int index) {
		return nodes.get(index);
	}
	
	// Getter for nodes by String name
	public GraphNode<?> get(String name) {
		for(GraphNode<?> node : nodes) {
			if(node.data.equals(name))
				return node;
		}
		return null;
	}
	
	public GraphNode<?> getNodeWithString(String name) {
		for(int i = 0; i < nodes.size(); i++) {
			if(name.equals(nodes.get(i).data));
				return nodes.get(i); 
		}
		return null;
	}
	
	// Getter for size of node list
	public int size() {
		return nodes.size();
	}
}
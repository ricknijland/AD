import java.util.ArrayList;
import java.util.List;

public class GraphNode<T> {
	public T data;
	public T type;
	public double nodeValue = Double.MAX_VALUE;
	
	public List<GraphLink> adjList=new ArrayList<>();
	
	public GraphNode(T data, T type){
		this.data = data;
		this.type = type;
	}

	public void connectToNodeDirected(GraphNode<T> destNode, double length, String name) {
		adjList.add(new GraphLink(destNode, length, name));
	}
	
	public void connectToNodeUndirected(GraphNode<T> destNode, double length, String name) {
		adjList.add(new GraphLink(destNode, length, name));
		destNode.adjList.add(new GraphLink(this, length, name));
	}
	
}

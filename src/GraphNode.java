import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphNode<T> {
	public T data;
	public double nodeValue = Double.MAX_VALUE;
	
	public List<GraphLink<?>> adjList=new ArrayList<>();
	
	public GraphNode(T data){
		this.data = data;
	}
	
<<<<<<< HEAD
	public void connectToNodeDirected(GraphNode<T> destNode, double length, String name) {
		adjList.add(new GraphLink(destNode, length, name));
	}
	
	public void connectToNodeUndirected(GraphNode<T> destNode, double length, String name) {
		adjList.add(new GraphLink(destNode, length, name));
		destNode.adjList.add(new GraphLink(this, length, name));
	}
	
=======
	public void connectToNodeDirected(GraphNode<T> destNode, double length, char speed) {
		adjList.add(new GraphLink(destNode,length, speed));
	}
	
	public void connectToNodeUndirected(GraphNode<T> destNode, double length, char speed) {
		adjList.add(new GraphLink(destNode, length, speed));
		destNode.adjList.add(new GraphLink(this, length, speed));
	}
	
	public static <T> Dijkstra findCheapestPathDijkstra(GraphNode<?> startNode, T lookingFor) {
		Dijkstra cp = new Dijkstra();
		List<GraphNode<?>> encountered=new ArrayList<>(), unencountered=new ArrayList<>();
		startNode.nodeValue=0;
		unencountered.add(startNode);
		GraphNode<?> currentNode;
		
		do {
			currentNode=unencountered.remove(0);
			encountered.add(currentNode);
			
			if(currentNode.data.equals(lookingFor)) {
				cp.pathList.add(currentNode);
				cp.pathCost=currentNode.nodeValue;
				
				while(currentNode!=startNode) {
					boolean foundPrevPathNode=false;
					for(GraphNode<?> n : encountered) {
						for(GraphLink e : n.adjList) {
							if(e.destNode==currentNode && currentNode.nodeValue-e.cost==n.nodeValue) {
								cp.pathList.add(0,n);
								currentNode=n;
								foundPrevPathNode=true;
								break;
							}
						}
						if(foundPrevPathNode) break;
					}
				}
				
				for(GraphNode<?> n : encountered) n.nodeValue=Integer.MAX_VALUE;
				for(GraphNode<?> n : unencountered) n.nodeValue=Integer.MAX_VALUE;
				
				return cp;
			}
			
			for(GraphLink e : currentNode.adjList)
				if(!encountered.contains(e.destNode)) {
					e.destNode.nodeValue=Integer.min(e.destNode.nodeValue, (int) (currentNode.nodeValue+e.cost));
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered,(n1,n2)->n1.nodeValue-n2.nodeValue);
		}while(!unencountered.isEmpty());
		
		
		return null;
	}
>>>>>>> 713a5bb397fe8743530e7c614fe3638ef57f3d1a
}

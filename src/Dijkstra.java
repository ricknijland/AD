import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {
	
	public <T> CostedPath findQuickestPath(GraphNode<?> startNode, T lookingFor) {
		CostedPath cp = new CostedPath();
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
							if(e.destNode==currentNode && currentNode.nodeValue-e.time==n.nodeValue) {
								cp.pathList.add(0,n);
								currentNode=n;
								foundPrevPathNode=true;
								break;
							}
						}
						if(foundPrevPathNode) break;
					}
				}
				
				for(GraphNode<?> n : encountered) n.nodeValue=Double.MAX_VALUE;
				for(GraphNode<?> n : unencountered) n.nodeValue=Double.MAX_VALUE;
				
				return cp;
			}
			
			for(GraphLink e : currentNode.adjList)
				if(!encountered.contains(e.destNode)) {
					e.destNode.nodeValue=Double.min(e.destNode.nodeValue, currentNode.nodeValue+e.time);
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered,(n1,n2)->(int)(n1.nodeValue-n2.nodeValue));
		}while(!unencountered.isEmpty());
		
		return null;
	}
	
	public <T> CostedPath findShortestPath(GraphNode<?> startNode, T lookingFor) {
		CostedPath cp = new CostedPath();
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
							if(e.destNode==currentNode && currentNode.nodeValue-e.length==n.nodeValue) {
								cp.pathList.add(0,n);
								currentNode=n;
								foundPrevPathNode=true;
								break;
							}
						}
						if(foundPrevPathNode) break;
					}
				}
				
				for(GraphNode<?> n : encountered) n.nodeValue=Double.MAX_VALUE;
				for(GraphNode<?> n : unencountered) n.nodeValue=Double.MAX_VALUE;
				
				return cp;
			}
			
			for(GraphLink e : currentNode.adjList)
				if(!encountered.contains(e.destNode)) {
					e.destNode.nodeValue=Double.min(e.destNode.nodeValue, currentNode.nodeValue+e.length);
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered,(n1,n2)->(int)(n1.nodeValue-n2.nodeValue));//FIXME
		}while(!unencountered.isEmpty());
		
		return null;
	}
	
}
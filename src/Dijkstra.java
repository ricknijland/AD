import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {
	
	public <T> CostedPath findQuickestPath(GraphNode<?> startNode, T lookingFor, List<GraphNode<?>> avoidNodes, List<String> avoidLinks) {
		CostedPath cp = new CostedPath();
		List<GraphNode<?>> encountered=new ArrayList<>(), unencountered=new ArrayList<>();
		startNode.nodeValue=0;
		unencountered.add(startNode);
		if(avoidNodes != null) encountered.addAll(avoidNodes); // Avoids the nodes in this list
		GraphNode<?> currentNode;
		
		do {
			currentNode=unencountered.remove(0);
			encountered.add(currentNode);

			
			if(currentNode.data.equals(lookingFor)) { //found goal - assemble path list back to start and return
				cp.pathList.add(currentNode); //add current (goal) node to result
				cp.pathCost=currentNode.nodeValue; //total quickest path cost is nodeValue of currentNode
				
				while(currentNode!=startNode) { //while not back to startNode
					boolean foundPrevPathNode=false; //use flag to identify when previous path node is identified
					for(GraphNode<?> n : encountered) { //each node in encountered..
						for(GraphLink e : n.adjList) { //each edga from that node
							if(e.destNode==currentNode && currentNode.nodeValue-(int)e.time==n.nodeValue) { 
								//if that edge links to currentNode and different in nodeValues is cost of the edge -> found path node
								cp.pathList.add(0,n); //add identified path node to front of result list
								currentNode=n; //move currentnode ref back to identified path node
								foundPrevPathNode=true; //set flag to break outer loop
								break; //found correct prev path node and moved currentNode ref back to it so break inner loop.
							}
						}
						if(foundPrevPathNode) break; //identified prev path node break inner loop to continue
					}
				}
				//Reset the node values for all nodes to INF so we can search again
				for(GraphNode<?> n : encountered) n.nodeValue=Double.MAX_VALUE;
				for(GraphNode<?> n : unencountered) n.nodeValue=Double.MAX_VALUE;
				
				return cp; //the costed (quickest) path has been assembled, return
			}
			
			for(GraphLink e : currentNode.adjList)
				if(!encountered.contains(e.destNode)) {
					if(avoidLinks.contains(e.name)) continue; // Avoids the link in the avoidLinks list
					e.destNode.nodeValue=Integer.min((int)(e.destNode.nodeValue), (int)(currentNode.nodeValue+e.time));
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered,(n1,n2)->(int)(n1.nodeValue-n2.nodeValue)); //sort ascending
		}while(!unencountered.isEmpty());
		
		return null; //no path
	}
	
	public <T> CostedPath findShortestPath(GraphNode<?> startNode, T lookingFor, List<GraphNode<?>> avoidNodes, List<String> avoidLinks) {
		CostedPath cp = new CostedPath();
		List<GraphNode<?>> encountered=new ArrayList<>(), unencountered=new ArrayList<>();
		startNode.nodeValue=0;
		unencountered.add(startNode);
		if(avoidNodes != null) encountered.addAll(avoidNodes); // Avoid nodes in this list
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
							if(e.destNode==currentNode && currentNode.nodeValue-(int)e.length==n.nodeValue) {
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
					if(avoidLinks.contains(e.name)) continue; // Avoids the link in the avoidLinks list
					e.destNode.nodeValue=Integer.min((int)(e.destNode.nodeValue), (int)(currentNode.nodeValue+e.length));
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered,(n1,n2)->(int)(n1.nodeValue-n2.nodeValue));
		}while(!unencountered.isEmpty());
		
		return null;
	}
	
	public <T> List<CostedPath> findMultiplePaths(GraphNode<?> startNode, T lookingFor, List<GraphNode<?>> avoidNodes, List<String> avoidLinks, int pathNum) {
		CostedPath cp = new CostedPath();
		List<GraphNode<?>> encountered=new ArrayList<>(), unencountered=new ArrayList<>();
		startNode.nodeValue=0;
		unencountered.add(startNode);
		if(avoidNodes != null) encountered.addAll(avoidNodes); // Avoid nodes in this list
		GraphNode<?> currentNode;
		List<CostedPath> paths = new ArrayList<>();
		int counter = 1;
		
		do {
			currentNode=unencountered.remove(0);
			encountered.add(currentNode);
			
			// If one path is found but more are needed
			if(currentNode.data.equals(lookingFor) && counter < pathNum) {
				cp.pathList.add(currentNode);
				cp.pathCost=currentNode.nodeValue;
				
				while(currentNode!=startNode) {
					boolean foundPrevPathNode=false;
					for(GraphNode<?> n : encountered) {
						for(GraphLink e : n.adjList) {
							if(e.destNode==currentNode && currentNode.nodeValue-(int)e.time==n.nodeValue) {
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
				// Reset and continue
				encountered.clear(); unencountered.clear();
				startNode.nodeValue=0;
				if(avoidNodes != null) encountered.addAll(avoidNodes); // Avoid nodes in this list
				currentNode = startNode;
				encountered.add(currentNode);
				paths.add(cp); // Add path to list
				for(CostedPath path : paths) {
					encountered.addAll(path.pathList);
					encountered.remove(encountered.size()-1); // Remove the destNode
				}
				cp = new CostedPath();
				counter++;
			}
			
			// If you find all paths
			if(currentNode.data.equals(lookingFor) && counter == pathNum) {
				cp.pathList.add(currentNode);
				cp.pathCost=currentNode.nodeValue;
				
				while(currentNode!=startNode) {
					boolean foundPrevPathNode=false;
					for(GraphNode<?> n : encountered) {
						for(GraphLink e : n.adjList) {
							if(e.destNode==currentNode && currentNode.nodeValue-(int)e.time==n.nodeValue) {
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
				paths.add(cp); // Add path to list
				
				return paths;
			}
			
			for(GraphLink e : currentNode.adjList)
				if(!encountered.contains(e.destNode)) {
					if(avoidLinks.contains(e.name)) continue; // Avoids the link in the avoidLinks list
					e.destNode.nodeValue=Integer.min((int)(e.destNode.nodeValue), (int)(currentNode.nodeValue+e.time));
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered,(n1,n2)->(int)(n1.nodeValue-n2.nodeValue));
		}while(!unencountered.isEmpty());
		
		return null;
	}

}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {
	
	public <T> CostedPath findQuickestPath(GraphNode<?> startNode, T lookingFor) {
		CostedPath cp = new CostedPath(); //Create result object for quickest path
		List<GraphNode<?>> encountered=new ArrayList<>(), unencountered=new ArrayList<>(); //(un)encountered lists
		startNode.nodeValue=0; //startvalue = 0
		unencountered.add(startNode); //add start node as only value in encountered list to start
		GraphNode<?> currentNode; 
		
		do { //loop until unencounterd EMPTY
			currentNode=unencountered.remove(0); //get first unencountered node (sorted list)
			encountered.add(currentNode); //record current node in encountered list
			
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
			
			//we're not at goalnode yet, so...
			for(GraphLink e : currentNode.adjList) //for each edge from currentnode
				if(!encountered.contains(e.destNode)) { //if node it leads not yet encountered
					e.destNode.nodeValue=Integer.min((int)e.destNode.nodeValue, (int)(currentNode.nodeValue+e.time)); 
					//^update nodeValue at the end of the edge to the minimum of its currentvalue or total of the currentnode's + cost of edge
					unencountered.add(e.destNode);
				}
			Collections.sort(unencountered,(n1,n2)->(int)(n1.nodeValue-n2.nodeValue)); //sort ascending
		}while(!unencountered.isEmpty());
		
		return null; //no path
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

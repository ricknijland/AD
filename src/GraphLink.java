public class GraphLink<T> {
	public GraphNode<?> destNode;
	public int cost;
	
	public GraphLink(GraphNode<?> destNode, int cost) {
		this.destNode = destNode;
		this.cost = cost;
	}
}

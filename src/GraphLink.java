public class GraphLink<T> {
	public GraphNode<?> destNode;
	public double cost;
	private double length;
	
	public GraphLink(GraphNode<?> destNode, double length, char speed) {
		this.destNode = destNode;
		this.length = length;
		switch(speed) {
		case 'M':
			this.cost = (this.length / 120) * 60; 
			break;
		case 'I':
			this.cost = (this.length / 80) * 60; 
			break;
		case 'S':
			this.cost = (this.length / 50) * 60;
		default: 
			break;
		}
	}
}

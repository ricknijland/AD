public class GraphLink<T> {
	public GraphNode<?> destNode;
<<<<<<< HEAD
	public double time;
	public double length;
	public int speed;
	public String name;
	
	public GraphLink(GraphNode<?> destNode, double length, String name) {
		this.destNode = destNode;
		this.length = length;
		this.name = name;
		char speed = name.charAt(0);
		
		switch(speed) {
		case 'M':
			this.speed = 120;
			this.time = (length / speed) * 60;
			break;
		case 'N':
			this.speed = 100;
			this.time = (length / speed) * 60;
			break;
		case 'R':
		case 'L':
			this.speed = 80;
			this.time = (length / speed) * 60;
			break;
		case 'I':
			this.speed = 35;
			this.time = (length / speed) * 60;
		default:
			this.speed = 25;
			this.time = (length / speed) * 60;
=======
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
>>>>>>> 713a5bb397fe8743530e7c614fe3638ef57f3d1a
			break;
		}
	}
}

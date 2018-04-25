public class GraphLink<T> {
	public GraphNode<?> destNode;
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

		}
	}
}

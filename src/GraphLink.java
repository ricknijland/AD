public class GraphLink {
	public GraphNode<?> destNode;
	public double time;
	public double length;
	public int speed;
	public String name;
	
	public GraphLink(GraphNode<?> destNode, double length, String name) {
		this.destNode = destNode;
		this.length = length;
		this.name = name;
		char speedType = name.charAt(0);
		
		switch(speedType) {
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
			break;
		default:
			this.speed = 10;
			this.time = (length / speed) * 60;

		}
	}
}

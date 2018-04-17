import java.lang.reflect.Array;

public class AdjacencyMatrix {
	public boolean[][] amat;
	public GraphNode<?>[] nodes;
	public int nodeCount=0;
	
	public AdjacencyMatrix(int size) {
		amat = new boolean[size][size];
		nodes = (GraphNode<?>[])Array.newInstance(GraphNode.class, size);
	}

}

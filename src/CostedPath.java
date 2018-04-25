import java.util.ArrayList;
import java.util.List;

public class CostedPath {
	
	protected double pathCost;
	protected List<GraphNode<?>> pathList;

	public CostedPath() {
		pathCost=0;
		pathList=new ArrayList<>();
	}
	
}

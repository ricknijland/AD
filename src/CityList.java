import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CityList {

	private String[] CSVdata;
	private List<GraphNode<?>> cities = new ArrayList<>();
	private boolean first, second;

	@SuppressWarnings("unchecked")
	public CityList() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("C:\\Users\\TJ\\OneDrive\\Documents\\MapOfIreland.csv"));
		sc.nextLine(); //Skip first line of headers
		while(sc.hasNext()) {
			CSVdata = sc.nextLine().split(",");
			GraphNode link1 = new GraphNode(CSVdata[3]);
			GraphNode link2 = new GraphNode(CSVdata[4]);
			first = false; second = false;
			for(int i = 0; i < 2; i++)
				for(int j = 0; j < cities.size(); j++) {
					if(cities.get(j).data.equals(link1.data)) first = true;
					if(cities.get(j).data.equals(link2.data)) second = true;
				}
			if(first && second) {
				int temp1 = 0, temp2 = 0;
				for(int i = 0; i < 2; i++)
				for(int j = 0; j < cities.size(); j++) {
					if(cities.get(j).data.equals(link1.data)) link1 = cities.get(j);
					if(cities.get(j).data.equals(link2.data)) link2 = cities.get(j);
				}
				link1.connectToNodeUndirected(link2, Double.parseDouble(CSVdata[1]), CSVdata[0]);
			}
			else if(first) {
				for(int i = 0; i < cities.size(); i++) {
					if(cities.get(i).data.equals(link1.data)) {
						cities.get(i).connectToNodeUndirected(link2, Double.parseDouble(CSVdata[1]), CSVdata[0]);
						cities.add(link2);
					}
				}
			}
			else if(second) {
				for(int i = 0; i < cities.size(); i++) {
					if(cities.get(i).data.equals(link2.data)) {
						cities.get(i).connectToNodeUndirected(link1, Double.parseDouble(CSVdata[1]), CSVdata[0]);
						cities.add(link1);
					}
				}
			}
			else {
				link1.connectToNodeUndirected(link2, Double.parseDouble(CSVdata[1]), CSVdata[0]);
				cities.add(link1); 
				cities.add(link2);
			}
		}
		sc.close();
	}
	
	public GraphNode<?> get(int index) {
		return cities.get(index);
	}
	
	public int size() {
		return cities.size();
	}
}

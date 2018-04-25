import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		CityList cities = new CityList();
		for(int i = 0; i < cities.size(); i++) {
			System.out.println(i + ". " +cities.get(i).data);
		} System.out.println();
		Dijkstra d = new Dijkstra();
		/*
		CostedPath shortest = d.findShortestPath(cities.get(1), "Cork");
		for(GraphNode<?> z : shortest.pathList) {
			System.out.println(z.data);
		}
		System.out.printf("Total distance: %.2f km.\n\n", shortest.pathCost);
		*/
		CostedPath quickest = d.findQuickestPath(cities.get(1), "Cork");
		for(GraphNode<?> z : quickest.pathList) {
			System.out.println(z.data);
		}
		System.out.printf("Total time: %.2f minutes.", quickest.pathCost);

	}
}

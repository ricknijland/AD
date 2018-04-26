import java.io.FileNotFoundException;

import GraphVisual.CellT;
import GraphVisual.Graph;
import GraphVisual.Layout;
import GraphVisual.Model;
import GraphVisual.RandomLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	Graph graph = new Graph();
	static CityList cities;

	public static void main(String[] args) throws FileNotFoundException {
		cities = new CityList();
		for(int i = 0; i < cities.size(); i++) {
			System.out.print(i + ". " +cities.get(i).data);
			for(int j = 0; j < cities.get(i).adjList.size(); j++) {
				System.out.println(" Time: " + cities.get(i).adjList.get(j).time + " Dest: " + cities.get(i).adjList.get(j).destNode.data);
			}
		} System.out.println();
		Dijkstra d = new Dijkstra();
		
		CostedPath shortest = d.findShortestPath(cities.get(1), "Galway");
		for(GraphNode<?> z : shortest.pathList) {
			System.out.println(z.data);
		}
		System.out.printf("Total distance: %.2f km.\n\n", shortest.pathCost);
		
		CostedPath quickest = d.findQuickestPath(cities.get(1), "Dublin");
		for(GraphNode<?> z : quickest.pathList) {
			System.out.println(z.data);
		}
		System.out.printf("Total time: %.2f minutes.", quickest.pathCost);

		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		
		root.setCenter(graph.getScrollPane());
		
		Scene scene = new Scene(root, 1024, 768);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		addGraphComponents();
		
		Layout layout = new RandomLayout(graph);
		layout.execute();
	}
	
	private void addGraphComponents() {
		Model model = graph.getModel();
		
		graph.beginUpdate();
		
		for(int i = 0; i < cities.size(); i++) {
			model.addCell(cities.get(i).data.toString(), CellT.CIRCLE);
			
		}
		
		for(int i = 0; i < cities.size(); i++) {
			for(int j = 0; j < cities.get(i).adjList.size(); j++) {
				model.addEdge(cities.get(i).data.toString(), cities.get(i).adjList.get(j).destNode.data.toString());
			}
		}
		
		graph.endUpdate();
	}
}

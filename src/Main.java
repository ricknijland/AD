import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import GraphVisual.CellT;
import GraphVisual.Graph;
import GraphVisual.Layout;
import GraphVisual.Model;
import GraphVisual.RandomLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Main extends Application {
	
	Graph graph = new Graph();
	static CityList cities;
	static Dijkstra d;

	public static void main(String[] args) throws FileNotFoundException {
		cities = new CityList();
		for(int i = 0; i < cities.size(); i++) {
			System.out.print(i + ". " +cities.get(i).data);
			for(int j = 0; j < cities.get(i).adjList.size(); j++) {
				System.out.println(" Time: " + cities.get(i).adjList.get(j).time + " Dest: " + cities.get(i).adjList.get(j).destNode.data);
			}
		} System.out.println();
		d = new Dijkstra();
		
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
		GridPane grid = new GridPane();
		BorderPane root = new BorderPane();
		

		
		root.setCenter(graph.getScrollPane());
		Scene scene = new Scene(root, 1024, 768);
		
		Label source = new Label("Source: ");
		TextField sourceTextField = new TextField();
		
		Label destination = new Label("Destination: ");
		TextField destTextField = new TextField();
		
		Button button = new Button("Calculate routes");
		
		//Setting an action for the Submit button
		button.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		    public void handle(ActionEvent e) {
		        if ((source.getText() != null && !source.getText().isEmpty())) {
		        	if((destination.getText() != null && !destination.getText().isEmpty())) {
		        		d.findShortestPath(getNodeWithString(source.getText()), destination.getText());
		        		System.out.println("vet kort");
		        	}
		        		
		        } 
		     }
		 });
		
		HBox hb = new HBox(source, sourceTextField, destination, destTextField, button);

		root.setTop(hb);
		
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
	
	private GraphNode getNodeWithString(String name) {
		for(int i = 0; i < cities.size(); i++) {
			if(name.equals(cities.get(i).data));
				return cities.get(i); 
		}
		return null;
	}
}
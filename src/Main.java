import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import GraphVisual.CellT;
import GraphVisual.Graph;
import GraphVisual.Layout;
import GraphVisual.Model;
import GraphVisual.RandomLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	// Variables
	Graph graph = new Graph();
	static Paths paths;
	static List<String> avoidNodes, avoidLinks, waypoints;
	static CostedPath path;

	public static void main(String[] args) throws FileNotFoundException {
	
		avoidNodes = new ArrayList<>();
		avoidLinks = new ArrayList<>();
		waypoints = new ArrayList<>();
		paths = new Paths(avoidNodes, avoidLinks, waypoints);
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		
		root.setCenter(graph.getScrollPane());
		Scene scene = new Scene(root, 1200, 768);
		
		// HBox to hold all textfields
		VBox vb = new VBox();
		
		// -----------------------------------------START OF HB1------------------------------------------------
		Label shortest = new Label("Finds the shortest path:   ");
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
		        		path = paths.findShortestPath(sourceTextField.getText(), destTextField.getText());
		        	}
		        } 
		     }
		 });
		
		HBox hb1 = new HBox(shortest, source, sourceTextField, destination, destTextField, button);
		// -----------------------------------------END OF HB1------------------------------------------------
		
		
		// -----------------------------------------START OF HB2------------------------------------------------
		Label quickest = new Label("Finds the quickest path:   ");
		Label source2 = new Label("Source: ");
		TextField sourceTextField2 = new TextField();
		
		Label destination2 = new Label("Destination: ");
		TextField destTextField2 = new TextField();
		
		Button button2 = new Button("Calculate routes");
		
		//Setting an action for the Submit button
		button2.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		    public void handle(ActionEvent e) {
		        if ((source2.getText() != null && !source2.getText().isEmpty())) {
		        	if((destination2.getText() != null && !destination2.getText().isEmpty())) {
		        		path = paths.findQuickestPath(sourceTextField2.getText(), destTextField2.getText());
		        	}
		        } 
		     }
		 });
		
		HBox hb2 = new HBox(quickest, source2, sourceTextField2, destination2, destTextField2, button2);
		hb2.setPadding(new Insets(5, 5, 5, 5));
		// -----------------------------------------END OF HB2------------------------------------------------
		
		
		// -----------------------------------------START OF AVOID------------------------------------------------
		Label avoidLinksText = new Label("List roads you would like to avoid (comma-separated):  ");
		TextField linksTextField = new TextField();
		
		Label avoidNodesText = new Label("List cities/towns you would like to avoid(comma-separated): ");
		TextField nodesTextField = new TextField();
		
		Button linkButton = new Button("Add Roads");
		Button nodeButton = new Button("Add Cities");
		
		//Setting an action for the Submit button
		linkButton.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
		        if (linksTextField.getText() != null) {
		        	String[] list = linksTextField.getText().split(",");
		        	for(int i = 0; i < list.length; i++) {
		        		avoidLinks.add(list[i]); // Add all links we want to avoid
		        	}
		        	paths = new Paths(avoidNodes, avoidLinks, waypoints); // Redo path for updated avoidLinks
		        } 
		     }
		 });
		
		//Setting an action for the Submit button
		nodeButton.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
		        if (nodesTextField.getText() != null) {
		        	String[] list = nodesTextField.getText().split(",");
		        	for(int i = 0; i < list.length; i++) {
		        		avoidNodes.add(list[i]); // Add all nodes we want to avoid
		        	}
		        	paths = new Paths(avoidNodes, avoidLinks, waypoints); // Redo path for updated avoidNodes
		        } 
		     }
		 });
		HBox avoid = new HBox(avoidLinksText, linksTextField, linkButton, avoidNodesText, nodesTextField, nodeButton);
		//-----------------------------------------END OF AVOID----------------------------------
		
		
		//----------------------------------------START OF WAYPOINT-----------------------------------
		Label waypointText = new Label("List cities you want to pass through (comma-separated):  ");
		TextField waypointTextField = new TextField();
		Button waypointButton = new Button("Add Cities");
		
		//Setting an action for the Submit button
		waypointButton.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
		        if (waypointTextField.getText() != null) {
		        	String[] list = waypointTextField.getText().split(",");
		        	for(int i = 0; i < list.length; i++) {
		        		waypoints.add(list[i]); // Add all links we want to avoid
		        	}
		        	paths = new Paths(avoidNodes, avoidLinks, waypoints); // Redo path for updated avoidLinks
		        } 
		     }
		 });
		HBox waypoint = new HBox(waypointText, waypointTextField, waypointButton);
		//----------------------------------------END OF WAYPOINT-----------------------------------

		
		vb.getChildren().addAll(avoid, waypoint, hb1, hb2);
		root.setTop(vb);
		
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
		
		for(int i = 0; i < paths.nodes.size(); i++) {
			model.addCell(paths.nodes.get(i).data.toString(), CellT.CIRCLE);
			
		}
		
		for(int i = 0; i < paths.nodes.size(); i++) {
			for(int j = 0; j < paths.nodes.get(i).adjList.size(); j++) {
				model.addEdge(paths.nodes.get(i).data.toString(), paths.nodes.get(i).adjList.get(j).destNode.data.toString());
			}
		}
		graph.endUpdate();
	}

}
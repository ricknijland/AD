import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import GraphVisual.Cell;
import GraphVisual.CellT;
import GraphVisual.Edge;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	// Variables
	Graph graph = new Graph();
	static Paths paths;
	static List<String> avoidNodes, avoidLinks, waypoints;
	static CostedPath path;
	static Model model;
	static Label avoidlistcontent = new Label();
	static Label waypointlist = new Label();
	static Label shortCities = new Label();
	static Label quickCities = new Label();
	static Label multiCities = new Label();
	static VBox directions = new VBox(); // needed here for being able to use it higher up in code

	public static void main(String[] args) throws FileNotFoundException {
		
		// Instantiate
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
		Scene scene = new Scene(root, 1200, 700);
		HBox splitScreen = new HBox(); // Split upper part into textfields and results of textfields
		
		// HBox to hold all textfields
		VBox vb = new VBox();
		
		// -----------------------------------------START OF HB1------------------------------------------------
		Label shortest = new Label("Finds the shortest path:   ");
		HBox shortBox = new HBox(shortest);
		shortBox.setPadding(new Insets(5, 5, 5, 5));
		
		Label source = new Label("Source: ");
		TextField sourceTextField = new TextField();
		HBox sourceBox = new HBox(source, sourceTextField);
		sourceBox.setPadding(new Insets(5, 5, 0, 5));
		
		Label destination = new Label("Destination: ");
		TextField destTextField = new TextField();
		Button button = new Button("Calculate routes");
		HBox destBoxNButton = new HBox(destination, destTextField, button);
		destBoxNButton.setPadding(new Insets(5, 5, 0, 5));
		
		// Find shortest path
		button.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
		        if ((sourceTextField.getText() != null && !sourceTextField.getText().isEmpty())) {
		        	if((destTextField.getText() != null && !destTextField.getText().isEmpty())) {
		        		clearPath(); // If path already created
		        		directions.getChildren().clear(); // Remove earlier directions
		        		paths = new Paths(avoidNodes, avoidLinks, waypoints);
		        		path = paths.findShortestPath(sourceTextField.getText(), destTextField.getText());
		        		if(path != null) { // Path found
		        			showPath(); // Show path on GUI
		        		}
		        	}
		        } 
		        // Add to label for shortCities and route
        		shortCities.setText(sourceTextField.getText() + " | " + destTextField.getText());
        		if(path != null) { // Path found
        			List<Label> temp = paths.getShortestRouteString();
        			for(int i = 0; i < temp.size(); i++) {
        				directions.getChildren().add(temp.get(i));
        			}
        		}
        		else { // No path found
        			directions.getChildren().add(new Label("No path found for specified cities."));
        		}
        		
        		// Reset fields
		        sourceTextField.clear();
        		destTextField.clear();
		     }
		 });
		
		HBox hb1 = new HBox(shortBox, sourceBox, destBoxNButton);
		// -----------------------------------------END OF HB1------------------------------------------------
		
		
		// -----------------------------------------START OF HB2------------------------------------------------
		Label quickest = new Label("Finds the quickest path:   ");
		HBox quickBox = new HBox(quickest);
		quickBox.setPadding(new Insets(5, 5, 0, 5));
		
		Label source2 = new Label("Source: ");
		TextField sourceTextField2 = new TextField();
		HBox source2Box = new HBox(source2, sourceTextField2);
		source2Box.setPadding(new Insets(5, 5, 5, 5));
		
		Label destination2 = new Label("Destination: ");
		TextField destTextField2 = new TextField();
		Button button2 = new Button("Calculate routes");
		HBox destBoxNButton2 = new HBox(destination2, destTextField2, button2);
		destBoxNButton2.setPadding(new Insets(5, 5, 0, 5));
		
		//Setting an action for the Submit button
		button2.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		    public void handle(ActionEvent e) {
		        if ((sourceTextField2.getText() != null && !sourceTextField2.getText().isEmpty())) {
		        	if((destTextField2.getText() != null && !destTextField2.getText().isEmpty())) {
		        		clearPath(); // If path already created
		        		directions.getChildren().clear(); // Remove earlier directions
		        		paths = new Paths(avoidNodes, avoidLinks, waypoints);
		        		path = paths.findQuickestPath(sourceTextField2.getText(), destTextField2.getText());
		        		if(path != null) { // Path found
		        			showPath(); // Show path on GUI
		        		}
		        	} 
		        }
		        // Add to label to quickCities and route
        		quickCities.setText(sourceTextField2.getText() + " | " + destTextField2.getText());
        		if(path != null) { // Path found
        			List<Label> temp = paths.getQuickestRouteString();
        			for(int i = 0; i < temp.size(); i++) {
        				directions.getChildren().add(temp.get(i));
        			}
        		}
        		else { // No path found
        			directions.getChildren().add(new Label("No path found for specified cities."));
        		}
        		
        		// Reset fields
		        sourceTextField2.clear(); // Clear TextField
        		destTextField2.clear(); // Clear TextField
		     }
		 });
		
		HBox hb2 = new HBox(quickBox, source2Box, destBoxNButton2);
		// -----------------------------------------END OF HB2------------------------------------------------
		
		// -----------------------------------------START OF HB3------------------------------------------------
		Label multi = new Label("Finds multiple quickest paths:   ");
		HBox multiBox = new HBox(multi);
		multiBox.setPadding(new Insets(5, 5, 0, 5));
		
		Label source3 = new Label("Source: ");
		TextField sourceTextField3 = new TextField();
		sourceTextField3.setPromptText("Waypoint not supported");
		HBox source3Box = new HBox(source3, sourceTextField3);
		source3Box.setPadding(new Insets(5, 5, 0, 5));
		
		Label destination3 = new Label("Destination: ");
		TextField destTextField3 = new TextField();
		destTextField3.setPromptText("Waypoint not supported");
		Button button3 = new Button("Calculate routes");
		HBox destBoxNButton3 = new HBox(destination3, destTextField3, button3);
		destBoxNButton3.setPadding(new Insets(5, 5, 0, 5));
		
		Label numRoutes = new Label("# of Routes: ");
		TextField number = new TextField();
		number.setText("1"); // Set default number
		number.setMaxWidth(30);
		HBox numRoutesBox = new HBox(numRoutes, number);
		numRoutesBox.setPadding(new Insets(5, 5, 0, 5));
		
		//Setting an action for the Submit button
		button3.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		    public void handle(ActionEvent e) {
				int pathNum = number.getText().equals("") ? 0 : Integer.parseInt(number.getText());
		        if ((sourceTextField3.getText() != null && !sourceTextField3.getText().isEmpty())) {
		        	if((destTextField3.getText() != null && !destTextField3.getText().isEmpty())) {
		        		if(!waypoints.isEmpty()) { // Not supported
		        			return;
		        		}
		        		clearPath(); // If path already created
		        		directions.getChildren().clear(); // Remove earlier directions
		        		paths = new Paths(avoidNodes, avoidLinks, waypoints);
		        		List<CostedPath> multiList = paths.findMultiplePaths(sourceTextField3.getText(), destTextField3.getText(), pathNum);
		        		if(multiList != null) { // Path found
		        			for(int i = 0; i < multiList.size(); i++) { // Set each CostedPath to path variable to show nodes
		        				path = multiList.get(i);
		        				showPath(); // Show path on GUI
		        			}
		        		}
		        	} 
		        }
		        // Add to label to quickCities and route
        		multiCities.setText(sourceTextField3.getText() + " | " + destTextField3.getText());
        		if(path != null) { // Path found
        			List<Label> temp = paths.getMultipleRoutesString(pathNum);
        			for(int i = 0; i < temp.size(); i++) {
        				directions.getChildren().add(temp.get(i));
        			}
        		}
        		else { // No path found
        			directions.getChildren().add(new Label("No path found for specified cities."));
        		}
        		
        		// Reset fields
		        sourceTextField2.clear(); // Clear TextField
        		destTextField2.clear(); // Clear TextField
		     }
		 });
		
		HBox hb3 = new HBox(multiBox, numRoutesBox, source3Box, destBoxNButton3);
		// -----------------------------------------END OF HB3------------------------------------------------
		
		// -----------------------------------------START OF AVOID------------------------------------------------
		Label avoidLinksText = new Label("Roads to avoid: ");
		TextField linksTextField = new TextField();
		linksTextField.setPromptText("Separate by (,)");
		Button linkButton = new Button("Add Roads");
		Button resetAvoids = new Button("Reset All");
		HBox linkBox = new HBox(avoidLinksText, linksTextField, linkButton, resetAvoids);
		linkBox.setPadding(new Insets(5, 5, 0, 5));
		
		Label avoidNodesText = new Label("Cities to avoid: ");
		TextField nodesTextField = new TextField();
		nodesTextField.setPromptText("Separate by (,)");
		Button nodeButton = new Button("Add Cities");
		HBox nodeBox = new HBox(avoidNodesText, nodesTextField, nodeButton);
		nodeBox.setPadding(new Insets(5, 5, 0, 5));
		
		// Adds links to avoidLinks list
		linkButton.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
		        if (linksTextField.getText() != null) {
		        	String[] list = linksTextField.getText().split(",");
		        	for(int i = 0; i < list.length; i++) {
		        		avoidLinks.add(list[i]); // Add all links we want to avoid
		        		// Add to label for GUI
		        		StringBuilder node = new StringBuilder();
		        		node.append(avoidlistcontent.getText());
		        		node.append(list[i] + ", ");
		        		avoidlistcontent.setText(node.toString());
		        	}
		        } 
		        linksTextField.clear(); // Clear TextField
		     }
		 });
		
		// Adds nodes to avoidNodes list
		nodeButton.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
		        if (nodesTextField.getText() != null) {
		        	String[] list = nodesTextField.getText().split(",");
		        	for(int i = 0; i < list.length; i++) {
		        		avoidNodes.add(list[i]); // Add all nodes we want to avoid
		        		// Add to label for GUI
		        		StringBuilder node = new StringBuilder();
		        		node.append(avoidlistcontent.getText());
		        		node.append(list[i] + ", ");
		        		avoidlistcontent.setText(node.toString());
		        	}
		        } 
		        nodesTextField.clear(); // Clear TextField
		     }
		 });
		// Reset waypoint list
		resetAvoids.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				avoidlistcontent.setText("");
				avoidNodes.clear();
				avoidLinks.clear();
			}
		});		
		HBox avoid = new HBox(nodeBox, linkBox);
		//-----------------------------------------END OF AVOID----------------------------------
		
		
		//----------------------------------------START OF WAYPOINT-----------------------------------
		Label waypointText = new Label("Cities to pass thru (in-order): ");
		TextField waypointTextField = new TextField();
		waypointTextField.setPromptText("Separate by (,)");
		Button waypointButton = new Button("Add Cities");
		Button resetWays = new Button("Reset");
		HBox wayBox = new HBox(waypointText, waypointTextField, waypointButton, resetWays);
		wayBox.setPadding(new Insets(5, 5, 0, 5));
		
		// Add waypoints to waypoints list
		waypointButton.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
		        if (waypointTextField.getText() != null) {
		        	String[] list = waypointTextField.getText().split(",");
		        	for(int i = 0; i < list.length; i++) {
		        		waypoints.add(list[i]); // Add all links we want to avoid
		        		// Add to label for GUI
		        		StringBuilder node = new StringBuilder();
		        		node.append(waypointlist.getText());
		        		node.append(list[i] + ", ");
		        		waypointlist.setText(node.toString());
		        	}
		        } 
		        waypointTextField.clear(); // Clear TextField
		     }
		 });
		
		// Reset waypoint list
		resetWays.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				waypointlist.setText("");
				waypoints.clear();
			}
		});
		HBox waypoint = new HBox(wayBox);
		//----------------------------------------END OF WAYPOINT-----------------------------------

		
		//---------------------------------------START OF SECOND VBOX--------------------------------------------
		Label avoidlist = new Label("Cities/Roads to avoid: ");
		HBox avoidlistBox = new HBox(avoidlist, avoidlistcontent);
		avoidlistBox.setPadding(new Insets(5, 5, 10, 5));
		
		Label waypoints = new Label("Cities to hit on way: ");
		HBox waypointlistBox = new HBox(waypoints, waypointlist);
		waypointlistBox.setPadding(new Insets(5, 5, 10, 5));
		
		Label shorts = new Label("Cities chosen: ");
		HBox shortsBox = new HBox(shorts, shortCities);
		shortsBox.setPadding(new Insets(5, 5, 5, 5));
		
		Label quicks = new Label("Cities chosen: ");
		HBox quicksBox = new HBox(quicks, quickCities);
		quicksBox.setPadding(new Insets(5, 5, 5, 5));
		
		Label multis = new Label("Cities chosen: ");
		HBox multisBox = new HBox(multis, multiCities);
		multisBox.setPadding(new Insets(10, 5, 5, 5));
		
		//---------------------------------------END OF SECOND VBOX----------------------------------------------
		
		//---------------------------------------START OF BOTTOM OF SCREEN---------------------------------------
		// This shows the route
		directions = new VBox();
		directions.setPadding(new Insets(5));
		
		// Makes box able to scroll
		ScrollPane routeScroll = new ScrollPane(directions);
		routeScroll.setFitToHeight(true);
		routeScroll.setMaxHeight(150);
		routeScroll.setMinHeight(120);
		
		//-----------------------------------------END OF BOTTOM OF SCREEN---------------------------------------
		
		
		//-----------------------------------------SET UP GUI----------------------------------------------------
		VBox vb2 = new VBox(avoidlistBox, waypointlistBox, shortsBox, quicksBox, multisBox);
		vb2.setPadding(new Insets(0, 0, 0, 10));
		
		vb.getChildren().addAll(avoid, waypoint, hb1, hb2, hb3);
		splitScreen.getChildren().addAll(vb, vb2);
		root.setTop(splitScreen);
		root.setBottom(routeScroll);
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		addGraphComponents();
		
		Layout layout = new RandomLayout(graph);
		layout.execute();
	}
	
	private void addGraphComponents() {
		model = graph.getModel();
		
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
	
	// Show path on GUI
	private void showPath() {
		for(int i = 0; i < path.pathList.size(); i++) {
			for(int j = 0; j < model.getAllCells().size(); j++) {
				if(path.pathList.get(i).data.equals(model.getAllCells().get(j).getCellId())) { // Shuffle through different links in each nodes' adjList
					for(int y = 0; y < path.pathList.get(i).adjList.size(); y++)
						for(int x = 0; x < model.getAllEdges().size(); x++) {
							if(i < path.pathList.size() - 1) {
									if(model.getAllEdges().get(x).getTarget().getCellId().equals(path.pathList.get(i+1).data))
										if(model.getAllEdges().get(x).getSource().getCellId().equals(path.pathList.get(i).data))
											model.getAllEdges().get(x).setColor(Color.GREEN);
							}
						}
					model.getAllCells().get(j).setColor(Color.DARKGREEN);
					}
				}
		}
	}
	
	// Clear path on GUI for next path
	private void clearPath() {
		for(Cell c : model.getAllCells()) {
			c.setColor(Color.DODGERBLUE);
		}
		for(Edge l : model.getAllEdges()) {
			l.setColor(Color.RED);
			l.getLine().setStrokeWidth(1);
		}
	}
	
}
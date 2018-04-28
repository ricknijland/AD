package GraphVisual;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Group {
	protected Cell source;
	protected Cell target;
	
	private Line line;
	
	public Edge(Cell source, Cell target) {
		this.source = source;
		this.target = target;
		
		source.addCellChild(target);
		target.addCellParent(source);
		
		line = new Line();
		line.setStroke(Color.RED);
		
		line.startXProperty().bind((source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0)));
        line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));
	
        getChildren().add(line);
	}
	
	public Cell getSource() {
		return source;
	}
	
	public Cell getTarget() {
		return target;
	}
	
	// Set color and width of line in GUI
	public void setColor(Color color) {
		line.setStroke(color);
		if(color.equals(Color.GREEN))
			line.setStrokeWidth(4);
	}
	
	// Return line to Main class
	public Line getLine() {
		return line;
	}
}

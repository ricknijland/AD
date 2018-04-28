package GraphVisual;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleCell extends Cell {
	
	private Circle view;

	public CircleCell(String cellId) {
		super(cellId);
		
		view = new Circle(10,10,10);
		
		view.setStroke(Color.DODGERBLUE);
		view.setFill(Color.DODGERBLUE);

		setView(view);
	}
	
	@Override
	public void setColor(Color color) {
		view.setStroke(color);
		view.setFill(color);
	}

}

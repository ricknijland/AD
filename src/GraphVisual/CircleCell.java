package GraphVisual;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CircleCell extends Cell {

	public CircleCell(String cellId) {
		super(cellId);
		
		Circle view = new Circle(25,25,25);
		
		view.setStroke(Color.DODGERBLUE);
		view.setFill(Color.DODGERBLUE);

		setView(view);
	}

}

package GraphVisual;

import javafx.scene.control.Button;

public class ButtonCell extends Cell {

    public ButtonCell(String id) {
        super(id);

        Button view = new Button(id);

        setView(view);

    }

}

package frontend;

import backend.model.ColoredFigure;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class StatusPane extends BorderPane {

	private final Label statusLabel;

	public StatusPane() {
		setStyle("-fx-background-color: #4EBCF8");
		statusLabel = new Label("");
		statusLabel.setAlignment(Pos.CENTER);
		statusLabel.setStyle("-fx-font-size: 16");
		setCenter(statusLabel);
	}
	
//	public void updateStatus(String text) {
//
//		statusLabel.setText(text);
//	}
	public void updateStatus(String st) {
		statusLabel.setText(st);
	}

}
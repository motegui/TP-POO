package frontend;

import backend.CanvasState;
import backend.model.*;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ResourceBundle;


public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	private static final String FIGURE_SELECTION_LINE_COLOR_HEX = "#FF0000";



	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color fillColor = Color.YELLOW;
	Double sliderWidth = 1.0;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	ToggleButton cpyFormat = new ToggleButton("Cop.Form.");

	Slider borderSize = new Slider(1.0, 50.0, 26.0);
	ColorPicker LineColorPicker = new ColorPicker(lineColor); //paneles de color seteados por default como se pedia
	ColorPicker fillColorPicker = new ColorPicker(fillColor);

	String undoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("undoIcon");
	Image undoIcon = new Image(HTMLEditorSkin.class.getResource(undoIconPath).toString());
	Button undoButton = new Button("Deshacer", new ImageView(undoIcon));
	String redoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("redoIcon");
	Image redoIcon = new Image(HTMLEditorSkin.class.getResource(redoIconPath).toString());

	Button redoButton = new Button("Rehacer", new ImageView(redoIcon));


	String cutIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("cutIcon");
	Image cutIcon = new Image(HTMLEditorSkin.class.getResource(cutIconPath).toString());
	Button cut = new Button("Cortar", new ImageView(cutIcon));

	String copyIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("copyIcon");
	Image copyIcon = new Image(HTMLEditorSkin.class.getResource(copyIconPath).toString());
	Button copy = new Button("Copiar", new ImageView(copyIcon));
	String pasteIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("pasteIcon");
	Image pasteIcon = new Image(HTMLEditorSkin.class.getResource(pasteIconPath).toString());

	Button paste = new Button("Pegar", new ImageView(pasteIcon));


	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private ColoredFigure selectedFigure = null;

	private Text undoText = new Text("0");
	private Text redoText = new Text("0");

	private ColoredFigure clipboard;


	// StatusBar
	private StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;



		Button[] shortcut = {cut, copy, paste, undoButton, redoButton};
		HBox shortcuts = new HBox(10);

		shortcuts.getChildren().addAll(cut, copy, paste);
		for (Button tool : shortcut) { //itera por los botones para setear su tamaño
			tool.setMinWidth(90);
			tool.setCursor(Cursor.HAND);
		}
		setTop(shortcuts);
		shortcuts.setPadding(new Insets(5));



		HBox timetravel = new HBox(10);
		timetravel.getChildren().addAll(undoText, undoButton, redoButton, redoText);
		timetravel.setPadding(new Insets(5));
		timetravel.setStyle("-fx-background-color: #999; -fx-alignment: center");

		BorderPane topPane = new BorderPane();
		topPane.setTop(shortcuts);
		topPane.setBottom(timetravel);
		setTop(topPane);

		shortcuts.setStyle("-fx-background-color: #999");
		//estilo de los botones
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, undoButton, redoButton, cpyFormat};


		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) { //itera por los botones para setear su tamaño
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		//crea un nuevo layout con todos los botones de forma vertical a la izq uno abajo del otro
		VBox buttonsBox = new VBox(10);


		buttonsBox.getChildren().addAll(toolsArr);
		borderSize.setShowTickMarks(true);
		borderSize.setShowTickLabels(true);
		buttonsBox.getChildren().addAll(new Text("Borde"),borderSize, LineColorPicker);
		buttonsBox.getChildren().addAll(new Text("Relleno"), fillColorPicker);


		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(sliderWidth);
		FrontGraphicsController fgc = new FrontGraphicsController(gc);





		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

//		canvas.setOnMouseReleased(event -> {
//			Point endPoint = new Point(event.getX(), event.getY());
//			if (startPoint == null) {
//				return;
//			}
//			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()){
//				return;
//			}
//			ColoredFigure newFigure = null;
//			ToggleButton[] buttons = new ToggleButton[]{ellipseButton,circleButton,squareButton,rectangleButton};
//			for(ToggleButton b : buttons){
//			if(b.isSelected()){
//				newFigure = b.draw( );
//				//cuando se dibuje una figura aca voy a tener que hacer la logica de redo aca
//			}
//			if(newFigure != null){
//				canvasState.addFigure(newFigure);
//				// aca sigue yendo lo del undo
//			}
//		}
//			startPoint = null;
//			redrawCanvas();
//		});
		//BORRAR DESDE ACA
		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return;
			}
			ColoredFigure newFigure = null;
			if (rectangleButton.isSelected()) {
				newFigure = new Rectangle(fgc, startPoint, endPoint, lineColor.toString(), fillColor.toString(), sliderWidth);
			} else if (circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(fgc, startPoint, circleRadius, lineColor.toString(), fillColor.toString(), sliderWidth);
			} else if (squareButton.isSelected()) {
				double size = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Square(fgc, startPoint, size, lineColor.toString(), fillColor.toString(), sliderWidth);
			} else if (ellipseButton.isSelected()) {
				Point centerPoint = new Point(Math.abs(endPoint.x + startPoint.x) / 2, (Math.abs((endPoint.y + startPoint.y)) / 2));
				double sMayorAxis = Math.abs(endPoint.x - startPoint.x);
				double sMinorAxis = Math.abs(endPoint.y - startPoint.y);
				newFigure = new Ellipse(fgc, centerPoint, sMayorAxis, sMinorAxis, lineColor.toString(), fillColor.toString(), sliderWidth);
			} else {
				return;
			}
			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});
		//HASTA ACA

				canvas.setOnMouseClicked(event -> {
				Point eventPoint = new Point(event.getX(), event.getY());
				if (selectedFigure != null && cpyFormat.isSelected()) {
					Double auxWidth=selectedFigure.getLineWidth();
					String auxLineColor=selectedFigure.getLineColor();
					String auxFillColor=selectedFigure.getFillColor();
					ColoredFigure aux = selectedFigure;
					find(eventPoint);
					selectedFigure.setFillColor(auxFillColor);
					selectedFigure.setLineColor(auxLineColor);
					selectedFigure.setLineWidth(auxWidth);
				}
				selectedFigure = aux;
				selectionButton.setSelected(true);
			}
			else if (selectionButton.isSelected()) {
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				selectedFigure = null;
				find(eventPoint);
				statusPane.updateStatus((selectedFigure == null) ? "Ninguna figura encontrada." : label.append(selectedFigure).toString());
			}
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected() && selectedFigure != null) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100.0;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100.0;
				selectedFigure.moveFigure(100.0*diffX, 100.0*diffY);
				startPoint.movePoint(0,0);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});



		fillColorPicker.setOnAction(event -> {
			fillColor = fillColorPicker.getValue();
			if (selectedFigure != null) {
				selectedFigure.setFillColor(fillColor.toString());
				redrawCanvas();
			}
		});

		LineColorPicker.setOnAction(event -> {
			lineColor = LineColorPicker.getValue();
			if (selectedFigure != null) {
				selectedFigure.setLineColor(LineColorPicker.getValue().toString());
				redrawCanvas();
			}
		});

		borderSize.setOnMouseDragged(event -> {
			sliderWidth = borderSize.getValue();
			if (selectedFigure != null) {
				selectedFigure.setLineWidth(sliderWidth);
				redrawCanvas();
			}
		});


		copy.setOnAction(event -> {
				clipboard = selectedFigure;
				redrawCanvas();
		});

		paste.setOnAction(event -> {
			if (clipboard != null) {
				ColoredFigure figure = clipboard.copyFigure();
				canvasState.addFigure(figure);
				redrawCanvas();
			}
		});

		cut.setOnAction(event -> {
			if (selectedFigure != null) {
				clipboard = selectedFigure;
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}


	//metodo que sirve para actualizar el canvas con los cambios realizados hasta el momento
	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		//for each para recorrer todas las figuras en la lista del canvasState
		for (ColoredFigure figure : canvasState.figures()) {
			if ((figure == selectedFigure)) {
				figure.draw(FIGURE_SELECTION_LINE_COLOR_HEX);
			} else {
				figure.draw();
			}
		}

	}
	public void find(Point eventPoint){
		for (ColoredFigure figure : canvasState.figures()) {
			if (figure.figureBelongs(eventPoint)) {
				selectedFigure = figure;
			}
		}
	}


	//rehace el ultimo cambio
//	public void redoChange(){
//		selectedFigure=null;
//		canvasState.redoChange();
//		redrawCanvas();
//	}
//
//	//revierte el ultimo cambio
//		public void undoChange(){
//		selectedFigure = null;
//		canvasState.undoChange();
//		redrawCanvas();
//		}
}
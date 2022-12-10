package frontend;

import backend.CanvasState;
import backend.model.*;
//import frontend.FrontFigures.GetFrontFigure;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


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
	ToggleButton undoButton = new ToggleButton("Deshacer");
	ToggleButton redoButton = new ToggleButton("Rehacer");

	Button cut = new Button("Cut");
	Button copy = new Button("Copy");
	Button paste = new Button("Paste");

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private ColoredFigure selectedFigure = null;



	// StatusBar
	private StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		//estilo de los botones
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, undoButton, redoButton, cpyFormat};


		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) { //itera por los botones para setear su tamaño
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		VBox vbox = new VBox(8); // spacing = 8
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().addAll(cut, copy, paste);

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
		//slider para cambiar el grosor del borde
		//borderSize.setOnMouseReleased(event -> {
		//hago un try catch por si no hay ninguna figura seleccionada
//			try{
//				selectedFigureExists();
//				selectedFigure.setLineWidth(borderSize.getValue());
//				redrawCanvas();
//			}
//			catch(NoSelectedFigureException ex){
//				System.out.println(ex.getMessage());
//			}
//
//		});
//		private void selectedFigureExists(){
//			throw new NoSelectedFigureException();
//		}


		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

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


		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			StringBuilder label = new StringBuilder();
			ColoredFigure last = null;
			for (ColoredFigure figure : canvasState.figures()) {
				if (figure.figureBelongs(eventPoint)) {
					last = figure;
					label.append(figure);
				}
			}
			statusPane.updateStatus((last == null) ? eventPoint.toString() : label.toString());
		});


		canvas.setOnMouseClicked(event -> {

			Point eventPoint = new Point(event.getX(), event.getY());
			if (selectedFigure != null && cpyFormat.isSelected()) {
				Double auxWidth=selectedFigure.getLineWidth();
				String auxLineColor=selectedFigure.getLineColor();
				String auxFillColor=selectedFigure.getFillColor();
				ColoredFigure aux = selectedFigure;
				find(eventPoint);
				if(selectedFigure!=null) {
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
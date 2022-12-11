package frontend;

import backend.CanvasState;
import backend.Timetravel;
import backend.action.ActionState;
import backend.action.ActionType;
import backend.exception.NothingToDoException;
import backend.model.*;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiFunction;


public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	private static final String FIGURE_SELECTION_LINE_COLOR_HEX = "#FF0000";


	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color fillColor = Color.YELLOW;
	private Double sliderWidth = 1.0;

	// Botones Barra Izquierda (Vbox)
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton ellipseButton = new ToggleButton("Elipse");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");
	private final ToggleButton cpyFormat = new ToggleButton("Cop.Form.");
	private final Slider borderSize = new Slider(1.0, 50.0, 26.0);
	private final ColorPicker LineColorPicker = new ColorPicker(lineColor); //paneles de color seteados por default como se pedia
	private final ColorPicker fillColorPicker = new ColorPicker(fillColor);

	//Botones de Barra superior (Hbox)
	// Se crean los botones con sus respectivos iconos
	private final String undoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("undoIcon");
	private final Image undoIcon = new Image(HTMLEditorSkin.class.getResource(undoIconPath).toString());
	private final Button undoButton = new Button("Deshacer", new ImageView(undoIcon));
	private final String redoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("redoIcon");
	private final Image redoIcon = new Image(HTMLEditorSkin.class.getResource(redoIconPath).toString());

	private final Button redoButton = new Button("Rehacer", new ImageView(redoIcon));
	private final String cutIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("cutIcon");
	private final Image cutIcon = new Image(HTMLEditorSkin.class.getResource(cutIconPath).toString());
	private final Button cut = new Button("Cortar", new ImageView(cutIcon));
	private final String copyIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("copyIcon");
	private final Image copyIcon = new Image(HTMLEditorSkin.class.getResource(copyIconPath).toString());
	private final Button copy = new Button("Copiar", new ImageView(copyIcon));
	private final String pasteIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("pasteIcon");
	private final Image pasteIcon = new Image(HTMLEditorSkin.class.getResource(pasteIconPath).toString());
	private final Button paste = new Button("Pegar", new ImageView(pasteIcon));
	private final Label undoLabel = new Label("");

	private final Label undoCounter = new Label("0");
	private final Label redoLabel = new Label("");
	private final Label redoCounter = new Label("0");


	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private ColoredFigure selectedFigure = null;



	private ColoredFigure clipboard;


	// StatusBar
	private final StatusPane statusPane;

	private final Timetravel timetravelInstance = new Timetravel();

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		//guardamos todos los botonoes de la Hbox
		Button[] shortcut = {cut, copy, paste, undoButton, redoButton};
		HBox shortcuts = new HBox(10);

		shortcuts.getChildren().addAll(cut, copy, paste);  // agregamos los botones de cortar, copiar y pegar
		for (Button tool : shortcut) { //itera por los botones para setear su tamaño
			tool.setMinWidth(90);
			tool.setCursor(Cursor.HAND);
		}

		shortcuts.setPadding(new Insets(5));

		HBox timetravel = new HBox(10);  //creamos la Hbox para los botones de timetravel (undo y redo y sus respectivos lables)
		undoLabel.setMinWidth(300); //seteamos y alineamos el tamaño de los labels
		undoLabel.setAlignment(Pos.CENTER_RIGHT);
		redoLabel.setMinWidth(300);

		timetravel.getChildren().addAll( undoLabel, undoCounter, undoButton, redoButton, redoCounter, redoLabel); // agregamos los botones y labels a la Hbox
		timetravel.setPadding(new Insets(5));
		timetravel.setStyle("-fx-background-color: #999; -fx-alignment: center");


		BorderPane topPane = new BorderPane(); // creamos el BorderPane para la barra superior
		topPane.setTop(shortcuts); // agregamos la Hbox de los botones de cortar, copiar y pegar
		topPane.setBottom(timetravel); // agregamos la Hbox de los botones de timetravel
		setTop(topPane); // agregamos el BorderPane a la barra superior

		shortcuts.setStyle("-fx-background-color: #999");
		//estilo de los botones
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, cpyFormat};
		ToggleButton[] figureButtonArr = {rectangleButton,circleButton,squareButton,ellipseButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) { //itera por los botones para setear su tamaño
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		//crea un nuevo layout con todos los botones de forma vertical a la izq uno abajo del otro
		VBox buttonsBox = new VBox(10);


		buttonsBox.getChildren().addAll(toolsArr); //agrega todos los botones del toolArr VBox
		borderSize.setShowTickMarks(true); // muestra los ticks en el slider
		borderSize.setShowTickLabels(true); // muestra los labels en el slider
		buttonsBox.getChildren().addAll(new Text("Borde"), borderSize, LineColorPicker); //agrega el slider y el colorpicker al VBox
		buttonsBox.getChildren().addAll(new Text("Relleno"), fillColorPicker); //agrega el colorpicker al VBox


		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(sliderWidth);
		FrontGraphicsController fgc = new FrontGraphicsController(gc); //crea un nuevo controlador de graficos

		Map<ToggleButton, BiFunction<Point,Point, ColoredFigure>> buttonMap = new HashMap<ToggleButton, BiFunction<Point,Point, ColoredFigure>>() {{
			put(figureButtonArr[0], (startPoint,endPoint) -> new Rectangle(fgc, startPoint, endPoint, lineColor.toString(), fillColor.toString(), sliderWidth));
			put(figureButtonArr[1], (startPoint,endPoint) -> new Circle(fgc, startPoint, Math.abs(endPoint.getX() - startPoint.getX()),
					lineColor.toString(), fillColor.toString(), sliderWidth));
			put(figureButtonArr[2], (startPoint,endPoint) -> new Square(fgc, startPoint, Math.abs(endPoint.getX() - startPoint.getX()),
					lineColor.toString(), fillColor.toString(), sliderWidth));
			put(figureButtonArr[3], (startPoint,endPoint) -> new Ellipse(fgc,
					(new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2))),
							Math.abs(endPoint.getX() - startPoint.getX()),
							Math.abs(endPoint.getY() - startPoint.getY()), lineColor.toString(), fillColor.toString(), sliderWidth));

		}}; //crea un mapa con los botones de figuras y sus respectivas funciones

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		}); //cuando se presiona el mouse se guarda el punto de inicio

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return;
			}
			ColoredFigure newFigure;
			BiFunction<Point,Point,ColoredFigure> figureFunction;
			for (ToggleButton button : figureButtonArr){
				if (button.isSelected()){
					figureFunction = buttonMap.get(button);//obtiene la funcion de la figura seleccionada
					newFigure = figureFunction.apply(startPoint,endPoint);
					timetravelInstance.add(new ActionState(ActionType.DRAW, canvasState.figures(), newFigure,clipboard)); //agrega el estado al timetravel
					canvasState.addFigure(newFigure);//agrega la figura al canvas
					startPoint = null;
					updateLabels(); // actualiza los labels de undo y redo
					redrawCanvas(); //redibuja el canvas
				}
			}
		}); // cuando se suelta el mouse se guarda el punto final y se crea la figura

		canvas.setOnMouseClicked(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			if (selectedFigure != null && cpyFormat.isSelected()) { //Se copia el fomrato de una figura valida
				timetravelInstance.add(new ActionState(ActionType.COPY_FORMAT, canvasState.figures(), selectedFigure,clipboard)); //se agrega al timetravel(undo/redo)
				double auxWidth = selectedFigure.getLineWidth(); //getters
				String auxLineColor = selectedFigure.getLineColor();
				String auxFillColor = selectedFigure.getFillColor();
				ColoredFigure aux = selectedFigure; // se crea un auxiliar para no perder la referencia y que no se cambie la seleccionada
				find(eventPoint); //se busca la figura a la que se le aplica el fomrato (find cambia selected figure)
				selectedFigure.setFillColor(auxFillColor);//setters a la figura a aplicarse el formato
				selectedFigure.setLineColor(auxLineColor);
				selectedFigure.setLineWidth(auxWidth);
				selectedFigure = aux; // se vuelve a seleccionar la figura original
				selectionButton.setSelected(true); //se pone el boton de seleccionar como seleccionado
			} else if (selectionButton.isSelected()) { //Se selecciona una figura si es que existe una en esas coordenadas
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				selectedFigure = null; // se pone en null por si quiere hacer click afuera para deseleccionar
				find(eventPoint);
				statusPane.updateStatus((selectedFigure == null) ? "Ninguna figura encontrada." : label.append(selectedFigure).toString());
			}
			updateLabels();
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {//se mueve la figura seleccionada
			Point eventPoint = new Point(event.getX(), event.getY());
			statusPane.updateStatus(String.format("{%s}", eventPoint));
			if (selectionButton.isSelected() && selectedFigure != null) {
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100.0;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100.0;
				selectedFigure.moveFigure(100.0 * diffX, 100.0 * diffY);
				startPoint.movePoint(0, 0);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> { //se borra la figura seleccionada
			if (selectedFigure != null) {
				timetravelInstance.add(new ActionState(ActionType.DELETE, canvasState.figures(), selectedFigure,clipboard));
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				updateLabels();
				redrawCanvas();
			}
		});

		undoButton.setOnAction(event -> { //se hace undo
			try {
				ActionState actionState = timetravelInstance.undo();
				clipboard = actionState.getCopied();
				timetravelInstance.addRedo(new ActionState(actionState.getActionType(), canvasState.figures(), actionState.getFigureAffected(), clipboard));
				canvasState.update(actionState.figures());
				updateLabels();
				redrawCanvas();
			} catch (NothingToDoException ex) {
				showAlarm(ex.getMessage());
			}

		});
		redoButton.setOnAction(event -> { //se hace redo
			try {
				ActionState actionState = timetravelInstance.redo();
				timetravelInstance.addUndo(new ActionState(actionState.getActionType(), canvasState.figures(), actionState.getFigureAffected(),clipboard));
				canvasState.update(actionState.figures());
				updateLabels();
				redrawCanvas();
			} catch (NothingToDoException ex) {
				showAlarm(ex.getMessage());
			}
			updateLabels();
			redrawCanvas();


		});

		//
		fillColorPicker.setOnAction(event -> { //se cambia el color de relleno
			fillColor = fillColorPicker.getValue();
			if (selectedFigure != null) {
				timetravelInstance.add(new ActionState(ActionType.FILL_COLOR, canvasState.figures(), selectedFigure,clipboard));
				selectedFigure.setFillColor(fillColor.toString());
				redrawCanvas();
			}
		});

		LineColorPicker.setOnAction(event -> { //se cambia el color de linea
			lineColor = LineColorPicker.getValue();
			if (selectedFigure != null) {
				timetravelInstance.add(new ActionState(ActionType.LINE_COLOR, canvasState.figures(), selectedFigure,clipboard));
				selectedFigure.setLineColor(LineColorPicker.getValue().toString());
				redrawCanvas();
			}
		});

		borderSize.setOnMouseDragged(event -> { //se cambia el tamaño de la linea
			sliderWidth = borderSize.getValue();
			if (selectedFigure != null) {
				selectedFigure.setLineWidth(sliderWidth);
				redrawCanvas();
			}
		});


		copy.setOnAction(event -> { //se copia la figura seleccionada
			if (selectedFigure != null) {
				timetravelInstance.add(new ActionState(ActionType.COPY, canvasState.figures(), selectedFigure,clipboard));
				clipboard = selectedFigure;
				updateLabels();
			}
		});

		paste.setOnAction(event -> { //se pega la figura copiada
			if (clipboard != null) {
				timetravelInstance.add(new ActionState(ActionType.PASTE, canvasState.figures(), selectedFigure,clipboard));
				ColoredFigure figure = clipboard.copyFigure(new Point(canvas.getWidth() / 2, canvas.getHeight() / 2));
				canvasState.addFigure(figure);
				updateLabels();
				redrawCanvas();
			}
		});

		cut.setOnAction(event -> { //se corta la figura seleccionada
			if (selectedFigure != null) {
				timetravelInstance.add(new ActionState(ActionType.CUT, canvasState.figures(), selectedFigure,clipboard));
				clipboard = selectedFigure;
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				updateLabels();
				redrawCanvas();
			}
		});


		Map<KeyCode, Button> keyCodeMap = new EnumMap<KeyCode, Button>(KeyCode.class){{ //se asignan los botones a las teclas
			put(KeyCode.V, paste);
			put(KeyCode.C, copy);
			put(KeyCode.X, cut);
			put(KeyCode.Z, undoButton);
			put(KeyCode.Y, redoButton);
		}};

		this.setOnKeyPressed(keyEvent -> { //se asignan las teclas de atajo
			if (keyEvent.isControlDown()) {
				keyCodeMap.get(keyEvent.getCode()).fire();
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

	public void find(Point eventPoint) { //metodo que sirve para encontrar la figura que se ha seleccionado
		for (ColoredFigure figure : canvasState.figures()) {
			if (figure.containsPoint(eventPoint)) {
				selectedFigure = figure;
			}
		}
	}


	private void updateLabels() { //metodo para actualizar los lables del undo y redo
		undoLabel.setText(String.format("%s", timetravelInstance.getUndoSize() != 0 ? timetravelInstance.getUndoLastAction() : ""));
		undoCounter.setText(String.format("[%d]", timetravelInstance.getUndoSize()));
		redoCounter.setText(String.format("[%d]", timetravelInstance.getRedoSize()));
		redoLabel.setText(String.format("%s", timetravelInstance.getRedoSize() != 0 ? timetravelInstance.getRedoLastAction() : ""));

	}

	private void showAlarm(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}
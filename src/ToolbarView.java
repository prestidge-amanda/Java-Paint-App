import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.control.ColorPicker;
import javafx.event.EventHandler;
import javafx.event.Event;

public class ToolbarView extends VBox implements IView {
    private Model model;
    private ToolBar tools = new ToolBar();
    private ToggleButton selection = new ToggleButton();
    private ToggleButton eraser = new ToggleButton();
    private ToggleButton line = new ToggleButton();
    private ToggleButton circle = new ToggleButton();
    private ToggleButton rect = new ToggleButton();
    private ToggleButton fill = new ToggleButton();
    private ToggleButton thicc1 = new ToggleButton();
    private ToggleButton thicc2 = new ToggleButton();
    private ToggleButton thicc3 = new ToggleButton();
    private ToggleButton style1 = new ToggleButton();
    private ToggleButton style2 = new ToggleButton();
    private ToggleButton style3 = new ToggleButton();
    private ToggleGroup toolsToggle = new ToggleGroup();
    private ToggleGroup thiccToggle = new ToggleGroup();
    private ToggleGroup styleToggle = new ToggleGroup();
    private ColorPicker lineC = new ColorPicker();
    private ColorPicker fillC = new ColorPicker();


    ToolbarView (Model model){
        this.model=model;
        this.setHeight(600);
        this.setWidth(100);

        // set up tool bar
        setUpButtons();
        tools.setOrientation(Orientation.VERTICAL);
        toolsToggle.getToggles().addAll(selection,eraser,rect,fill);
        thiccToggle.getToggles().addAll(thicc1,thicc2,thicc3);
        styleToggle.getToggles().addAll(style1,style2,style3);
        tools.setPrefHeight(600);
        tools.setPrefWidth(100);
        toolsToggle.selectToggle(selection);
        thiccToggle.selectToggle(thicc1);
        styleToggle.selectToggle(style1);


        // Set colors
        lineC.setOnAction(new EventHandler() {
            public void handle(Event t) {
                model.setLineC(lineC.getValue());
            }
        });
        fillC.setOnAction(new EventHandler() {
            public void handle(Event t) {
                model.setFillC(fillC.getValue());
            }
        });

        lineC.setValue(Color.TEAL);
        fillC.setValue(Color.TEAL);

        // set tool num
        selection.setOnMouseClicked(mouseEvent -> {
            model.setToolNum(1);
        });

        eraser.setOnMouseClicked(mouseEvent -> {
            model.setToolNum(2);
        });

        line.setOnMouseClicked(mouseEvent -> {
            model.setToolNum(3);
        });

        circle.setOnMouseClicked(mouseEvent -> {
            model.setToolNum(4);
        });

        rect.setOnMouseClicked(mouseEvent -> {
            model.setToolNum(5);
        });

        fill.setOnMouseClicked(mouseEvent -> {
            model.setToolNum(6);
        });

        // Set line thickness
        thicc1.setOnMouseClicked(mouseEvent -> {
            model.setThiccNum(1);
        });

        thicc2.setOnMouseClicked(mouseEvent -> {
            model.setThiccNum(2);
        });

        thicc3.setOnMouseClicked(mouseEvent -> {
            model.setThiccNum(3);
        });

        // Set line style
        style1.setOnMouseClicked(mouseEvent -> {
            model.setStyleNum(1);
        });

        style2.setOnMouseClicked(mouseEvent -> {
            model.setStyleNum(2);
        });

        style3.setOnMouseClicked(mouseEvent -> {
            model.setStyleNum(3);
        });

        tools.getItems().addAll(new Separator(),new Label("Tools"),new HBox(selection,eraser),new HBox(rect,fill),new Separator(),new Label("Line Colour"),lineC,
                new Label("Fill Colour"),fillC,new Separator(),
                new Label("Line Thickness"),new HBox(thicc1,thicc2,thicc3),new Separator(),new Label("Line Style"), new HBox(style1,style2,style3),new Separator());

        // Add all tools to Vbox
        this.getChildren().add(tools);

        model.addView(this);
    }

    public void updateView() {
            if(model.getNumShapes()>0){
                eraser.setDisable(false);
                selection.setDisable(false);
                fill.setDisable(false);
            }else{
                eraser.setDisable(true);
                selection.setDisable(true);
                fill.setDisable(true);
            }

            fillC.setDisable(false);
            lineC.setValue(model.getLineC());
            fillC.setValue(model.getFillC());
            if (model.getToolNum() == 1) {
                toolsToggle.selectToggle(selection);
            } else if (model.getToolNum()  == 2) {
                toolsToggle.selectToggle(eraser);
            } else if (model.getToolNum()  == 3) {
                toolsToggle.selectToggle(line);
                fill.setDisable(true);
                fillC.setDisable(true);
            } else if (model.getToolNum()  == 4) {
                toolsToggle.selectToggle(circle);
            } else if (model.getToolNum()  == 5) {
                toolsToggle.selectToggle(rect);
            } else if (model.getToolNum()  == 6) {
                toolsToggle.selectToggle(fill);
            }

            // Set line thickness
            if (model.getThiccNum()  == 1) {
                thiccToggle.selectToggle(thicc1);
            } else if (model.getThiccNum()  == 2) {
                thiccToggle.selectToggle(thicc2);
            } else if (model.getThiccNum()  == 3) {
                thiccToggle.selectToggle(thicc3);
            }

            // Set line style
            if (model.getStyleNum() ==1){
                styleToggle.selectToggle(style1);
            }else if(model.getStyleNum() ==2){
                styleToggle.selectToggle(style2);
            }else if (model.getStyleNum() ==3){
                styleToggle.selectToggle(style3);
            }


    }

    private void setUpButtons(){
        // General tool images
        ImageView bucketImage = new ImageView(new Image("/images/bucket.png"));
        ImageView circleImage = new ImageView(new Image("/images/circle.png"));
        ImageView eraserImage = new ImageView( new Image("/images/eraser.png"));
        ImageView lineImage = new ImageView(new Image("/images/line.png"));
        ImageView selectionImage = new ImageView(new Image("/images/selection.png"));
        ImageView square = new ImageView(new Image("/images/square.png"));
        bucketImage.setFitHeight(50);
        bucketImage.setFitWidth(50);
        circleImage.setFitHeight(50);
        circleImage.setFitWidth(50);
        eraserImage.setFitHeight(50);
        eraserImage.setFitWidth(50);
        lineImage.setFitHeight(50);
        lineImage.setFitWidth(50);
        selectionImage.setFitHeight(50);
        selectionImage.setFitWidth(50);
        square.setFitHeight(50);
        square.setFitWidth(50);
        fill.setGraphic(bucketImage);
        eraser.setGraphic(eraserImage);
        circle.setGraphic(circleImage);
        line.setGraphic(lineImage);
        rect.setGraphic(square);
        selection.setGraphic(selectionImage);

        // Set thickness tools
        Line l1 = new Line();
        l1.setStrokeWidth(1);
        l1.setStartX(0);
        l1.setEndX(30);
        Line l2 = new Line();
        l2.setStrokeWidth(2);
        l2.setStartX(0);
        l2.setEndX(30);
        Line l3 = new Line();
        l3.setStrokeWidth(3);
        l3.setStartX(0);
        l3.setEndX(30);
        thicc1.setPrefSize(42,50);
        thicc1.setGraphic(l1);
        thicc2.setPrefSize(42,50);
        thicc2.setGraphic(l2);
        thicc3.setPrefSize(42,50);
        thicc3.setGraphic(l3);

        // Set style tools
        Line l4 = new Line();
        l4.setStrokeWidth(1);
        l4.setStartX(0);
        l4.setEndX(30);
        Line l5 = new Line();
        l5.setStrokeWidth(1);
        l5.setStartX(0);
        l5.setEndX(30);
        l5.getStrokeDashArray().addAll(3.0,5.0);
        Line l6 = new Line();
        l6.setStrokeWidth(1);
        l6.setStartX(0);
        l6.setEndX(30);
        l6.getStrokeDashArray().addAll(1.0,2.0);

        style1.setPrefSize(42,50);
        style1.setGraphic(l4);
        style2.setPrefSize(42,50);
        style2.setGraphic(l5);
        style3.setPrefSize(42,50);
        style3.setGraphic(l6);

    }
}
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javafx.scene.input.MouseButton;

import static java.lang.StrictMath.abs;

public class CanvasView extends Pane implements IView {
    private Model model;
    final ContextMenu clip = new ContextMenu();
    ArrayList<Shape> shapes = new ArrayList();
    boolean makingShape;
    CanvasView(Model model){
        this.model=model;
        layoutView();
        final ContextMenu clip = new ContextMenu();
        MenuItem ei1 = new MenuItem("Copy");
        MenuItem ei2 = new MenuItem("Cut");
        MenuItem ei3 = new MenuItem("Paste");
        ei1.setOnAction(e->{
            model.setCopyItem();
        });
        ei2.setOnAction(e->{
            model.setCutItem();
        });
        ei3.setOnAction(e->{
            model.setPasteTruth(true);
            e.consume();
        });

        clip.getItems().addAll(ei1,ei2,ei3);


        this.setOnContextMenuRequested(e->{
            if(model.getToolNum()==1){
                clip.show(this, e.getScreenX(),e.getScreenY());
                model.setPaste(e.getX(),e.getY());
            }
        });


        // Deal with clicks
        this.setOnMouseClicked(mouseEvent -> {
            // Handle drawing rectangle
            if(model.getToolNum()==5&&!(makingShape)){
                Rectangle r = new Rectangle(10,10);
                r.setX(mouseEvent.getX());
                r.setY(mouseEvent.getY());
                r.setStrokeLineCap(StrokeLineCap.BUTT);
                r.setFill(model.getFillC());
                r.setStroke(model.getLineC());
                if (model.getThiccNum()==2){
                    r.setStrokeWidth(2);
                }else if (model.getThiccNum()==3){
                    r.setStrokeWidth(3);
                }
                if (model.getStyleNum()==2){
                    r.getStrokeDashArray().addAll(3.0,5.0);
                }else if (model.getStyleNum()==3){
                    r.getStrokeDashArray().addAll(1.0,2.0);
                }
                model.setCurrX(mouseEvent.getX());
                model.setCurrY(mouseEvent.getY());
                makingShape=true;
                model.makeNew();
                this.getChildren().add(r);
                model.setShapeSelected(-1);
                addShapeEvents(this.getChildren().size()-1);
            }else if(makingShape=true && model.getToolNum()==5){
                makingShape=false;
            }
        });

        this.setOnMouseMoved(mouseEvent -> {
            if((model.getToolNum() ==5)&&makingShape){
                Rectangle r = new Rectangle(10,10);
                r.setFill(model.getFillC());
                r.setStroke(model.getLineC());
                r.setStrokeLineCap(StrokeLineCap.BUTT);
                if (model.getThiccNum()==2){
                    r.setStrokeWidth(2);
                }else if (model.getThiccNum()==3){
                    r.setStrokeWidth(3);
                }
                if (model.getStyleNum()==2){
                    r.getStrokeDashArray().addAll(3.0,5.0);
                }else if (model.getStyleNum()==3){
                    r.getStrokeDashArray().addAll(1.0,2.0);
                }

                r.setWidth(mouseEvent.getX()-model.getCurrX());
                r.setX(model.getCurrX());
                model.setShapeWidth(mouseEvent.getX()-model.getCurrX(),this.getChildren().size()-1);

                r.setHeight(mouseEvent.getY()-model.getCurrY());
                r.setY(model.getCurrY());
                model.setShapeHeight(mouseEvent.getY()-model.getCurrY(),this.getChildren().size()-1);
                this.getChildren().remove(this.getChildren().size()-1);
                this.getChildren().add(r);
                addShapeEvents(this.getChildren().size()-1);
            }
        });
        model.addView(this);
    }

    private void addShapeEvents(int i){
        Node a = this.getChildren().get(i);
        a.setOnContextMenuRequested(e->{
            model.setRightItem(this.getChildren().indexOf(a));
        });
        a.setOnMouseClicked(MouseEvent -> {
            if(MouseEvent.getButton()==MouseButton.SECONDARY){
                model.setRightItem(this.getChildren().indexOf(a));
            }
            else if (model.getToolNum()==1){
                model.setShapeSelected(this.getChildren().indexOf(a));
            }else if (model.getToolNum()==2){
                model.removeShape(this.getChildren().indexOf(a));
                model.setShapeSelected(-1);
                this.getChildren().remove(a);
            }else if (model.getToolNum()==6){
                model.setShapeSelected(this.getChildren().indexOf(a));
                model.setShapeFill(this.getChildren().indexOf(a));
                model.setShapeSelected(-1);
            }
        });
        a.setOnMouseDragged(MouseEvent -> {
            if (model.getToolNum()==1){
                if((a.getBoundsInParent().getMinX()+(MouseEvent.getX()-a.getBoundsInLocal().getCenterX()))>0 &&
                        a.getBoundsInParent().getMaxX()+(MouseEvent.getX()-a.getBoundsInLocal().getCenterX())<this.getBoundsInLocal().getMaxX()){
                    a.setTranslateX(a.getTranslateX()+(MouseEvent.getX()-a.getBoundsInLocal().getCenterX()));
                    model.setShapeTranslateX(a.getTranslateX()+(MouseEvent.getX()-a.getBoundsInLocal().getCenterX()),this.getChildren().indexOf(a));
                }
                if ((a.getBoundsInParent().getMinY()+(MouseEvent.getY()-a.getBoundsInLocal().getCenterY()))>0 &&
                        a.getBoundsInParent().getMaxY()+(MouseEvent.getY()-a.getBoundsInLocal().getCenterY())<this.getBoundsInLocal().getMaxY()){
                    model.setShapeTranslateY(a.getTranslateY()+(MouseEvent.getY()-a.getBoundsInLocal().getCenterY()),this.getChildren().indexOf(a));
                    a.setTranslateY(a.getTranslateY()+(MouseEvent.getY()-a.getBoundsInLocal().getCenterY()));
                }
            }
        });
    }


    private void layoutView(){
        this.setPrefHeight(600);
        this.setPrefWidth(800);
    }

    public void updateView(){
        if (model.getPaste() && (model.getCopyItem()>=0||model.getCutItem()>=0)){
            int i= model.getCopyItem();
            if (model.getCutItem()>=0){
                i=model.getCutItem();
            }
            Rectangle r = new Rectangle(model.shapeWidth(i),model.shapeHeight(i));
            r.setX(model.getPasteX());
            r.setY(model.getPasteY());
            r.setStrokeLineCap(StrokeLineCap.BUTT);
            r.setFill(model.shapefill(i));
            r.setStroke(model.shapeLine(i));
            if (model.shapeThicc(i)==2){
                r.setStrokeWidth(2);
            }else if (model.shapeThicc(i)==3){
                r.setStrokeWidth(3);
            }
            if (model.shapeStyle(i)==2){
                r.getStrokeDashArray().addAll(3.0,5.0);
            }else if (model.shapeStyle(i)==3){
                r.getStrokeDashArray().addAll(1.0,2.0);
            }
            this.getChildren().add(r);
            model.copyItemTo(i);
            addShapeEvents(this.getChildren().size()-1);
            if(model.getCutItem()>=0){
                this.getChildren().remove(i);
                model.removeShape(i);
            }
            model.setPaste(-1,-1);
            model.setRightItem(-1);
            model.setCopyItem();
            model.setCutItem();
            model.setShapeSelected(-1);
            model.setPasteTruth(false);
        }
         if(model.getKey()==KeyCode.ESCAPE){
            model.setShapeSelected(-1);
        }else if (model.getKey()==KeyCode.DELETE&&model.getShapeSelected()!=-1){
            this.getChildren().remove(model.getShapeSelected());
            model.removeShape(model.getShapeSelected());
            model.setShapeSelected(-1);
        }else if(model.getShapeSelected()!=-1) {
            if (model.shapeType(model.getShapeSelected())==5){
                Rectangle r = new Rectangle(model.shapeWidth(model.getShapeSelected()),model.shapeHeight(model.getShapeSelected()));
                r.setX(model.shapeX(model.getShapeSelected()));
                r.setY(model.shapeY(model.getShapeSelected()));
                r.setTranslateX(model.shapeTranslateX(model.getShapeSelected()));
                r.setTranslateY(model.shapeTranslateY(model.getShapeSelected()));
                r.setStrokeLineCap(StrokeLineCap.BUTT);
                r.setFill(model.shapefill(model.getShapeSelected()));
                r.setStroke(model.shapeLine(model.getShapeSelected()));
                if (model.shapeThicc(model.getShapeSelected())==2){
                    r.setStrokeWidth(2);
                }else if (model.shapeThicc(model.getShapeSelected())==3){
                    r.setStrokeWidth(3);
                }
                if (model.shapeStyle(model.getShapeSelected())==2){
                    r.getStrokeDashArray().addAll(3.0,5.0);
                }else if (model.shapeStyle(model.getShapeSelected())==3){
                    r.getStrokeDashArray().addAll(1.0,2.0);
                }
                this.getChildren().remove(model.getShapeSelected());
                this.getChildren().add(model.getShapeSelected(),r);
                addShapeEvents(model.getShapeSelected());
            }
        }
    }
}

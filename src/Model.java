import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import java.awt.*;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;

public class Model {

    // the data in the model, just a counter
    private int toolNum=1;
    private int thiccNum=1;
    private int styleNum=1;
    private double currX;
    private double currY;
    private int shapeSelected= -1;
    private int currIndex;
    private ArrayList<ShapeInfo> shapes = new ArrayList();
    private Color lineC;
    private Color fillC;
    private KeyCode k;
    private int rightItem=-1;
    private int cutItem=-1;
    private double pasteX=-1;
    private double pasteY=-1;
    private boolean paste=false;
    private int copyItem=-1;

    // all views of this model
    private ArrayList<IView> views = new ArrayList<IView>();

    // method that the views can use to register themselves with the Model
    // once added, they are told to update and get state from the Model
    public void addView(IView view) {
        views.add(view);
        view.updateView();
    }

    public int getNumShapes(){
        return shapes.size();
    }

    public int getCopyItem(){
        return copyItem;
    }

    public boolean getPaste(){
        return paste;
    }

    public void setPasteTruth(boolean b){
        paste=b;
        notifyObservers();
    }

    public int getCutItem(){
        return cutItem;
    }

    public double getPasteX(){
        return pasteX;
    }

    public double getPasteY(){
        return pasteY;
    }

    public void setPaste(double x,double y){
        pasteX=x;
        pasteY=y;
    }

    public void setCopyItem(){
        copyItem=rightItem;
        cutItem=-1;
    }

    public void setCutItem(){
        cutItem=rightItem;
        copyItem=-1;
    }

    public void setRightItem(int i){
        rightItem=i;
    }

    public double getCurrX() {
        return currX;
    }

    public double getCurrY(){
        return currY;
    }

    public Color getFillC(){
        return fillC;
    }

    public Color getLineC(){
        return lineC;
    }

    public int getShapeSelected (){return shapeSelected;}

    public int getToolNum(){return toolNum;}

    public int getThiccNum(){return thiccNum;}

    public int getStyleNum(){return styleNum;}

    public void setCurrX(double x){
        this.currX=x;
    }

    public void setCurrY(double y){
        this.currY=y;
    }

    public void setShapeWidth(double w, int i){
        shapes.get(i).width=w;
    }

    public void setShapeHeight(double h, int i){
        shapes.get(i).height=h;
    }

    public void setShapeTranslateX(double w, int i){
        shapes.get(i).translateX=w;
    }

    public void setShapeTranslateY(double h, int i){
        shapes.get(i).translateY=h;
    }

    public void makeNew(){
        ShapeInfo s = new ShapeInfo();
        s.shapeType=toolNum;
        s.thiccNum=thiccNum;
        s.styleNum=styleNum;
        s.fillC=fillC;
        s.lineC=lineC;
        s.x=currX;
        s.y=currY;
        s.translateX=0;
        s.translateY=0;
        s.width=10;
        s.height=10;
        shapes.add(s);
        notifyObservers();
    }

    public void copyItemTo(int i){
        ShapeInfo s = new ShapeInfo();
        s.shapeType=shapes.get(i).shapeType;
        s.thiccNum=shapes.get(i).thiccNum;
        s.styleNum=shapes.get(i).styleNum;
        s.fillC=shapes.get(i).fillC;
        s.lineC=shapes.get(i).lineC;
        s.translateX=0;
        s.translateY=0;
        s.x=pasteX;
        s.y=pasteY;
        s.width=shapes.get(i).width;
        s.height=shapes.get(i).height;
        shapes.add(s);
    }

    public int shapeType(int i){
        return shapes.get(i).shapeType;
    }

    public int shapeThicc(int i){
        return shapes.get(i).thiccNum;
    }

    public int shapeStyle(int i){
        return shapes.get(i).styleNum;
    }

    public Color shapefill(int i){
        return shapes.get(i).fillC;
    }

    public Color shapeLine(int i){
        return shapes.get(i).lineC;
    }

    public void setShapeFill(int i){
        shapes.get(i).fillC=fillC;
        notifyObservers();
    }

    public double shapeX(int i){
        return shapes.get(i).x;
    }

    public void removeShape(int i){
        shapes.remove(i);
    }

    public double shapeTranslateX(int i){
        return shapes.get(i).translateX;
    }

    public double shapeTranslateY(int i){
        return shapes.get(i).translateY;
    }

    public double shapeY(int i){
        return shapes.get(i).y;
    }

    public double shapeWidth(int i){
        return shapes.get(i).width;
    }

    public double shapeHeight(int i){
        return shapes.get(i).height;
    }


    public void updateShape(){
        notifyObservers();
    }
    public void setToolNum(int num){
        this.toolNum=num;
        shapeSelected=-1;
        notifyObservers();
    }

    public void setThiccNum(int num){
        if (shapeSelected!=-1){
            shapes.get(shapeSelected).thiccNum=num;
        }
        this.thiccNum=num;
        notifyObservers();
    }

    public void setStyleNum(int num){
        if (shapeSelected!=-1){
            shapes.get(shapeSelected).styleNum=num;
        }
        this.styleNum=num;
        notifyObservers();
    }

    public void setShapeSelected(int b){
        this.shapeSelected=b;
        if (b!=-1 && toolNum!=6){
            this.thiccNum=shapes.get(b).thiccNum;
            this.styleNum=shapes.get(b).styleNum;
            this.fillC=shapes.get(b).fillC;
            this.lineC=shapes.get(b).lineC;
            notifyObservers();
        }

    }

    public void setLineC(Color c){
        if (shapeSelected!=-1){
            shapes.get(shapeSelected).lineC=c;
        }
        lineC=c;
        notifyObservers();
    }

    public void setFillC(Color c){
        if (shapeSelected!=-1){
            shapes.get(shapeSelected).fillC=c;
        }
        fillC=c;
        notifyObservers();
    }

    public void sendKey(KeyCode r){
        k=r;
        System.out.println("setit");
        notifyObservers();
        k=KeyCode.A;
    }

    public KeyCode getKey(){
        return k;
    }

    // the model uses this method to notify all of the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private void notifyObservers() {
        for (IView view : this.views) {
            view.updateView();
        }
    }

    private class ShapeInfo{
        private int shapeType;
        private int thiccNum;
        private int styleNum;
        private Color fillC;
        private Color lineC;
        private double x;
        private double y;
        private double translateX;
        private double translateY;
        private double width;
        private double height;
    }
}



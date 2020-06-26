import javafx.application.Application;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import javax.xml.stream.EventFilter;


public class SketchIt extends Application {
    public void start(Stage stage) throws Exception{
        // create and initialize the Model to hold our counter
        Model model = new Model();

        // create each view, and tell them about the model
        // the views will register themselves with the model
        ToolbarView view1 = new ToolbarView(model);
        CanvasView view2 = new CanvasView(model);
        MenuView view3 = new MenuView(model);

        HBox g = new HBox(view1,view2);
        HBox.setHgrow(view2, Priority.ALWAYS);
        VBox grid = new VBox(view3,g);
        VBox.setVgrow(g,Priority.ALWAYS);

        // Add grid to a scene (and the scene to the stage)
        Scene scene = new Scene(grid, 900, 600);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                model.sendKey(keyEvent.getCode());
            }
        });

        stage.setScene(scene);
        stage.show();

    }
}
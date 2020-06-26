import javafx.scene.layout.Pane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuView extends MenuBar implements IView {
    private Model model;
    MenuView(Model model){
        this.model=model;
        Menu m=new Menu("File");
        MenuItem mi1 = new MenuItem("New");
        MenuItem mi2 = new MenuItem("Load");
        MenuItem mi3 = new MenuItem ("Save");
        MenuItem mi4 = new MenuItem ("Quit");
        m.getItems().addAll(mi1,mi2,mi3,mi4);
        this.getMenus().addAll(m);
        model.addView(this);
    }

    @Override
    public void updateView() {

    }
}

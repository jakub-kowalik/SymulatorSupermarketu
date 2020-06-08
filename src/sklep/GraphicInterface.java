package sklep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicInterface extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfejsKasy.fxml"));
        Parent root = loader.load();
        GraphicInterfaceController controller = loader.getController();
        Scene scene = new Scene(root);

        stage.setTitle("Symulacja Kas");
        stage.setScene(scene);
        stage.setOnHidden(e ->
            controller.shutdown());
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
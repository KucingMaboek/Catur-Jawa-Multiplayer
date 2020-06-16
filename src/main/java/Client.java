import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/mainmenu.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        root.getStylesheets().add(getClass().getResource("styling/style.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package Client.utils;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Helper {
    public static void changeStage(Event event, String namaView) {
        Node node = (Node) event.getSource();
        Stage currentStage = (Stage) node.getScene().getWindow();

        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(Helper.class.getResource("../../views/" + namaView + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Hello World");
            stage.setScene(scene);
            stage.setResizable(false);
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changePage(Event event, String namaView) {
        Node node = (Node) event.getSource();
        Scene oldScene = node.getScene();
        Stage stage = (Stage) oldScene.getWindow();
        try {
            Parent newPage = FXMLLoader.load(Helper.class.getResource("../../views/" + namaView + ".fxml"));
            Scene newScene = new Scene(newPage);
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error tidak ditemukan");
            e.printStackTrace();
        }
    }
}

package Client.controllers;

import Client.utils.TaskReadThread;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePlayController implements Initializable {

    @FXML
    private GridPane gp_papan;

    @FXML
    private Text txt_status;

    @FXML
    private Text txt_p1;

    @FXML
    private Text txt_p2;

    private TaskReadThread task = MainMenuController.task;
    private String[][] board;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        task.setGamePlayController(this);
        Gson gson = new Gson();
        board = gson.fromJson((String) task.papan.get("papan"), String[][].class);
        System.out.println("Test: " + board[0][0]);

        createPane();
//        setPlayerName(task.player.get("player1"),task.player.get("player2"));

    }

    private void addPane(int colIndex, int rowIndex) {
//        Pane pane = new Pane();
//        System.out.print(board[colIndex][rowIndex]);
        ImageView gaco = new ImageView(new Image(getClass().getResourceAsStream(
                "../../drawable/bunder.png"),
                105,
                105,
                true,
                true
        ));
        gaco.setOnMouseClicked(e -> {
            String indexLabel = String.valueOf((char) (colIndex + 65)) + (rowIndex + 1);
//            System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
            System.out.println(indexLabel);
            task.inputGaco(indexLabel);
        });
//        gp_papan.add(pane, colIndex, rowIndex);
        gp_papan.add(gaco, colIndex, rowIndex);
    }

    public void updatePane() {
        gp_papan.getChildren().clear();
        txt_status.setText((String) task.message.get("message"));

        int numCols = 3;
        int numRows = 3;
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j);
            }
        }
    }

    private void createPane() {
        int numCols = 3;
        int numRows = 3;
        txt_status.setText((String) task.message.get("message"));
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            colConstraints.setPrefWidth(30);
            gp_papan.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            rowConstraints.setPrefHeight(30);
            gp_papan.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j);
            }
        }
    }

    public void setPlayerName(Object p1, Object p2){
        txt_p1.setText((String) p1);
        txt_p2.setText((String) p2);
    }
}

package Client.controllers;

import Client.utils.TaskReadThread;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePlayController implements Initializable {
    private TaskReadThread task;
    private String[][] board;
    private String indexLabel;
    private boolean isFull = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        task = MainMenuController.task;
        task.setGamePlayController(this);

        createPane();
        txt_status.setText((String) task.message.get("message"));
        setPlayerName(task.player.get("player1"), task.player.get("player2"));

    }

    //Menambah pane pada gp_papan
    private void addPane(int colIndex, int rowIndex) {
        //Memberikan nilai default berupa gambar polos
        ImageView gaco = new ImageView(new Image(getClass().getResourceAsStream(
                "../../drawable/polos.png"),
                105,
                105,
                true,
                true
        ));

        //Mengecek apakah terdapat gaco
        if (board[rowIndex][colIndex] != null) {
            if (board[rowIndex][colIndex].equals("SATU")) {
                gaco = new ImageView(new Image(getClass().getResourceAsStream(
                        "../../drawable/bunder.png"),
                        105,
                        105,
                        true,
                        true
                ));
            } else if (board[rowIndex][colIndex].equals("DUA")) {
                gaco = new ImageView(new Image(getClass().getResourceAsStream(
                        "../../drawable/silang.png"),
                        105,
                        105,
                        true,
                        true
                ));
            }
        }

        //Memberikan listener
        gaco.setOnMouseClicked(e -> {
            if (isFull) { //Jika jumlah Gaco yang berada di papan sudah berjumlah 3
                if (indexLabel == null) {
                    indexLabel = String.valueOf((char) (colIndex + 65)) + (3 - rowIndex);
                } else {
                    indexLabel = indexLabel + "-" + (char) (colIndex + 65) + (3 - rowIndex);
                    System.out.println(indexLabel);
                    task.inputGaco(indexLabel);
                    indexLabel = null;
                }
            } else { //Jika jumlah Gaco yang berada di papan sudah berjumlah kurang dari 3
                indexLabel = String.valueOf((char) (colIndex + 65)) + (3 - rowIndex);
                System.out.println(indexLabel);
                task.inputGaco(indexLabel);
                indexLabel = null;
            }
        });
        //Menambah pane ke grid
        gp_papan.add(gaco, colIndex, rowIndex);
    }

    //Memperbarui nilai dari pane
    public void updatePane() {
        Gson gson = new Gson();
        board = gson.fromJson((String) task.papan.get("papan"), String[][].class);

        //Me-reset children
        gp_papan.getChildren().clear();
        int numCols = 3;
        int numRows = 3;
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j);
            }
        }

        //Jika jumlah Gaco yang berada di papan sudah berjumlah 3
        if (task.message.get("message").equals("Enter move (eg. A2-A3): ")) {
            isFull = true;
        }
        txt_status.setText((String) task.message.get("message"));
    }

    //Menginisialisasikan gp_papan
    private void createPane() {
        Gson gson = new Gson();
        board = gson.fromJson((String) task.papan.get("papan"), String[][].class);

        int numCols = 3;
        int numRows = 3;
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

    //Memberikan nilai pada text UI nama player
    private void setPlayerName(Object p1, Object p2) {
        txt_p1.setText("Player 1: " + p1);
        txt_p2.setText("Player 2: " + p2);
    }

    //FXML Variables
    @FXML
    private GridPane gp_papan;

    @FXML
    private Text txt_status;

    @FXML
    private Text txt_p1;

    @FXML
    private Text txt_p2;
}

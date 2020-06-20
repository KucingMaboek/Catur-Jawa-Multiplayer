package Client.controllers;

import Client.utils.TaskReadThread;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GamePlay implements Initializable {

    @FXML
    private GridPane gp_papan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println(TaskReadThread.papan);
//        System.out.println(TaskReadThread.message);

        if (TaskReadThread.papan != null) {
            String[] key = TaskReadThread.papan.values().toArray(new String[0]);
            String[] papangaco = new String[]{key[0]};
            System.out.println(Arrays.toString(papangaco));
        }

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

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        pane.setOnMouseClicked(e -> {
            String indexLabel = String.valueOf((char) (colIndex + 65)) + (rowIndex + 1);
//            System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
            System.out.println(indexLabel);
        });
        gp_papan.add(pane, colIndex, rowIndex);
    }

    private void updatePane() {
        gp_papan.getChildren().clear();
        //terima api dari program
    }
}

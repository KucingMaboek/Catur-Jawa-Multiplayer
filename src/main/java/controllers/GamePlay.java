package controllers;

import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GamePlay {

    @FXML
    private GridPane gp_papan;

    @FXML
    void dropCatur(DragEvent event) {
        System.out.println("ngedrop kk");
    }

    @FXML
    void grabCatur(MouseEvent event) {
        System.out.println("ngegrab kk");
    }

}

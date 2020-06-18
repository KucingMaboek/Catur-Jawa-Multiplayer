package Client.controllers;

import Client.utils.Helper;
import Client.utils.TaskReadThread;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainMenu {

    @FXML
    private TextField tf_nickname;

    @FXML
    private Text txt_status, txt_nickname;

    @FXML
    private ImageView img_loadingAnimation;

    @FXML
    void btn_play(ActionEvent event) {
        if (tf_nickname.getText().isEmpty()) {
            txt_nickname.setText("Jeneng ora oleh kosong");
        } else {
            txt_nickname.setText("Jenengmu : " + tf_nickname.getText());
            tf_nickname.setEditable(false);
            Image image = new Image(getClass().getResourceAsStream("../../drawable/loadinganim.gif"));
            img_loadingAnimation.setImage(image);
            task = new TaskReadThread(this, tf_nickname.getText());
            Thread thread = new Thread(task);
            thread.start();
            this.event = event;
            matchmakingStart();
        }
    }

    private TaskReadThread task;


    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> {
                if (searchOpponent()) {
                    matchmakingStop(event);
                }
            });
        }
    };
    private Timer timer = new Timer();
    private int refreshRate = 1;
    private long intervalPeriod = refreshRate * 1000;
    private Event event;

    private int loadCounter = 0;

    private boolean searchOpponent() {
        //Mencari musuh sampai ketemu
        StringBuilder temp = new StringBuilder();
        for (int i = 1; i < loadCounter; i++) {
            temp.append(".");
        }
        if (loadCounter <= 2) {
            loadCounter++;
        } else {
            loadCounter = 0;
            loadCounter++;
        }
        txt_status.setText("Nggoleki masalah" + temp);

        return !task.res.equals("waiting");
    }

    private void matchmakingStart() {
        long delay = 0;
        timer.scheduleAtFixedRate(timerTask, delay, intervalPeriod);
    }

    private void matchmakingStop(Event event) {
        timerTask.cancel();
        timer.cancel();
        Helper.changePage(event, "gameplay");
    }
}

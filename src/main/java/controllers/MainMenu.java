package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import utils.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class MainMenu {

    @FXML
    private TextField tf_nickname;

    @FXML
    private Text txt_status, txt_nickname;

    @FXML
    private ImageView img_loadingAnimation;

    @FXML
    void btn_play(ActionEvent event) throws IOException {
        if (tf_nickname.getText().isEmpty()) {
            txt_nickname.setText("Jeneng ora oleh kosong");
        } else {
            txt_nickname.setText("Jenengmu : " + tf_nickname.getText());
            tf_nickname.setEditable(false);
            animation();

            String test = tf_nickname.getText();
            out.println(test + "\n"); //Kirim nickname ke server
            out.flush();
            res = in.readLine();//Kalo dia Server.Player 1 dia bakal nerima pesan waiting disini kalo player 2 ga usah langsung masuk while aja
            System.out.print(res);

            this.event = event;

            while (true) {
                res = in.readLine();//Ini bakal listen ke server, apapun yg dikirim server lewat sini
                System.out.print(res);
                Map<String, Object> has = new Gson().fromJson(
                        res, new TypeToken<HashMap<String, String>>() {
                        }.getType() //ini convert dari json ke bentuk hashmap
                );
                if (has.get("status").equals("play")) { //Kalo status play dia inputin naruh atau perpindahannya
                    System.out.println("Put: ");
                    String send = scanner.nextLine();
                    out.print(send + "\n");//Kirim perpindahan atau tempat gaconya
                    out.flush();
                } else if (has.get("status").equals("wait")) { //Kalo status wait berarti bukan giliran dia maen
                    System.out.println(has.get("message"));
                }
                //Selain itu ada status papan. Artinya dia bawa matriks bentuk papan setelah perubahan
                //Selain itu ada juga status message, dia cuma bawa pesan biasa, bisa diliat diservernya
            }

//            matchmakingStart();
        }
    }


    private String res;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;

    @FXML
    private void initialize() throws IOException {
        Socket socket = new Socket("localhost", 3002);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        scanner = new Scanner(System.in);
        res = in.readLine(); //Disini bakal nerima send nickname
        System.out.print(res);
//        System.out.println("Ketik Sesuatu: ");
//        String test = scanner.nextLine(); //Ini inputin nickname
//        out.print(test+"\n"); //Kirim nickname ke server
//        out.flush();
//        res = in.readLine();//Kalo dia Server.Player 1 dia bakal nerima pesan waiting disini kalo player 2 ga usah langsung masuk while aja
//        System.out.print(res);
    }

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
        //search for opponent
        //placeholder algorithm


        boolean opponent;
        //Mencari musuh sampai ketemu

        //ini lo isi sama logika lo y jing

        opponent = false;
        //kalau musuh ga ketemu play animasi gabut
        if (res.equals("waiting")) {
            return false;
        }
        //kalau musuh ketemu
        else {
            return true;
        }

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

    private void animation() {
        Image image = new Image(getClass().getResourceAsStream("../drawable/loadinganim.gif"));
        img_loadingAnimation.setImage(image);
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
    }
}

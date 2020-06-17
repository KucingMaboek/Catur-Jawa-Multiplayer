package Client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client1 {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",3002);
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);
        String res = in.readLine(); //Disini bakal nerima send nickname
        System.out.print(res);
        System.out.println("Ketik Sesuatu: ");
        String test = scanner.nextLine(); //Ini inputin nickname
        out.print(test+"\n"); //Kirim nickname ke server
        out.flush();
        res = in.readLine();//Kalo dia Server.Player 1 dia bakal nerima pesan waiting disini kalo player 2 ga usah langsung masuk while aja
        System.out.print(res);
        while(true){
            res = in.readLine();//Ini bakal listen ke server, apapun yg dikirim server lewat sini
            System.out.print(res);
            Map<String, Object> has = new Gson().fromJson(
                    res, new TypeToken<HashMap<String, String>>() {}.getType() //ini convert dari json ke bentuk hashmap
            );
            if (has.get("status").equals("play")){ //Kalo status play dia inputin naruh atau perpindahannya
                System.out.println("Put: ");
                String send = scanner.nextLine();
                out.print(send+"\n");//Kirim perpindahan atau tempat gaconya
                out.flush();
            }else if (has.get("status").equals("wait")){ //Kalo status wait berarti bukan giliran dia maen
                System.out.println(has.get("message"));
            }
            //Selain itu ada status papan. Artinya dia bawa matriks bentuk papan setelah perubahan
            //Selain itu ada juga status message, dia cuma bawa pesan biasa, bisa diliat diservernya
        }
    }
}
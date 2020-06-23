package Server;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static ArrayList<Room> rooms;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3002);
        rooms = new ArrayList<>();
         while (true){
             Socket client = serverSocket.accept();
             if (rooms.isEmpty()){
                 Player newPlayer = new Player(client, Gaco.Player.SATU);
                 Room newRoom = new Room(newPlayer);
                 rooms.add(newRoom);
                 newPlayer.sendMessage("waiting");
             }else {
                 System.out.println("cek2");
                 Room lastRoom = rooms.get(rooms.size()-1);
                 if (lastRoom.isFull()){
                     Player newPlayer = new Player(client,Gaco.Player.SATU);
                     Room newRoom = new Room(newPlayer);
                     rooms.add(newRoom);
                     newPlayer.sendMessage("waiting");
                 }else {
                     lastRoom.setPlayer2(new Player(client, Gaco.Player.DUA));
                     lastRoom.getPlayer2().sendMessage("waiting");
                     HashMap<String,String> hashMap = new HashMap<>();
                     hashMap.put("status","nickname");
                     hashMap.put("player1",lastRoom.getPlayer1().getNickName());
                     hashMap.put("player2",lastRoom.getPlayer2().getNickName());
                     Gson gson = new Gson();
                     lastRoom.getPlayer1().sendMessage(gson.toJson(hashMap));
                     lastRoom.getPlayer2().sendMessage(gson.toJson(hashMap));
                     lastRoom.start();
                 }
             }
         }
    }
}

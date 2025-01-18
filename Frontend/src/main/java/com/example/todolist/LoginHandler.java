package com.example.todolist;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginHandler {
    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    public void ConnectionMaker(){
        try {
            socket = new Socket("127.0.0.1", 1103);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            System.out.println("error on the  connection making");
            System.out.println(e.getMessage());
        }
    }
    public boolean login(String username, String password) {
        ConnectionMaker();
        try{
            System.out.println("connection established");
            out.writeUTF("l");
            out.writeUTF(username);
            out.writeUTF(password);
            out.flush();
            boolean x = in.readBoolean();
            System.out.println(x);
            socket.close();
            return x;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
            // Handle the IOException appropriately
        }
    }

    public boolean register(String first, String last, String username, String password) {
        ConnectionMaker();
        try{
            out.writeUTF("r");
            out.writeUTF(username);
            out.writeUTF(password);
            out.writeUTF(first);
            out.writeUTF(last);
            out.flush();
            boolean x = in.readBoolean();
            socket.close();
            return x;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
            // Handle the IOException appropriately
        }
    }
}
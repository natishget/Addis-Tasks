package com.example.todolist;

import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class menu {
    String username;
    Socket socket;
    DataOutputStream out;
    DataInputStream in;


    public menu(){

    }
    public menu(String username){
        this.username = username;
    }

    public void ConnectionMaker(){
        try {
            socket = new Socket("127.0.0.1", 1103);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public String ListAsker(){
        ConnectionMaker();
        try {
            System.out.println(username);
            out.writeUTF("t");
            out.writeUTF(username);
            out.flush();
            String result =in.readUTF();
            socket.close();
            return result;

        } catch (
                IOException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            System.out.println("error is from ListAsker method of menu class");
                return "false";
            // Handle the IOException appropriately
        }
    }

    public String CatListAsker(int cat, String username){
        ConnectionMaker();
        try {
            System.out.println(cat);
            System.out.println(username);
            out.writeUTF("Cat");
            out.writeUTF(username);
            out.writeInt(cat);
            out.flush();
            String result =in.readUTF();
            socket.close();
            return result;

        } catch (
                IOException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            System.out.println("error is from ListAsker method of menu class");
            return "false";
            // Handle the IOException appropriately
        }
    }

    public int AddList(String username, String Title, String Description, int cat){
        ConnectionMaker();
        try{
            System.out.println(username);
            out.writeUTF("Add");
            out.writeUTF(username);
            out.writeUTF(Title);
            out.writeUTF(Description);
            out.writeInt(cat);
            out.flush();
            int result =in.readInt();
            socket.close();
            return result;

        } catch (
                IOException e) {
            System.out.println("Error occured in AddList method of menu class");
            return 0;
            // Handle the IOException appropriately
        }
    }

    public int EditList(String username, String Title, String Description, String FinalTitle, String FinalDescription, int cat){
        ConnectionMaker();
        try{
            System.out.println("EditList is called");
            out.writeUTF("Edit");
            out.writeUTF(username);
            out.writeUTF(Title);
            out.writeUTF(Description);
            out.writeUTF(FinalTitle);
            out.writeUTF(FinalDescription);
            out.writeInt(cat);
            out.flush();
            System.out.println("data is sent to server waiting for result");
            int result =in.readInt();
            socket.close();
            return result;

        } catch (
                IOException e) {
            System.out.println("Error occured in AddList method of menu class");
            return 0;
            // Handle the IOException appropriately
        }
    }

    public int DeleteList(String username, String Title, String Description){
        ConnectionMaker();
        try {
            System.out.println(username+" delete process is happening");
            out.writeUTF("D");
            out.writeUTF(username);
            out.writeUTF(Title);
            out.writeUTF(Description);
            out.flush();
            int result =in.readInt();
            socket.close();
            Home home = new Home(username);
            home.start(new Stage());
            System.out.println(result);
            return result;

        } catch (
                IOException e) {
            System.out.println("Error occured in AddList method of menu class");
            return 0;
            // Handle the IOException appropriately
        }
    }

    public String Status(String username, String Title, String Description, String DoneUndone){
        String value = DoneUndone;
        ConnectionMaker();
        try{
            System.out.println(username+" delete process is happening");
            out.writeUTF("Status");
            out.writeUTF(username);
            out.writeUTF(Title);
            out.writeUTF(Description);
            out.writeUTF(DoneUndone);
            out.flush();
            int result =in.readInt();
            socket.close();
            if (result > 0 && DoneUndone =="U")
                value = "D";
            if (result > 0 && DoneUndone =="D")
                value = "U";


        } catch (
                IOException e) {
            System.out.println("Error occured in Status method of menu class");
            // Handle the IOException appropriately
        }
        return value;
    }
}

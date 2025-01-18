import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    String username;
    String password;
    String last;
    String first;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1103);
            while (true) {
                System.out.println("server running on port: 1103");
                Socket socket = server.accept();
                System.out.println("server connected");
                new Thread(new HandleAClient(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("Error occured in main class of the server side program");
        }
    }
        static class HandleAClient implements Runnable {
            private Socket socket;

            public HandleAClient(Socket socket) {
                this.socket = socket;
            }

            public void run() {
                try {
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    while (true) {
                        String action = in.readUTF();
                        LoginHandler lh = new LoginHandler();
                        System.out.println(action);
                        switch (action) {
                            case "l": {
                                String username = in.readUTF();
                                String password = in.readUTF();
                                System.out.println("data recived" + username + password + "data end");
                                out.writeBoolean(lh.login(username, password));
                                System.out.println("data sent to clint about login");

                            }
                            break;
                            case "r": {
                                String username = in.readUTF();
                                String password = in.readUTF();
                                String first = in.readUTF();
                                String last = in.readUTF();
                                System.out.println("data " + username + password + last + first + "data end");
                                out.writeBoolean(lh.register(first, last, username, password));
                            }
                            break;
                            case "t": {
                                String username = in.readUTF();
                                System.out.println("data " + username + " data end");
                                out.writeUTF(lh.list(username));
                                System.out.println("data sent to client");
                            }
                            break;
                            case "Cat": {
                                System.out.println("Cat is On");
                                String username = in.readUTF();
                                int Cat = in.readInt();
                                System.out.println("data " + username + " data end");
                                out.writeUTF(lh.CatList(username, Cat));
                                System.out.println("data sent to client");
                            }
                            break;
                            case "Add": {
                                String username = in.readUTF();
                                String title = in.readUTF();
                                String description = in.readUTF();
                                int cat = in.readInt();
                                System.out.println(username + " Add list process");
                                out.writeInt(lh.AddList(username, title, description, cat));
                            }
                            break;
                            case "Edit": {
                                String username = in.readUTF();
                                String title = in.readUTF();
                                String description = in.readUTF();
                                String FinalTitle = in.readUTF();
                                String FinalDescription = in.readUTF();
                                int cat = in.readInt();
                                System.out.println(username + " Add list process");
                                out.writeInt(lh.EditList(username, title, description, FinalTitle, FinalDescription, cat));
                            }
                            break;
                            case "D": {
                                String username = in.readUTF();
                                String title = in.readUTF();
                                String description = in.readUTF();
                                System.out.println(username + " Add list process");
                                out.writeInt(lh.DeleteList(username, title, description));
                            }
                            case "Status": {
                                String username = in.readUTF();
                                String title = in.readUTF();
                                String description = in.readUTF();
                                String DoneUndone = in.readUTF();
                                System.out.println(username + " Add list process");
                                out.writeInt(lh.Status(username, title, description, DoneUndone));
                            }
                            break;
                            default: {
                                System.out.println("Defualt switch case");
                            }
                        }
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
}
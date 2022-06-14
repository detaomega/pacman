import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    String Name = "";
    String Score = "0"; 
    public void updateRecord(String type, String name, int score) {
        final String HOST = "140.113.235.151";
        final int PORT = 8787;
        
        System.out.println("Client started.");

        try {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
        
          
            out.println("udapte\n" + type + "\n" + name + "\n" + score);
            System.out.println("udapte " + type + " " + name + " " + score);

                // System.out.println("Echoed from server: " + in.nextLine());
            
        }
        catch(IOException e){
            System.err.println("Error opening file.");
        }
    }

    public void getRecord(String type, int rank) {
        final String HOST = "140.113.235.151";
        final int PORT = 8787;

        System.out.println("Client started.");
        try {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
        
        
            out.println("query\n" + type + "\n" + rank);
                // if (in.nextLine().equals("OK!")) break;
            if (in.hasNextLine())
                Name = in.nextLine();
            if (in.hasNextLine())
                Score = in.nextLine();
        }
        catch(IOException e){
            System.err.println("Error opening file.");
        }
    }

    public String getName() {
        return Name;
    }
    public String getScore() {
        return Score;
    }
}

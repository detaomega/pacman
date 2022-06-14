import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    String Name = "";
    String Score = "0"; 
    String Rk = ""; 
    public void updateRecord(String type, String name, int score) {
        final String HOST = "140.113.235.151";
        final int PORT = 8787;
        
        System.out.println("Client started.");

        try {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
        
          
            out.println("update\n" + type + "\n" + name + "\n" + score);
            System.out.println("update " + type + " " + name + " " + score);

            
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

    public void getUserScore(String type, String name) {
        final String HOST = "140.113.235.151";
        final int PORT = 8787;

        System.out.println("Client started.");
        try {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
        
        
            out.println("userScore\n" + type + "\n" + name);
                // if (in.nextLine().equals("OK!")) break;
            Name = name;
            Score = in.nextLine();
        }
        catch(IOException e){
            System.err.println("Error opening file.");
        }
    }

    public void getRank(String type, String name) {
        final String HOST = "140.113.235.151";
        final int PORT = 8787;

        System.out.println("Client started.");
        try {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
        
        
            out.println("getRank\n" + type + "\n" + name);
                // if (in.nextLine().equals("OK!")) break;
            Name = name;
            
            Rk = in.nextLine();
            System.out.print(Rk);
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

    public String getRank() {
        return Rk;
    }
}

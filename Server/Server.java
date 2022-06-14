import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;

/**
 *
 * @author 5442
 */
public class Server {
  public static void main(String[] args) throws IOException {
        final int PORT = 8787;
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Server started...");
        System.out.println("Wating for clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread() {
                public void run() {
                    try (
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        Scanner in = new Scanner(clientSocket.getInputStream());
                    ) {
                        while (in.hasNextLine()) {
                            String input = in.nextLine();
                            if (input.equals("query")) {
                                String type = in.nextLine();
                                String rank = in.nextLine();
                                String name = "", Score = "0";
                                String result = "";
                                System.out.println("Received radius from client: " + rank);
                                try {
                                    String target = new String("/net/cs/109/109550032/search.sh" + " " + type + " " +  rank);
                                    Runtime rt = Runtime.getRuntime();
                                    Process proc = rt.exec(target);
                                    proc.waitFor();
                                    StringBuffer output = new StringBuffer();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                                    String line = "";
                                    while ((line = reader.readLine())!= null) {
                                        result += (line + "\n");
                                    }
                                    System.out.println(result);
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                                if (result.equals(""))
                                    out.println("...\n0");
                                else
                                    out.println(result);
                            }
                            else {
                                String type = in.nextLine();
                                String name = in.nextLine();
                                String score = in.nextLine();
                                try {
                                    String target = new String("/net/cs/109/109550032/add.sh" + " " + type + " " +  name + " " + score);
                                    Runtime rt = Runtime.getRuntime();
                                    Process proc = rt.exec(target);
                                    proc.waitFor();
                                    StringBuffer output = new StringBuffer();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                                out.println("OK!");
                            }
                        }
                    } catch (IOException e) { }
                }
            };
            t.start();
        }
    }
}
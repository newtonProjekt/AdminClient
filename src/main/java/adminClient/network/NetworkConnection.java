package adminClient.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Johan Lindström (jolindse@hotmail.com) on 2016-03-10.
 */
public class NetworkConnection implements Runnable {

    private Socket server;
    private boolean disconnect = true;
    private PrintWriter out;
    private CommandHandler commandHandler;

    public NetworkConnection (String IPADRESS, int PORT, CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        try {
            server = new Socket(IPADRESS,PORT);
            out = new PrintWriter(server.getOutputStream(),true);
            disconnect = false;
        } catch (IOException e) {
            System.out.println("NETWORKCONNECTION; Constructor: Unable to connect to server");
        }
    }

    public void send(String jsonData){
        out.println(jsonData);
    }

    public void run() {
        while (!disconnect) {
            try {
                    Scanner sc = new Scanner(server.getInputStream());
                    while (sc.hasNextLine()) {
                        commandHandler.parse(sc.nextLine());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectServer(){
        disconnect = true;
    }
}

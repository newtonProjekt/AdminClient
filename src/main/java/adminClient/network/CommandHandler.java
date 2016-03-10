package adminClient.network;

import adminClient.beans.Message;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Jonas on 2016-03-07.
 */

public class CommandHandler {

    private Gson gson = new Gson();
    private NetworkConnection server;

    public CommandHandler() {
        gson = new Gson();
    }

    public void registerServer(NetworkConnection server){
        this.server = server;
    }

    public <T> void send(String cmd, T cmdData) {
        System.out.println("går du hit ofta?");
        Message currMessage = new Message(cmd, cmdData);

        System.out.println(currMessage);
        server.send(gson.toJson(currMessage));
    }

    public void parse(String jsonData) {
        Message currMessage = gson.fromJson(jsonData, Message.class);
        List<String> cmdData = currMessage.getCommandData();

        switch (currMessage.getCommand()) {

        }
    }
}


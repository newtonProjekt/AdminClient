package adminClient.beans;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan Lindström (jolindse@hotmail.com) on 2016-03-10.
 */
public class Message<T> {
    private String command;
    private List<String> commandData;

    public Message(String command){
        this.command = command;
        commandData = new ArrayList<>();
    }

    public Message(String command, T cmdData){
        this.command = command;
        commandData = new ArrayList<>();
        addCommandData(cmdData);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getCommandData() {
        return commandData;
    }

    public void setCommandData(List<String> commandData) {
        this.commandData = commandData;
    }

    /**
     * Converts object to JSON for inclusion in message object.
     *
     * @param currData Object
     */
    public void addCommandData(T currData){
        Gson gson = new Gson();
        String cmdData = gson.toJson(currData);
        commandData.add(cmdData);
    }

    @Override
    public String toString() {
        return "Message{" +
                "command='" + command + '\'' +
                ", commandData=" + commandData +
                '}';
    }
}

package adminClient.network;

import adminClient.AdminController;
import adminClient.beans.*;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Jonas on 2016-03-07.
 */

public class CommandHandler {

    private Gson gson = new Gson();
    private NetworkConnection server;
    private AdminController adminController;

    public CommandHandler(AdminController adminController) {
        this.adminController = adminController;
        gson = new Gson();
    }

    public void registerServer(NetworkConnection server){
        this.server = server;
    }

    public <T> void send(String cmd, T cmdData) {
        Message currMessage = new Message(cmd, cmdData);

        System.out.println(currMessage);
        server.send(gson.toJson(currMessage));
    }

    public void parse(String jsonData) {
        Message currMessage = gson.fromJson(jsonData, Message.class);
        List<String> cmdData = currMessage.getCommandData();

        switch (currMessage.getCommand()) {

            case "getalltests":
                SchoolTest[] tests = gson.fromJson(cmdData.get(0),SchoolTest[].class);

                adminController.clearTestList();
                for (int i = 0; i < tests.length; i++) {
                    adminController.addTest(tests[i]);
                }

                break;

            case "getallstudents":
                TableStudent[] students = gson.fromJson(cmdData.get(0),TableStudent[].class);

                adminController.clearStudentList();
                for (int i = 0; i < students.length; i++) {
                    adminController.addStudent(students[i]);
                }

                break;

            case "getallstudentclasses":
                NewtonClass[] newtonClasses = gson.fromJson(cmdData.get(0),NewtonClass[].class);

                adminController.clearClassList();
                for (int i = 0; i < newtonClasses.length; i++) {
                    adminController.addNewtonClass(newtonClasses[i]);
                }


        }
    }

    public void disconnectServer(){
        server.disconnectServer();
    }
}


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

    public void sendMessage(Message message){
        server.send(gson.toJson(message));
        System.out.println(message);
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

                adminController.removeClassListener();

                adminController.clearClassList();

                adminController.setClassListener();

                for (int i = 0; i < newtonClasses.length; i++) {
                    adminController.addNewtonClass(newtonClasses[i]);
                }
                break;

            case "getteststudents":
                long[] pNumberArray = gson.fromJson(cmdData.get(0),long[].class);

                for (int i = 0; i < pNumberArray.length; i++) {
                    adminController.addClassToInformationBox(pNumberArray[i]);
                }
                break;

            case "getteststocorrect":
                TestsToCorrect[] testsToCorrect = gson.fromJson(cmdData.get(0),TestsToCorrect[].class);

                for (int i = 0; i < testsToCorrect.length; i++) {
                    adminController.addTestToCorrect(testsToCorrect[i]);

                    System.out.println(testsToCorrect[i].toString());
                }
                break;
            case "gettesttocorrect":
                SubmittedTest submittedTest = gson.fromJson(cmdData.get(0),SubmittedTest.class);
                SchoolTest schoolTest = gson.fromJson(cmdData.get(1),SchoolTest.class);

                adminController.addSubmittedTestToCorrect(submittedTest);
                adminController.addSchoolTestToCorrect(schoolTest);

                break;

        }
    }

    public void disconnectServer(){
        server.disconnectServer();
    }
}


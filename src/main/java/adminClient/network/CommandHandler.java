package adminClient.network;

import adminClient.beans.Login;
import adminClient.beans.Student;
import com.google.gson.Gson;

/**
 * Created by Jonas on 2016-03-07.
 */

public class CommandHandler {
    Gson gson = new Gson();


    public void createLoginJson(Login login){
        System.out.println(gson.toJson(login));

    }

    public void createUserJson(Student user){
        System.out.println(gson.toJson(user));
    }
}

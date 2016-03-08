package adminClient.network;

import adminClient.beans.Login;
import adminClient.beans.User;
import adminClient.gui.LoginBox;
import com.google.gson.Gson;

/**
 * Created by Jonas on 2016-03-07.
 */

public class CommandHandler {
    Gson gson = new Gson();


    public void createLoginJson(Login login){
        System.out.println(gson.toJson(login));

    }

    public void createUserJson(User user){
        System.out.println(gson.toJson(user));
    }
}

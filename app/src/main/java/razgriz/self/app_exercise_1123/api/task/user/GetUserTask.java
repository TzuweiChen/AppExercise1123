package razgriz.self.app_exercise_1123.api.task.user;

import com.google.gson.Gson;

import razgriz.self.app_exercise_1123.api.retrofit.UsersRtf;
import razgriz.self.app_exercise_1123.api.task.DataTask;
import razgriz.self.app_exercise_1123.bean.User;

public class GetUserTask extends DataTask<User> {

    private final UsersRtf api;
    private final String login;

    protected GetUserTask(String login) {
        this.api = new UsersRtf();

        this.login = login;
    }

    @Override
    protected String load() throws Exception {
        return this.api.getUser(login);
    }

    @Override
    protected User parseData(String s) {
        try {
            return new Gson().fromJson(s, User.class);
        } catch (Exception e) {
            onException(e);
            return null;
        }
    }
}
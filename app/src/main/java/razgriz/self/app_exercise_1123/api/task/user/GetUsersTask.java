package razgriz.self.app_exercise_1123.api.task.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

import razgriz.self.app_exercise_1123.api.retrofit.UsersRtf;
import razgriz.self.app_exercise_1123.api.task.DataTask;
import razgriz.self.app_exercise_1123.bean.User;

public class GetUsersTask extends DataTask<List<User>> {

    private final UsersRtf api;
    private final int since;
    private final int limit;

    protected GetUsersTask(int since, int limit) {
        this.api = new UsersRtf();

        this.since = since;
        this.limit = limit;
    }

    @Override
    protected String load() throws Exception {
        return this.api.getUsers(since, limit);
    }

    @Override
    protected List<User> parseData(String s) {
        try {
            JSONArray dataArray = new JSONArray(s);
            return new Gson().fromJson(dataArray.toString(), new TypeToken<List<User>>() {
            }.getType());
        } catch (Exception e) {
            onException(e);
            return null;
        }
    }
}
package razgriz.self.app_exercise_1123.api.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class UsersRtf extends BaseRtf<UsersRtf.Service> {

    public UsersRtf() {
        super();
    }

    @Override
    protected Class<Service> getType() {
        return UsersRtf.Service.class;
    }

    public String getUsers(int since, int limit) throws Exception {
        return this.execute(this.api.getUsers(since, limit));
    }

    public interface Service {
        @GET("users")
        Call<String> getUsers(@Query("since") int since, @Query("per_page") int limit);
    }
}
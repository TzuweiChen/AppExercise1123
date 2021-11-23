package razgriz.self.app_exercise_1123.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("login")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("site_admin")
    private boolean isSiteAdmin;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public boolean isSiteAdmin() {
        return isSiteAdmin;
    }
}

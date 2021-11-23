package razgriz.self.app_exercise_1123.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("login")
    private String login;

    @SerializedName("id")
    private int id;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("site_admin")
    private boolean isSiteAdmin;

    @SerializedName("name")
    private String name;

    @SerializedName("bio")
    private String bio;

    @SerializedName("location")
    private String location;

    @SerializedName("blog")
    private String blog;

    public String getLogin() {
        return login;
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

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getBlog() {
        return blog;
    }
}

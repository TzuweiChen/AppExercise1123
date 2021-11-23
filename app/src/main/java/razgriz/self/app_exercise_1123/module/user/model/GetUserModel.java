package razgriz.self.app_exercise_1123.module.user.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Locale;

import razgriz.self.app_exercise_1123.api.basic.ApiException;
import razgriz.self.app_exercise_1123.api.task.DataLoader;
import razgriz.self.app_exercise_1123.api.task.user.GetUserTask;
import razgriz.self.app_exercise_1123.bean.User;

public class GetUserModel extends AndroidViewModel {

    private MutableLiveData<User> user;
    private MutableLiveData<String> error;
    private MutableLiveData<Boolean> progress;

    public GetUserModel(@NonNull Application application) {
        super(application);

        this.user = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.progress = new MutableLiveData<>();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progress;
    }

    public void doGetUser(@NonNull String login) {
        DataLoader.run(
                new GetUserTask(login) {

                    @Override
                    protected void onResult(User val) throws Exception {
                        user.postValue(val);
                    }

                    @Override
                    protected void onStart() {
                        progress.postValue(true);
                    }

                    @Override
                    protected void onFinish() {
                        progress.postValue(false);
                    }

                    @Override
                    protected void onApiException(ApiException e) {
                        super.onApiException(e);
                        error.postValue(String.format(Locale.getDefault(), "%s\n(%d)", e.getMessage(), e.getCode()));
                    }

                    @Override
                    protected void onException(Exception e) {
                        error.postValue(e.getMessage());
                    }
                });
    }
}

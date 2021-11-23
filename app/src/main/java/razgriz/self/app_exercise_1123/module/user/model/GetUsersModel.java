package razgriz.self.app_exercise_1123.module.user.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import java.util.List;
import java.util.Locale;

import razgriz.self.app_exercise_1123.api.basic.ApiException;
import razgriz.self.app_exercise_1123.api.task.DataLoader;
import razgriz.self.app_exercise_1123.api.task.user.GetUsersTask;
import razgriz.self.app_exercise_1123.bean.User;

public class GetUsersModel extends AndroidViewModel {

    private final int INIT_LOAD_SIZE = 20;
    private final int PAGE_SIZE = 20;

    private LiveData<PagedList<User>> users;
    private MutableLiveData<String> error;
    private MutableLiveData<Boolean> progress;

    public GetUsersModel(@NonNull Application application) {
        super(application);

        this.users = new LivePagedListBuilder<>(new DataSourceFactory(), new PagedList.Config.Builder()
                .setInitialLoadSizeHint(INIT_LOAD_SIZE)
                .setPageSize(PAGE_SIZE)
                .setMaxSize(Integer.MAX_VALUE)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(true)
                .build())
//                .setBoundaryCallback(new PagedList.BoundaryCallback() {
//                    @Override
//                    public void onZeroItemsLoaded() {
//                        super.onZeroItemsLoaded();
//                    }
//                })
                .build();

        this.error = new MutableLiveData<>();
        this.progress = new MutableLiveData<>();
    }

    public LiveData<PagedList<User>> getUser() {
        return users;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progress;
    }

    class DataSourceFactory extends DataSource.Factory<Integer, User> {

        public MutableLiveData<SaveUserDataSource> postLiveData;

        @NonNull
        @Override
        public DataSource<Integer, User> create() {
            SaveUserDataSource dataSource = new SaveUserDataSource(getApplication().getApplicationContext());

            postLiveData = new MutableLiveData<>();
            postLiveData.postValue(dataSource);

            return dataSource;
        }
    }

    class SaveUserDataSource extends PageKeyedDataSource<Integer, User> {

        private Context context;

        public SaveUserDataSource(Context context) {
            this.context = context;
        }

        @Override
        public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, User> callback) {
            DataLoader.run(
                    new GetUsersTask(0, params.requestedLoadSize) {
                        @Override
                        protected void onResult(List<User> val) throws Exception {
                            int since;
                            if (val.isEmpty()) {
                                since = -1;
                            } else {
                                since = val.get(val.size() - 1).getId();
                            }
                            callback.onResult(val, -1, since);
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

        @Override
        public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {

        }

        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {
            if (users.getValue().size() >= 100) {
                return;
            }

            DataLoader.run(
                    new GetUsersTask(params.key, params.requestedLoadSize) {
                        @Override
                        protected void onResult(List<User> val) throws Exception {
                            int since;
                            if (val.isEmpty()) {
                                since = -1;
                            } else {
                                since = val.get(val.size() - 1).getId();
                            }
                            callback.onResult(val, since);
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
}

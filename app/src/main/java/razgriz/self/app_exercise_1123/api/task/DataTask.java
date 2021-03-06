package razgriz.self.app_exercise_1123.api.task;

import com.orhanobut.logger.Logger;

import razgriz.self.app_exercise_1123.api.basic.ApiException;

public abstract class DataTask<T> {
    final void action() {
        try {
            this.onStart();
            String s = this.load();
            this.onResult(parseData(s));
        } catch (ApiException e) {
            Logger.e(e, e.getMessage());
            this.onApiException(e);
        } catch (Exception e) {
            Logger.e(e, e.getMessage());
            this.onException(e);
        } finally {
            this.onFinish();
        }
    }

    protected void onStart() {

    }

    protected void onFinish() {

    }

    protected abstract String load() throws Exception;

    protected abstract T parseData(String s) throws Exception;

    protected void onResult(T t) throws Exception {

    }

    protected void onApiException(ApiException e) {
    }

    protected void onException(Exception e) {
    }
}

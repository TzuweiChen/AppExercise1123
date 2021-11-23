package razgriz.self.app_exercise_1123.api.basic;

import androidx.annotation.Nullable;

public class ApiException extends Exception {
    private final int code;
    private final String message;
    private final String body;

    public ApiException(int code, String message, String body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public String getBody() {
        return body;
    }
}

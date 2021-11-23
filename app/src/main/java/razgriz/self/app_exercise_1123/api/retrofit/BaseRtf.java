package razgriz.self.app_exercise_1123.api.retrofit;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import razgriz.self.app_exercise_1123.BuildConfig;
import razgriz.self.app_exercise_1123.api.basic.ApiException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

abstract class BaseRtf<T> {
    protected T api;

    BaseRtf() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder().method(original.method(), original.body());
                    requestBuilder.addHeader("accept", "application/vnd.github.v3+json");
                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                })
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.GITHUB_API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();

        this.api = retrofit.create(this.getType());
    }

    String execute(Call<String> call) throws Exception {
        Response<String> response = call.execute();


        if (response.isSuccessful()) {
            return response.body();
        } else {
            String errorBody = response.errorBody() == null ? "" : response.errorBody().string();
            String message;
            String documentationUrl;
            JSONObject jsonObject = new JSONObject(errorBody);
            message = jsonObject.optString("message", "");
            documentationUrl = jsonObject.optString("documentation_url", "");

            throw new ApiException(response.code(), message, documentationUrl);
        }
    }

    protected abstract Class<T> getType();
}

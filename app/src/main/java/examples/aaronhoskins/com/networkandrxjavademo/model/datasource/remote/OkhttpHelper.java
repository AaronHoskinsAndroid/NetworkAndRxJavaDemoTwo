package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote;

import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import examples.aaronhoskins.com.networkandrxjavademo.model.events.RnadomUserResponseEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkhttpHelper {
    public static final String COMPLETE_URL = "https://randomuser.me/api/?results=5";

    public OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    public String executeSyncOkHttpRequest() throws IOException {
        Request request = new Request.Builder().url(COMPLETE_URL).build();

        Response response = getClient().newCall(request).execute();
        return response.body().string();
    }

    public void enqueAsyncOkHttpRequest() {
        Request request = new Request.Builder().url(COMPLETE_URL).build();

        getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("OKHTTP_HELP", "Error on request -->" , e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String json = response.body().string();
                Gson gson = new Gson();
                EventBus.getDefault().post(
                        new RnadomUserResponseEvent(gson.fromJson(json, RandomMeResponse.class)));
            }
        });
    }
}

package examples.aaronhoskins.com.networkandrxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.HttpUrlConnectionHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.Result;

public class MainActivity extends AppCompatActivity implements HttpUrlConnectionHelper.HttpCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            Thread networkThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    HttpUrlConnectionHelper.getRandomUsers(MainActivity.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            networkThread.start();
    }

    @Override
    public void onHttpUrlConnectionResponse(String json) {
        Log.d("JSON_RESPONSE", "onHttpUrlConnectionResponse: --> " + json);
        RandomMeResponse randomMeResponse = new Gson().fromJson(json, RandomMeResponse.class);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        for(Result result : randomMeResponse.getResults()) {
            Log.d("POJO_EMAIL", result.getEmail());
        }

    }
}

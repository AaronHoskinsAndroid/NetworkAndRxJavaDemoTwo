package examples.aaronhoskins.com.networkandrxjavademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.HttpUrlConnectionHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.OkhttpHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.RandomUserAsyncTask;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.RandomUserObserver;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.RetrofitHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.events.RnadomUserResponseEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.Result;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements HttpUrlConnectionHelper.HttpCallback {
    private RecyclerView rvUserList;
    private RandomUserRvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvUserList = findViewById(R.id.rvRandomUser);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void populateRecyclerView(List<Result> passedResults){
        if(adapter == null) {
            adapter = new RandomUserRvAdapter(passedResults);
            rvUserList.setLayoutManager(new LinearLayoutManager(this));
            rvUserList.setAdapter(adapter);
        } else {
            adapter.setResultList(passedResults);
        }
    }

    @Override
    public void onHttpUrlConnectionResponse(String json) {
        Log.d("JSON_RESPONSE", "onHttpUrlConnectionResponse: --> " + json);
        RandomMeResponse randomMeResponse = new Gson().fromJson(json, RandomMeResponse.class);
        final List<Result> responseList = randomMeResponse.getResults();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                populateRecyclerView(responseList);
            }
        });
//        for(Result result : randomMeResponse.getResults()) {
//            Log.d("POJO_EMAIL", result.getEmail());
//        }

    }

    public void onOkHttpClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMakeCallUsingOkHttpAsync:
                OkhttpHelper okhttpHelper = new OkhttpHelper();
                okhttpHelper.enqueAsyncOkHttpRequest();
                break;
            case R.id.btnMakeCallUsingOkHttpSync:
                RandomUserAsyncTask randomUserAsyncTask = new RandomUserAsyncTask();
                randomUserAsyncTask.execute();
                break;
        }
    }

    public void onHttpUrlClicked(View view) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRandomUserEvent(RnadomUserResponseEvent randomUserEvent) {
        if(randomUserEvent.getRandomMeResponse() != null) {
            List<Result> resultList = randomUserEvent.getRandomMeResponse().getResults();
            populateRecyclerView(resultList);
        }
    }

    public void onRetrofitClicked(View view) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        switch (view.getId()) {
            case R.id.btnMakeCallUsingRetrofitAsync:
                retrofitHelper.getRandomUsersAsync("5", "female");
                break;
            case R.id.btnMakeCallUsingRetrofitRxJava:
                retrofitHelper.getObservableService().getUsers("5", "male")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new RandomUserObserver());
                break;
        }

    }
}

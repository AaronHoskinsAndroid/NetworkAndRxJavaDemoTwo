package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import examples.aaronhoskins.com.networkandrxjavademo.model.events.RnadomUserResponseEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;

public class RandomUserAsyncTask extends AsyncTask<Void, String, RandomMeResponse> {
    @Override
    protected RandomMeResponse doInBackground(Void... voids) {
        OkhttpHelper okhttpHelper = new OkhttpHelper();
        String json;
        try {
           json = okhttpHelper.executeSyncOkHttpRequest();
           Gson gson = new Gson();
           RandomMeResponse randomMeResponse = gson.fromJson(json, RandomMeResponse.class);
           publishProgress("COMPLETED NETWORK CALL");
           return randomMeResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d("PROGRESS", values[0]);
    }

    @Override
    protected void onPostExecute(RandomMeResponse randomMeResponse) {
        super.onPostExecute(randomMeResponse);
        EventBus.getDefault().post(new RnadomUserResponseEvent(randomMeResponse));
    }
}

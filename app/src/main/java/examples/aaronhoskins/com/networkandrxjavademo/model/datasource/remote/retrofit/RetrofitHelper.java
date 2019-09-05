package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.OkhttpHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.services.ObservableRandomUserService;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.services.RandomUserCallService;
import examples.aaronhoskins.com.networkandrxjavademo.model.events.RnadomUserResponseEvent;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.Url_Constants.BASE_URL;

public class RetrofitHelper {
    private Retrofit getRetrofitInstance() {
        OkhttpHelper okhttpHelper = new OkhttpHelper();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //GSON CONVERTER FOR RETROFIT
                .addConverterFactory(GsonConverterFactory.create())
                //Add a custom OKHTTP client
                .client(okhttpHelper.getClient())
                .build();
    }

    private Retrofit getRetrofitInstanceRxJava() {
        OkhttpHelper okhttpHelper = new OkhttpHelper();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //GSON CONVERTER FOR RETROFIT
                .addConverterFactory(GsonConverterFactory.create())
                //Add a custom OKHTTP client
                .client(okhttpHelper.getClient())
                //Convert CALL object to Observables
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public RandomUserCallService getService(){
        return getRetrofitInstance().create(RandomUserCallService.class);
    }

    public ObservableRandomUserService getObservableService() {
        return  getRetrofitInstanceRxJava().create(ObservableRandomUserService.class);
    }

    public void getRandomUsersAsync(String numOfResults, String gender) {
        getService().getUsers(numOfResults, gender)
                .enqueue(new Callback<RandomMeResponse>() {
                    @Override
                    public void onResponse(Call<RandomMeResponse> call, Response<RandomMeResponse> response) {
                        EventBus.getDefault().post(new RnadomUserResponseEvent(response.body()));
                    }

                    @Override
                    public void onFailure(Call<RandomMeResponse> call, Throwable t) {
                        Log.e("Retrofit", "Error in retrofit", t.fillInStackTrace());
                    }
                });
    }
}

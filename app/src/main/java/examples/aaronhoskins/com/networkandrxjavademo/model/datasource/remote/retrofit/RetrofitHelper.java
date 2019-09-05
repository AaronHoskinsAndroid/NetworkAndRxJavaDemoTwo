package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit;

import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.OkhttpHelper;
import examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.services.RandomUserCallService;
import retrofit2.Retrofit;
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

    public RandomUserCallService getService(){
        return getRetrofitInstance().create(RandomUserCallService.class);
    }
}

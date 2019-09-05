package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.services;

import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.Url_Constants.PATH_API;
import static examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.Url_Constants.QUERY_GENDER;
import static examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.Url_Constants.QUERY_RESULT;

public interface ObservableRandomUserService {
    @GET(PATH_API)
    Observable<RandomMeResponse> getUsers(@Query(QUERY_RESULT) String resultCount, @Query(QUERY_GENDER) String gender);
}



package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.services;

import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.Url_Constants.PATH_API;
import static examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.Url_Constants.QUERY_GENDER;
import static examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote.retrofit.Url_Constants.QUERY_RESULT;

public interface RandomUserCallService {
    @GET(PATH_API)
    Call<RandomMeResponse> getUsers(@Query(QUERY_RESULT) String resultCount, @Query(QUERY_GENDER) String gender);
}

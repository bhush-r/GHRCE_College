package bhushan.org.GHRCEUSER.compile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JDoodleApi {
    @Headers("Content-Type: application/json")
    @POST("/v1/execute")
    Call<JDoodleResponse> executeCode(@Body JDoodleRequest request);
}


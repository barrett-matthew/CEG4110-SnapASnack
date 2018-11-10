package ceg4110.fa2018.group21.snapasnack.http;

import ceg4110.fa2018.group21.snapasnack.model.RetrieveAllImagesResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SeeFoodAPI {

    String BASE_URL = "http://ec2-13-58-18-68.us-east-2.compute.amazonaws.com";

    @Multipart
    @POST("/images/")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);

    @GET("/images/")
    Call<RetrieveAllImagesResponse> retrieveAllImages();

    @GET("/images/")
    Call<RetrieveAllImagesResponse> retrieveAllImages(@Query("page") String page);
}

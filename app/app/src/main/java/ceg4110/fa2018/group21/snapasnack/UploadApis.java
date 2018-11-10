package ceg4110.fa2018.group21.snapasnack;

import ceg4110.fa2018.group21.snapasnack.model.RetrieveAllImagesResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UploadApis {
    @Multipart
    @POST("/images/")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);

    @GET("/images/")
    Call<RetrieveAllImagesResponse> retrieveAllImages(@Query("page") String page);
}

package ceg4110.fa2018.group21.snapasnack;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadApis {
    @Multipart
    @POST("/images/")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("images") RequestBody requestBody);
}

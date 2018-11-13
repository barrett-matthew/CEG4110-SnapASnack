package ceg4110.fa2018.group21.snapasnack.http;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.response.PostCommentToImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.PostImageToServerResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.RetrieveAllImageCommentsResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.RetrieveAllImagesResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.RetrieveSingleImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.RetrieveCommentInformationResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SeeFoodAPI {

    String BASE_URL = "http://ec2-13-58-18-68.us-east-2.compute.amazonaws.com";

    @Multipart
    @POST("/images/")
    Call<PostImageToServerResponse> uploadImage(@Part List<MultipartBody.Part> file);

    @Multipart
    @POST("/images/{imageId}/add_comment/")
    Call<PostCommentToImageResponse> addCommentToImageWithID(@Path(value="imageId", encoded=true) int imageId,
                                                             @Part MultipartBody.Part commentAsString);

    @GET("/images/")
    Call<RetrieveAllImagesResponse> retrieveAllImages();

    @GET("/images/")
    Call<RetrieveAllImagesResponse> retrieveAllImages(@Query("page") int page);

    @GET("/images/{imageId}/")
    Call<RetrieveSingleImageResponse> retrieveSingleImage(@Path(value="imageId", encoded=true) int imageId);

    @GET("/images/{imageId}/comments/")
    Call<RetrieveAllImageCommentsResponse> retrieveAllCommentsForImageId(@Path(value="imageId", encoded=true) int imageId);

    @GET("/comments/{commentId}/")
    Call<RetrieveCommentInformationResponse> retrieveCommentInformation(@Path(value="commentId", encoded=true) int commentId);

}

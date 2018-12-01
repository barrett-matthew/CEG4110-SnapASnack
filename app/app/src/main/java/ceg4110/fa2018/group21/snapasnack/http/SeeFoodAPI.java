package ceg4110.fa2018.group21.snapasnack.http;

import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.response.PostCommentToImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.PostImagesResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.FetchAllCommentsOnImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.FetchImagesByPageNumberResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.FetchSingleImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.FetchCommentInformationResponse;
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

    // Use these for returning an image list in either ascending or descending order
    // Default is descending
    String FETCH_DIR_ASC = "asc";
    String FETCH_DIR_DESC = "desc";

    // Parameters for ordering fetched images
    // Default is no parameter; orders by posted date
    String FETCH_ORDER_BY_COMMENTS = "comments";
    String FETCH_ORDER_BY_SCORE = "score";

    @Multipart
    @POST("/images/")
    Call<PostImagesResponse> postImages(@Part List<MultipartBody.Part> file);

    @Multipart
    @POST("/images/{imageId}/add_comment/")
    Call<PostCommentToImageResponse> postCommentToImage(@Path(value="imageId", encoded=true) int imageId,
                                                        @Part MultipartBody.Part commentAsString);

    @GET("/images/")
    Call<FetchImagesByPageNumberResponse> fetchAllImages();

    @GET("/images/")
    Call<FetchImagesByPageNumberResponse> fetchImagesByPageNumber(@Query("page") int page,
                                                                  @Query("sort") String sort,
                                                                  @Query("direction") String direction);

    @GET("/images/{imageId}/")
    Call<FetchSingleImageResponse> fetchSingleImage(@Path(value="imageId", encoded=true) int imageId);

    @GET("/images/{imageId}/comments/")
    Call<FetchAllCommentsOnImageResponse> fetchAllCommentsOnImage(@Path(value="imageId", encoded=true) int imageId);

    @GET("/comments/{commentId}/")
    Call<FetchCommentInformationResponse> fetchCommentInformation(@Path(value="commentId", encoded=true) int commentId);

}

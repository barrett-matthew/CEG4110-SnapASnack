package ceg4110.fa2018.group21.snapasnack.http;

import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ceg4110.fa2018.group21.snapasnack.http.callback.FetchAllCommentsOnImageCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.PostCommentToImageCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.PostImagesCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchImagesByPageNumberCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchCommentInformationCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.FetchSingleImageCallback;
import ceg4110.fa2018.group21.snapasnack.model.response.PostCommentToImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.PostImagesResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.FetchAllCommentsOnImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.FetchImagesByPageNumberResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.FetchCommentInformationResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.FetchSingleImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodComment;
import ceg4110.fa2018.group21.snapasnack.model.seefood.SeeFoodImage;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeeFoodHTTPHandler {

    private static SeeFoodHTTPHandler _instance;

    private SeeFoodHTTPHandler() { }

    public static SeeFoodHTTPHandler getInstance() {
        if (_instance == null) {
            _instance = new SeeFoodHTTPHandler();
        }
        return _instance;
    }

    // Permissions: Internet, External Storage Read/Write
    public static void postImages(ArrayList<String> filePaths, @Nullable final PostImagesCallback callbacks) {
        ArrayList<MultipartBody.Part> parts = new ArrayList<>();
        for(String thisPath : filePaths) {
            File file = new File(thisPath);
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), fileRequestBody);
            parts.add(image);
        }

        Call call = getTransactionHandler().postImages(parts);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                PostImagesResponse result = (PostImagesResponse) response.body();
                if(response.code() == 200) {
                    // Image added successfully
                    List<SeeFoodImage> images = result.getImages();

                    if(callbacks != null) {
                        callbacks.onSuccess(images);
                    }
                }
                else if(response.code() == 400) {
                    // Invalid input
                    if(callbacks != null) {
                        callbacks.onError(result.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(callbacks != null) {
                    callbacks.onFailure(t);
                }
            }
        });
    }

    public static void postCommentToImage(int imageId, String comment, @Nullable final PostCommentToImageCallback callbacks) {
        MultipartBody.Part commentPart = MultipartBody.Part.createFormData("text", comment);
        Call call = getTransactionHandler().postCommentToImage(imageId, commentPart);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                PostCommentToImageResponse result = (PostCommentToImageResponse) response.body();
                if(response.code() == 200) {
                    // Comment added successfully
                    SeeFoodComment comment = result.getComment();

                    if(callbacks != null) {
                        callbacks.onSuccess(comment);
                    }
                }
                else {
                    // Error occurred
                    if(callbacks != null) {
                        String errorMessage = result.getMessage();
                        callbacks.onError(errorMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(callbacks != null) {
                    callbacks.onFailure(t);
                }
            }
        });
    }

    public static void fetchSingleImage(int imageId, @Nullable final FetchSingleImageCallback callbacks) {
        Call call = getTransactionHandler().fetchSingleImage(imageId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Successful operation
                    FetchSingleImageResponse result = (FetchSingleImageResponse) response.body();
                    SeeFoodImage image = result.getImage();

                    if(callbacks != null) {
                        callbacks.onSuccess(image);
                    }
                }
                else if(response.code() == 404) {
                    // Image not found
                    if(callbacks != null) {
                        callbacks.onError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(callbacks != null) {
                    callbacks.onFailure(t);
                }
            }
        });
    }

    public static void fetchImagesDefaultFirstPage(@Nullable final FetchImagesByPageNumberCallback callbacks) {
        // Default to page 1
        fetchImagesByPageNumber(1, null, null, callbacks);
    }

    // Use SeeFoodAPI.FETCH_* string constants for the orderBy and orderDirection parameters
    public static void fetchImagesByPageNumber(int pageNumber, String orderBy, String orderDirection,
                                               @Nullable final FetchImagesByPageNumberCallback callbacks) {
        Call call = getTransactionHandler().fetchImagesByPageNumber(pageNumber, orderBy, orderDirection);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Image added successfully
                    // Isolate the images
                    FetchImagesByPageNumberResponse result = (FetchImagesByPageNumberResponse) response.body();
                    List<SeeFoodImage> images = result.getImages();
                    int pageNumber = result.getPage();
                    int numImagesTotal = result.getTotal();
                    int imagesPerPage = result.getPerPage();
                    boolean hasPrev = result.isHasPrevious();
                    boolean hasNext = result.isHasNext();

                    if(callbacks != null) {
                        callbacks.onSuccess(images, pageNumber, numImagesTotal, imagesPerPage, hasPrev, hasNext);
                    }
                }
                else if(response.code() == 400) {
                    // Invalid input
                    if(callbacks != null) {
                        callbacks.onError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(callbacks != null) {
                    callbacks.onFailure(t);
                }
            }
        });
    }

    public static void fetchAllCommentsOnImage(int imageId, @Nullable final FetchAllCommentsOnImageCallback callbacks) {
        Call call = getTransactionHandler().fetchAllCommentsOnImage(imageId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Comments retrieved successfully
                    // Extract the comments
                    FetchAllCommentsOnImageResponse result = (FetchAllCommentsOnImageResponse) response.body();
                    List<SeeFoodComment> comments = result.getComments();

                    if(callbacks != null) {
                        callbacks.onSuccess(comments);
                    }
                }
                else if(response.code() == 400) {
                    // Invalid input
                    if(callbacks != null) {
                        callbacks.onError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(callbacks != null) {
                    callbacks.onFailure(t);
                }
            }
        });
    }

    public static void fetchCommentInformation(int commentId, final FetchCommentInformationCallback callbacks) {
        Call call = getTransactionHandler().fetchCommentInformation(commentId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Comment info retrieved
                    // Extract the comment object
                    FetchCommentInformationResponse result = (FetchCommentInformationResponse) response.body();
                    SeeFoodComment comment = result.getComment();

                    if(callbacks != null) {
                        callbacks.onSuccess(comment);
                    }
                }
                else if(response.code() == 400) {
                    // Invalid input
                    if(callbacks !=null) {
                        callbacks.onError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(callbacks != null) {
                    callbacks.onFailure(t);
                }
            }
        });
    }

    private static SeeFoodAPI getTransactionHandler() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SeeFoodAPI.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SeeFoodAPI.class);
    }

}

package ceg4110.fa2018.group21.snapasnack.http;

import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ceg4110.fa2018.group21.snapasnack.http.callback.PostCommentToImageCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.PostImageToServerCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.RetrieveAllImageCommentsCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.RetrieveAllImagesCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.RetrieveCommentInformationCallback;
import ceg4110.fa2018.group21.snapasnack.http.callback.RetrieveSingleImageCallback;
import ceg4110.fa2018.group21.snapasnack.model.response.PostCommentToImageResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.PostImageToServerResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.RetrieveAllImageCommentsResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.RetrieveAllImagesResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.RetrieveCommentInformationResponse;
import ceg4110.fa2018.group21.snapasnack.model.response.RetrieveSingleImageResponse;
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
    public static void uploadImageListFromPath(ArrayList<String> filePaths, final PostImageToServerCallback callbacks) {
        ArrayList<MultipartBody.Part> parts = new ArrayList<>();
        for(String thisPath : filePaths) {
            File file = new File(thisPath);
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), fileRequestBody);
            parts.add(image);
        }

        // TODO : Fix this...we only submit the first selected image
        Call call = getTransactionHandler().uploadImage(parts.get(0));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                PostImageToServerResponse result = (PostImageToServerResponse) response.body();

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

    public static void addCommentToImageById(int imageId, String comment, @Nullable final PostCommentToImageCallback callbacks) {
        MultipartBody.Part commentPart = MultipartBody.Part.createFormData("text", comment);
        Call call = getTransactionHandler().addCommentToImageWithID(imageId, commentPart);

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

    public static void retrieveSingleImage(int imageId, @Nullable final RetrieveSingleImageCallback callbacks) {
        Call call = getTransactionHandler().retrieveSingleImage(1);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Successful operation
                    RetrieveSingleImageResponse result = (RetrieveSingleImageResponse) response.body();
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

    public static void retrieveAllImages(@Nullable final RetrieveAllImagesCallback callbacks) {
        // Default to page 1
        retrieveAllImages(1, callbacks);
    }

    public static void retrieveAllImages(int pageNumber, @Nullable final RetrieveAllImagesCallback callbacks) {
        Call call = getTransactionHandler().retrieveAllImages(pageNumber);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Image added successfully
                    // Isolate the images
                    RetrieveAllImagesResponse result = (RetrieveAllImagesResponse) response.body();
                    List<SeeFoodImage> images = result.getImages();

                    if(callbacks != null) {
                        callbacks.onSuccess(images);
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

    public static void retrieveAllCommentsForImageById(int imageId, @Nullable final RetrieveAllImageCommentsCallback callbacks) {
        Call call = getTransactionHandler().retrieveAllCommentsForImageId(imageId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Comments retrieved successfully
                    // Extract the comments
                    RetrieveAllImageCommentsResponse result = (RetrieveAllImageCommentsResponse) response.body();
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

    public static void retrieveCommentInformation(int commentId, final RetrieveCommentInformationCallback callbacks) {
        Call call = getTransactionHandler().retrieveCommentInformation(commentId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Comment info retrieved
                    // Extract the comment object
                    RetrieveCommentInformationResponse result = (RetrieveCommentInformationResponse) response.body();
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

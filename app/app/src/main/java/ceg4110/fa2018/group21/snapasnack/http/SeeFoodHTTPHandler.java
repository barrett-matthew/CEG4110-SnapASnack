package ceg4110.fa2018.group21.snapasnack.http;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    // TODO : Return results
    public static void uploadImageListFromPath(ArrayList<String> filePaths) {
        for(String thisPath : filePaths) {
            File file = new File(thisPath);
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), fileRequestBody);
            Call call = getTransactionHandler().uploadImage(image);

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, retrofit2.Response response) {
                    // TODO: Do something with the response
                    if(response.code() == 200) {
                        // Image added successfully
                    }
                    else if(response.code() == 400) {
                        // Invalid input
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    // TODO: Do something if we cannot submit the image
                }
            });
        }
    }

    public static void addCommentToImageById(int imageId, String comment) {
        MultipartBody.Part commentPart = MultipartBody.Part.createFormData("text", comment);
        Call call = getTransactionHandler().addCommentToImageWithID(imageId, commentPart);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                // TODO: Do something with the response
                if(response.code() == 200) {
                    // Image added successfully
                }
                else if(response.code() == 404) {
                    // Image not found
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // TODO: Do something if we cannot submit the image
            }
        });
    }

    // TODO : Finish and test this once the response object is fixed (11.10)
    public static void retrieveSingleImage(int imageId) {
        Call call = getTransactionHandler().retrieveSingleImage(1);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Successful operation
                    RetrieveSingleImageResponse result = (RetrieveSingleImageResponse) response.body();
                    SeeFoodImage image = result.getImage();
                }
                else if(response.code() == 404) {
                    // Image not found
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // TODO: Do something if we cannot retrieve the image
            }
        });
    }

    // TODO: Return results
    public static void retrieveAllImages() {
        Call call = getTransactionHandler().retrieveAllImages();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                if(response.code() == 200) {
                    // Image added successfully
                    System.out.println(response.code());

                    // Isolate the images
                    RetrieveAllImagesResponse result = (RetrieveAllImagesResponse) response.body();
                    List<SeeFoodImage> images = result.getImages();
                }
                else if(response.code() == 400) {
                    // Invalid input
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // TODO: Do something if we cannot retrieve the images
            }
        });
    }

    // TODO : This has the same issue as the retrieveImages JSON...fix after JSON is resolved
    public static void retrieveAllCommentsForImageById(int imageId) {
        Call call = getTransactionHandler().retrieveAllCommentsForImageId(imageId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Comments retrieved successfully
                    // Extract the comments
                    RetrieveAllImageCommentsResponse result = (RetrieveAllImageCommentsResponse) response.body();
                    List<SeeFoodComment> images = result.getComments();
                }
                else if(response.code() == 400) {
                    // Invalid input
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // TODO: Do something if we cannot retrieve the comments
            }
        });
    }

    // TODO : Finish this after the JSON issue is resolved
    public static void retrieveCommentInformation(int commentId) {
        Call call = getTransactionHandler().retrieveCommentInformation(commentId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200) {
                    // Comment info retrieved
                    // Extract the comment object
                    RetrieveCommentInformationResponse result = (RetrieveCommentInformationResponse) response.body();
                    SeeFoodComment comment = result.getComment();
                }
                else if(response.code() == 400) {
                    // Invalid input
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                // TODO: Do something if we cannot retrieve the comments
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

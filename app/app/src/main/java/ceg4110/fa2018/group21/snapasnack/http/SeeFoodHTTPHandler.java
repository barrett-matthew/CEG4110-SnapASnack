package ceg4110.fa2018.group21.snapasnack.http;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ceg4110.fa2018.group21.snapasnack.model.RetrieveAllImagesResponse;
import ceg4110.fa2018.group21.snapasnack.model.SeeFoodImage;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
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
                // TODO: Do something if we cannot submit the image
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

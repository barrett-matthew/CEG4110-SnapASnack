package ceg4110.fa2018.group21.snapasnack;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageSubmissionHelper {

    private static RequestQueue _requestQueue;
    private static StringRequest _stringRequest;
    private static String _remoteAddr = "http://ec2-13-58-18-68.us-east-2.compute.amazonaws.com";
    private static ImageSubmissionHelper _instance;

    private ImageSubmissionHelper() {
        _requestQueue = null;
        _stringRequest = null;
    }

    public static ImageSubmissionHelper getInstance() {
        if (_instance == null) {
            _instance = new ImageSubmissionHelper();
        }
        return _instance;
    }

    public static RequestQueue getRequestQueue() {
        return _requestQueue;
    }

    // Returns reponse as a String
    public static void uploadImageListFromPath(ArrayList<String> filePaths, final Context context) {
        // TODO: Return results?

        for(String thisPath : filePaths) {
            File file = new File(thisPath);
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), fileRequestBody);
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
            Call call = getTransactionHandler().uploadImage(part, description);

            // NOTE: THIS REQUIRES INTERNET AND EXTERNAL READ/WRITE STORAGE PERMISSIONS
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, retrofit2.Response response) {
                    // TODO: Do something with the response
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    // TODO: Do something if we cannot submit the image
                }
            });
        }

        // TODO: The return statement here is called before the response gets back to us...fix?
        return;
    }

    private static UploadApis getTransactionHandler() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(_remoteAddr)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(UploadApis.class);
    }

}

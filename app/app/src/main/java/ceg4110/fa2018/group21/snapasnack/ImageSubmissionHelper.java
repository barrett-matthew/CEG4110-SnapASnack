package ceg4110.fa2018.group21.snapasnack;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ImageSubmissionHelper {

    private static RequestQueue _requestQueue;
    private static StringRequest _stringRequest;
    private static ImageSubmissionHelper _instance;
    private static String remoteAddr = "http://ec2-13-58-18-68.us-east-2.compute.amazonaws.com/images/";
    private static Context _context;

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
    public static String submitStringRequest(String string, final Context context) {
        String retVal = "";
        _requestQueue = Volley.newRequestQueue(context);
        _stringRequest = new StringRequest(Request.Method.GET, remoteAddr,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String responseee = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        _requestQueue.add(_stringRequest);
        return retVal;
    }

}

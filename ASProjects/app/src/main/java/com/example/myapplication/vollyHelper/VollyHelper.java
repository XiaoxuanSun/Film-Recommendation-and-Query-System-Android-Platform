package com.example.myapplication.vollyHelper;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.function.Consumer;
import java.util.function.Function;

public class VollyHelper {
    private static JsonArrayRequest request;
    private static JsonObjectRequest request2;

    public static void ResponseRes(String Url, Context context,Function<JSONArray, Boolean> funs) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        request = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    funs.apply(response);
                }catch (Exception e){
                    Log.d("Tag",e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Tag",error.getMessage());
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        mQueue.add(request);

    }

    public static void ResponseRes2(String Url, Context context,Function<JSONObject, Boolean> funs) {
        RequestQueue mQueue = Volley.newRequestQueue(context);

        request2 = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request2.setRetryPolicy(policy);

        mQueue.add(request2);
    }

    public static void ResponseRes(String Url, Context context,Consumer<JSONArray> funs){
        RequestQueue mQueue = Volley.newRequestQueue(context);
        request = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    funs.accept(response);
                }catch (Exception e){
                    Log.d("Tag",e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Tag",error.getMessage());
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        mQueue.add(request);
    }
}

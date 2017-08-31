package com.example.prashanttanksali.csc750_project1;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Prashant Tanksali on 29-08-2017.
 */

public class SingletonRequestQueue {
    private static SingletonRequestQueue rqInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private SingletonRequestQueue(Context context){
        SingletonRequestQueue.context = context;
        requestQueue = getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized SingletonRequestQueue getInstance(Context context){

            rqInstance = new SingletonRequestQueue(context);

        return rqInstance;
    }
    public void addToRequestQueue(Request request){
        requestQueue.add(request);
        System.out.print("HUU");
    }
}

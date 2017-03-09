package com.alo.alomobile.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.alo.alomobile.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 02/03/2017
 */

public class ProxyConnection {
    private static String TAG = ProxyConnection.class.getSimpleName();
    private ProgressDialog pDialog;
    private IStatusPostConnection statusConnection;
    private IStatusGetConnection statusGetConnection;
    private IStatusGetStringConnection stringConnection;
    private Context context;


    public ProxyConnection(Context context, IStatusPostConnection statusConnection){
        this.context = context;
        this.statusConnection = statusConnection;
    }

    public ProxyConnection(Context context, IStatusGetConnection statusGetConnection){
        this.context = context;
        this.statusGetConnection = statusGetConnection;
    }

    public ProxyConnection(Context context, IStatusGetStringConnection stringConnection){
        this.context = context;
        this.stringConnection = stringConnection;
    }



    public void postConnection(String url, HashMap<String, String> params, String msgDialog){

        pDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setIndeterminate(true);
        pDialog.setTitle(msgDialog);
        pDialog.setMessage("Aguarde...");
        pDialog.setCancelable(false);
        pDialog.show();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hidepDialog();
                        Log.i(TAG, "Encaminhando response para activty:"+
                                 response.toString());
                        statusConnection.notifySuccess(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoginFailed();
                        statusConnection.notifyError(error);

                    }
                }
        ) ;
        req.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Application.getInstance().addToRequestQueue(req);

    }


    public void getJSONOBJECTConnection(String url, String msgDialog){

        pDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setIndeterminate(true);
        pDialog.setTitle(msgDialog);
        pDialog.setMessage("Aguarde...");
        pDialog.setCancelable(false);
        pDialog.show();

        JsonObjectRequest req = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hidepDialog();
                        Log.i(TAG, "Encaminhando response para activty:"+
                                response.toString());
                        statusConnection.notifySuccess(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoginFailed();
                        statusConnection.notifyError(error);

                    }
                }
        ) ;
        req.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Application.getInstance().addToRequestQueue(req);

    }



    public void getJsonArrayConnection(String url, String msgDialog){

        pDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setIndeterminate(true);
        pDialog.setTitle(msgDialog);
        pDialog.setMessage("Aguarde...");
        pDialog.setCancelable(false);
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hidepDialog();
                        Log.i(TAG, "Encaminhando response para activty:"+
                                response.toString());
                        statusGetConnection.notifySuccess(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoginFailed();
                        statusGetConnection.notifyError(error);

                    }
                }
        ) ;
        req.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Application.getInstance().addToRequestQueue(req);

    }


    public void getStringRequest(String url, String msgDialog){
        pDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setIndeterminate(true);
        pDialog.setTitle(msgDialog);
        pDialog.setMessage("Aguarde...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest req = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        hidepDialog();
                        Log.i("onResponse", response);
                        stringConnection.notifySuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoginFailed();
                        Log.e("Volley error", error.getMessage());
                        stringConnection.notifyError(error);
                    }
                }
        );

        req.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Application.getInstance().addToRequestQueue(req);


    }



    private void onLoginFailed() {
        Log.i(TAG, "onLoginFailed()");
        hidepDialog();
    }

    private void hidepDialog(){
        if(pDialog != null){
            pDialog.dismiss();
            pDialog = null;
        }
    }

}



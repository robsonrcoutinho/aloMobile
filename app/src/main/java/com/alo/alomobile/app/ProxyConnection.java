package com.alo.alomobile.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.alo.alomobile.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 02/03/2017
 */

public class ProxyConnection {
    private static String TAG = ProxyConnection.class.getSimpleName();
    private ProgressDialog pDialog;
    private IStatusRespostaConnection statusConnection;
    private Context context;

    public ProxyConnection(Context context, IStatusRespostaConnection statusConnection){
        this.context = context;
        this.statusConnection = statusConnection;

    }

    public void postConnection(String url, HashMap<String, String> params, String msgDialog){

        pDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setIndeterminate(true);
        pDialog.setMessage(msgDialog);
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



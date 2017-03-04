package com.alo.alomobile.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.alo.alomobile.R;
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

    public ProxyConnection(Context context){
        this.context = context;

        try {
            statusConnection = (IStatusRespostaConnection) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " IStatusRespostaConnection");
        }

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
                        Log.i(TAG, "Resposta cadastro usuario: " + response.toString());
                        statusConnection.statusConnectionPost(true, response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoginFailed();
                        statusConnection.statusConnectionPost(false, null);
                    }
                }
        ) ;

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



package com.alo.alomobile.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alo.alomobile.R;
import com.alo.alomobile.app.Application;
import com.alo.alomobile.app.Consts;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 26/02/2017
 */

public class RecoveryActivity extends AppCompatActivity{
    private static String TAG = RecoveryActivity.class.getSimpleName();
    private EditText etEmail;
    private Button btnEnviar;
    private TextView tvLogin;
    private TextView tvRegistrar;
    private ProgressDialog pDialog;
    private HashMap<String, String> params;

    @Override
    public void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.recovery_senha_layout);

        etEmail = (EditText) findViewById(R.id.etEmailRec);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvRegistrar =(TextView) findViewById(R.id.tvRegistrarRec);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrarActivity.class);
                startActivity(intent);
            }
        });


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverySenha();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, ".onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, ".onStop()");
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    public void recoverySenha(){
        Log.i(TAG, ".recoverySenha()");

        if (!validate()) {
            return;
        }
        btnEnviar.setEnabled(false);


        pDialog = new ProgressDialog(RecoveryActivity.this,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Solicitando...");
        pDialog.setCancelable(false);
        pDialog.show();

        params = new HashMap<String,String>();
        params.put("email", etEmail.getText().toString());


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                Consts.REQUEST_RESET_PASSWORD,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Status recovery senha:" + response.toString());
                        hidePDialog();
                        try{
                            String status = response.getString("status");
                            Log.i("status", String.valueOf(status));

                            if(status.equals("sucesso")){
                                Intent intent = new Intent(RecoveryActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                onLoginFailed();
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                        };

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoginFailed();
                    }
                }
        ) ;

        Application.getInstance().addToRequestQueue(req);

    }


    public boolean validate() {
        boolean valid = true;

        String email = etEmail.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Digite um endereço de e-mail válido");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        return valid;
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Erro: Verifique se email está correto!", Toast.LENGTH_LONG).show();
        hidePDialog();
        btnEnviar.setEnabled(true);
    }
}

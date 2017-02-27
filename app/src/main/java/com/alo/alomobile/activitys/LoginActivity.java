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

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 23/02/2017
 */

public class LoginActivity extends AppCompatActivity {
    private static String TAG = LoginActivity.class.getSimpleName();
    private EditText etEmail;
    private EditText etPassword;
    private HashMap<String, String> params;
    private ProgressDialog pDialog;
    private TextView registrar;
    private TextView tvRecSenha;
    private Button btnLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.login_layout);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        registrar = (TextView) findViewById(R.id.tvRegistrar);
        tvRecSenha = (TextView) findViewById(R.id.tvRecSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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

    public void login(){
        Log.i(TAG, ".login()");

        if (!validate()) {
            return;
        }
        btnLogin.setEnabled(false);

        pDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Autenticando...");
        pDialog.setCancelable(false);
        pDialog.show();

        params = new HashMap<String,String>();
        params.put("email", etEmail.getText().toString());
        params.put("password", etPassword.getText().toString());

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                Consts.REQUEST_LOGIN,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Token Response: " + response.toString());
                        hidePDialog();
                     try{
                        String user_id = response.getString("token");
                        getSharedPreferences("alo_prefs", getApplicationContext().MODE_PRIVATE).edit().remove("alo_prefs").apply();
                        getSharedPreferences("alo_prefs", getApplicationContext().MODE_PRIVATE).edit()
                                .putString("token", user_id).apply();

                        Log.i("token", String.valueOf(user_id));

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

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
        String password = etPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Digite um endereço de e-mail válido");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty()) {
            etPassword.setError("Campo senha não pode ser vazio!");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Dados de acesso incorretos!", Toast.LENGTH_LONG).show();
        hidePDialog();
        btnLogin.setEnabled(true);
    }
}

package com.alo.alomobile.activitys;

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
import com.alo.alomobile.app.Consts;
import com.alo.alomobile.app.IStatusPostConnection;
import com.alo.alomobile.app.ProxyConnection;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

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
    private TextView registrar;
    private TextView tvRecSenha;
    private Button btnLogin;
    private IStatusPostConnection mResultCallback = null;
    private ProxyConnection pc;

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


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrarActivity.class);
                startActivity(intent);
            }
        });

        tvRecSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecoveryActivity.class);
                startActivity(intent);
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


    public void login(){
        Log.i(TAG, ".login()");

        if (!validate()) {
            return;
        }
        btnLogin.setEnabled(false);

        params = new HashMap<String,String>();
        params.put("email", etEmail.getText().toString());
        params.put("password", etPassword.getText().toString());

        iniciaVolleyCallback();
        pc = new ProxyConnection(this, mResultCallback);
        pc.postConnection(Consts.REQUEST_LOGIN, params,"Autenticando");

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

    void iniciaVolleyCallback(){
        mResultCallback = new IStatusPostConnection() {
            @Override
            public void notifySuccess(JSONObject response) {
                try{
                    String user_token = response.getString("token");
                    getSharedPreferences("alo_prefs", getApplicationContext().MODE_PRIVATE).edit().remove("alo_prefs").apply();
                    getSharedPreferences("alo_prefs", getApplicationContext().MODE_PRIVATE).edit()
                            .putString("token", user_token).apply();

                    Log.i("token", String.valueOf(user_token));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);

                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Dados de acesso incorretos!", Toast.LENGTH_LONG).show();
                };
            }

            @Override
            public void notifyError(VolleyError error) {
                Toast.makeText(getBaseContext(), "Dados de acesso incorretos!", Toast.LENGTH_LONG).show();
            }
        };
    }
}

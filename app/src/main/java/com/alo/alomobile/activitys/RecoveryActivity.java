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
import com.alo.alomobile.app.IStatusRespostaConnection;
import com.alo.alomobile.app.ProxyConnection;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;
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
    private HashMap<String, String> params;
    private ProxyConnection pc;
    private IStatusRespostaConnection mResultCallback = null;

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

      public void recoverySenha(){
        Log.i(TAG, ".recoverySenha()");

        if (!validate()) {
            return;
        }
        btnEnviar.setEnabled(false);


        params = new HashMap<String,String>();
        params.put("email", etEmail.getText().toString());

        iniciaVolleyCallback();
        pc = new ProxyConnection(this, mResultCallback);

        pc.postConnection(Consts.REQUEST_RESET_PASSWORD, params, "Solicitando");
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


    void iniciaVolleyCallback(){
        mResultCallback = new IStatusRespostaConnection() {
            @Override
            public void notifySuccess(JSONObject response) {
                Log.d(TAG, "Volley JSON post" + response);
                try {
                    String status = response.getString("status");
                    if(status.equals("sucesso")){
                        Toast.makeText(getBaseContext(), "Verifique link de redefinição de senha em seu email!",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RecoveryActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }else {
                        btnEnviar.setEnabled(true);
                        Toast.makeText(getBaseContext(), "Erro: Verifique se email está correto!", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    btnEnviar.setEnabled(true);
                    Toast.makeText(getBaseContext(), "Erro: Verifique se email está correto!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post error:" + error.toString());
                btnEnviar.setEnabled(true);
                Toast.makeText(getBaseContext(), "Erro: Verifique se email está correto!", Toast.LENGTH_LONG).show();
            }
        };
    }
    }


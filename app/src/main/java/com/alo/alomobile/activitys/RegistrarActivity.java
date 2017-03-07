package com.alo.alomobile.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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
 * @Since 26/02/2017
 */

public class RegistrarActivity extends AppCompatActivity{
    private static String TAG = RegistrarActivity.class.getSimpleName();
    private EditText etRazaoSocial;
    private EditText etNomeFantasia;
    private EditText etEndereco;
    private EditText etTelefone;
    private EditText etCidade;
    private EditText etUF;
    private EditText etCNPJ_CPF;
    private EditText etNomeUsuario;
    private EditText etEmailUsuario;
    private EditText etPasswordUsuario;
    //private EditText etRole;
    private TextView recSenha;
    private TextView login;
    private Button btnRegister;
    private HashMap<String, String> params;
    private ProxyConnection pc;
    private IStatusPostConnection mResultCallback = null;

    @Override
    public void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.registrar_layout);

        etRazaoSocial = (EditText) findViewById(R.id.etRazao_social);
        etNomeFantasia = (EditText) findViewById(R.id.etNome_fantasia);
        etEndereco = (EditText) findViewById(R.id.etEndereco);
        etTelefone= (EditText) findViewById(R.id.etTelefone);
        etCidade= (EditText) findViewById(R.id.etCidade);
        etUF= (EditText) findViewById(R.id.etUF);
        etCNPJ_CPF= (EditText) findViewById(R.id.etCnpj_cpf);
        etNomeUsuario= (EditText) findViewById(R.id.etNome_usuario);
        etEmailUsuario= (EditText) findViewById(R.id.etEmail_usuario);
        etPasswordUsuario= (EditText) findViewById(R.id.etPassword_usuario);

        recSenha = (TextView) findViewById(R.id.tvRecSenha);
        login = (TextView) findViewById(R.id.tvLoginRegister);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        recSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecoveryActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

    public void registrar(){
        Log.i(TAG,"registrar()");

        if(!validate()){
            return;
        }

        btnRegister.setEnabled(false);

        params = new HashMap<String,String>();
        params.put("email", etEmailUsuario.getText().toString());
        params.put("password", etPasswordUsuario.getText().toString());
        params.put("razao_social", etRazaoSocial.getText().toString());
        params.put("nome_fantasia", etNomeFantasia.getText().toString());
        params.put("rua", etEndereco.getText().toString());
        params.put("telefone", etTelefone.getText().toString());
        params.put("cidade", etCidade.getText().toString());
        params.put("uf", etUF.getText().toString());
        params.put("cnpj_cpf", etCNPJ_CPF.getText().toString());
        params.put("name", etNomeUsuario.getText().toString());
        btnRegister.setEnabled(true);

        iniciaVolleyCallback();
        pc = new ProxyConnection(this, mResultCallback);
        pc.postConnection(Consts.REQUEST_REGISTER,params,"Registrando");


    }

    public boolean validate() {
        Log.i(TAG, "validate()");
        boolean valid = true;

        String emailUsuario = etEmailUsuario.getText().toString();
        String passwordUsuario =  etPasswordUsuario.getText().toString();
        String razaoSocial = etRazaoSocial.getText().toString();
        String nomeFantasia = etNomeFantasia.getText().toString();
        String endereco = etEndereco.getText().toString();
        String telefone = etTelefone.getText().toString();
        String cidade = etCidade.getText().toString();
        String UF = etUF.getText().toString();
        String cnpjCpf = etCNPJ_CPF.getText().toString();
        String nomeUsuario = etNomeUsuario.getText().toString();


        if (emailUsuario.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailUsuario).matches()) {
            etEmailUsuario.setError("Digite um endereço de e-mail válido");
            valid = false;
        } else {
            etEmailUsuario.setError(null);
        }

        if (passwordUsuario.isEmpty()) {
            etPasswordUsuario.setError("Campo senha não pode ser vazio!");
            valid = false;
        } else {
            etPasswordUsuario.setError(null);
        }

        if (razaoSocial.isEmpty()) {
            etRazaoSocial.setError("Campo Razão Social não pode ser vazio!");
            valid = false;
        } else {
            etRazaoSocial.setError(null);
        }

        if (nomeFantasia.isEmpty()) {
            etNomeFantasia.setError("Campo Nome Fantasia não pode ser vazio!");
            valid = false;
        } else {
            etNomeFantasia.setError(null);
        }

        if (endereco.isEmpty()) {
            etEndereco.setError("Campo Endereço não pode ser vazio!");
            valid = false;
        } else {
            etEndereco.setError(null);
        }

        if (telefone.isEmpty() || !Patterns.PHONE.matcher(telefone).matches()) {
            etTelefone.setError("Campo Telefone não pode ser vazio!");
            valid = false;
        } else {
            etTelefone.setError(null);
        }

        if (cidade.isEmpty()) {
            etCidade.setError("Campo Cidade não pode ser vazio!");
            valid = false;
        } else {
            etCidade.setError(null);
        }

        if (UF.isEmpty()) {
            etUF.setError("Campo UF não pode ser vazio!");
            valid = false;
        } else {
            etUF.setError(null);
        }

        if (cnpjCpf.isEmpty()) {
            etCNPJ_CPF.setError("Campo CNPJ/CPF não pode ser vazio!");
            valid = false;
        } else {
            etCNPJ_CPF.setError(null);
        }

        if (nomeUsuario.isEmpty()) {
            etNomeUsuario.setError("Campo Nome Usuário não pode ser vazio!");
            valid = false;
        } else {
            etNomeUsuario.setError(null);
        }
        return valid;
    }

    void iniciaVolleyCallback(){
        mResultCallback = new IStatusPostConnection() {
            @Override
            public void notifySuccess(JSONObject response){

                try {
                    String resposta = response.getString("user");
                    if(resposta.equals("create_sucess")){
                        Toast.makeText(getBaseContext(), "Conta criada com sucesso. Faça login com email e senha cadastrados!",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistrarActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }else {
                        Toast.makeText(getBaseContext(), "Falha no resgitro! Tente novamente!",
                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Falha no resgitro! Tente novamente!",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        };

    }
}


package com.alo.alomobile.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.alo.alomobile.R;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 26/02/2017
 */

public class RegistrarActivity extends AppCompatActivity{
    //private EditText
    /*
    razao_social
    nome_fantasia
    role
    rua
    telefone
    cidade
    uf
    cnpj_cpf
    name
    email
    password
    */

    @Override
    public void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.registrar_layout);

    }
}

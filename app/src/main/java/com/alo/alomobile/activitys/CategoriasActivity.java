package com.alo.alomobile.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.alo.alomobile.R;
import com.alo.alomobile.adapters.CategoriaAdapter;
import com.alo.alomobile.app.Consts;
import com.alo.alomobile.app.IStatusGetConnection;
import com.alo.alomobile.app.ProxyConnection;
import com.alo.alomobile.model.Categoria;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Robson Coutinho
 * @version 1.0
 * @since  22/02/2017.
 */

public class CategoriasActivity extends AppCompatActivity {
    private static String TAG = CategoriasActivity.class.getSimpleName();
    private ListView listView;
    private CategoriaAdapter adapter;
    private List<Categoria> catList = new ArrayList<Categoria>();
    private IStatusGetConnection mResultCallback = null;
    private ProxyConnection pc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, ".onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolCategoria);

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Categorias");
            actionBar.show();
        }

        listView = (ListView) findViewById(R.id.listCategorias);
        adapter = new CategoriaAdapter(this, catList);
        listView.setAdapter(adapter);

        carregaCategorias();
    }

    public void carregaCategorias(){
        iniciaVolleyCallback();
        pc = new ProxyConnection(this, mResultCallback);
        pc.getJsonArrayConnection(Consts.GET_CATEGORIA, "Buscando Categorias...");

    }

    public void iniciaVolleyCallback(){
        mResultCallback = new IStatusGetConnection() {
            @Override
            public void notifySuccess(JSONArray response) {
                Log.i(TAG, response.toString());
                for(int i = 0 ; i< response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Categoria categoria = new Categoria();
                        categoria.setIdCategoria(obj.getString("id"));
                        categoria.setNomeCategoria(obj.getString("nome_categoria"));

                        catList.add(categoria);

                        Collections.sort(catList, categoria);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.i(TAG, error.toString());
                Toast.makeText(getBaseContext(), "Erro ao conectar com servidor! Tente novamente!",
                        Toast.LENGTH_LONG).show();
            }

        };

    }
}

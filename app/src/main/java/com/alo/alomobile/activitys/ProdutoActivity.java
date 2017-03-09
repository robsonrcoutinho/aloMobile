package com.alo.alomobile.activitys;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alo.alomobile.R;
import com.alo.alomobile.adapters.ProdutoAdapter;
import com.alo.alomobile.app.Consts;
import com.alo.alomobile.app.IStatusPostConnection;
import com.alo.alomobile.app.ProxyConnection;
import com.alo.alomobile.model.Item;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 09/03/2017
 */

public class ProdutoActivity extends AppCompatActivity {
    private static  String TAG = ProdutoActivity.class.getSimpleName();
    private ProxyConnection pc;
    private IStatusPostConnection mCallBack = null;
    private ArrayList<Item> items = new ArrayList<>();
    private ProdutoAdapter adapter;
    private ListView listView;
    private JSONArray jsonArrayItens;
   // String URL = "http://192.168.0.102/PHP_REST/resposta.php";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolProduto);

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
            actionBar.setTitle("Produtos");
            actionBar.show();
        }

        listView = (ListView) findViewById(R.id.list_produto);
        adapter = new ProdutoAdapter(this, items);
        listView.setAdapter(adapter);

        carregaProdutos();
   }


   public void carregaProdutos() {
        iniciaVolleyRequest();

        pc = new ProxyConnection(this, mCallBack);
        pc.getJSONOBJECTConnection(Consts.REQUEST_PRODUTOS,"Buscando Produtos");
       //pc.getJSONOBJECTConnection(URL,"Buscando Produtos");

   }

   public void iniciaVolleyRequest(){
        mCallBack = new IStatusPostConnection() {
            @Override
            public void notifySuccess(JSONObject response) {
                Log.i(TAG, response.toString());
                try {
                    String itensJSON = response.getString("produtos");
                    jsonArrayItens = new JSONArray(itensJSON);

                    for(int i = 0; i< jsonArrayItens.length(); i++){
                        JSONObject obj =  jsonArrayItens.getJSONObject(i);

                        Item item =  new Item();
                        item.setNome(obj.getString("nome_produto"));
                        item.setPreco(obj.getString("valor"));
                        String image = obj.getString("imagem");

                        Log.i("IMAGEM",Consts.ROOT + image);
                        item.setImagem(Consts.ROOT + image);

                        JSONObject obj2 = obj.getJSONObject("marca");
                        item.setMarca(obj2.getString("marca"));
                        items.add(item);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, e.toString());
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                    Log.i(TAG, "ERROR VOLLEY:"+ error.toString());
            }
        };
    }
}

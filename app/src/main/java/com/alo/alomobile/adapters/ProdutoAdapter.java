package com.alo.alomobile.adapters;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 09/03/2017
 */

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alo.alomobile.R;
import com.alo.alomobile.app.Application;
import com.alo.alomobile.model.Item;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ProdutoAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Item> produtoItems;
    ImageLoader imageLoader = Application.getInstance().getImageLoader();

    public ProdutoAdapter(Activity activity, List<Item> produtoItems) {
        this.activity = activity;
        this.produtoItems = produtoItems;
    }

    @Override
    public int getCount() {
        return produtoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return produtoItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.produto_list_row, null);

        if (imageLoader == null)
            imageLoader = Application.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail_prod);
        TextView nomeProduto = (TextView) convertView.findViewById(R.id.tv_nome_produto);
        TextView preco = (TextView) convertView.findViewById(R.id.tv_preco_produto);
        TextView marca = (TextView) convertView.findViewById(R.id.tv_marca_produto);

        Item m = produtoItems.get(position);

        // thumbnail image
        Log.i("LOG IMAGEM: ", m.getImagem().toString());
        thumbNail.setImageUrl("Preço: " +m.getImagem(), imageLoader);

        // nome produto
        nomeProduto.setText(m.getNome());

        // preco
        preco.setText(m.getPreco());

        // marca
        marca.setText(m.getMarca());




        return convertView;
    }

}
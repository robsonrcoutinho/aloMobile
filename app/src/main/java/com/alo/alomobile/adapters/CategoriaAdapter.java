package com.alo.alomobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alo.alomobile.R;
import com.alo.alomobile.activitys.MainActivity;
import com.alo.alomobile.models.Categoria;

import java.util.List;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 07/03/2017
 */

public class CategoriaAdapter extends BaseAdapter{

    private static final String TAG = CategoriaAdapter.class.getSimpleName();
    private Activity activity;
    private List<Categoria> categorias;
    private LayoutInflater inflater;


    public CategoriaAdapter(Activity activity, List<Categoria> categorias){
        this.activity = activity;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.categoria_list_row, null);
        final Context context = convertView.getContext();

        TextView nomeCategoria = (TextView) convertView.findViewById(R.id.idNomeCategoria);

        Categoria categoria = categorias.get(position);

        nomeCategoria.setText(categoria.getNomeCategoria());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.finish();
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

package com.alo.alomobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alo.alomobile.R;
import com.alo.alomobile.activitys.MensagemActivity;
import com.alo.alomobile.activitys.PedidoActivity;
import com.alo.alomobile.activitys.CategoriasActivity;
import com.alo.alomobile.activitys.PromocaoActivity;

/**
 * Created by rramo on 22/02/2017.
 */

public class RecicleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView itemNome;
    public ImageView ItemImage;
    Context context;


    public RecicleViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemNome = (TextView)itemView.findViewById(R.id.item_nome);
        ItemImage = (ImageView)itemView.findViewById(R.id.item_foto);
    }

    @Override
    public void onClick(View view) {
        context = view.getContext();
        final Intent intent;
        int position = getAdapterPosition();

        switch (position){
            case 0:
                intent = new Intent(context,CategoriasActivity.class);
                context.startActivity(intent);
                break;
            case 1:
                intent = new Intent(context,MensagemActivity.class);
                context.startActivity(intent);
                break;
            case 2:
                intent = new Intent(context,PedidoActivity.class);
                context.startActivity(intent);
                break;
            case 3:
                intent = new Intent(context,PromocaoActivity.class);
                context.startActivity(intent);
                break;
        }


    }
}


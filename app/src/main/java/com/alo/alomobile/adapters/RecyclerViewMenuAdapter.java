package com.alo.alomobile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alo.alomobile.R;
import com.alo.alomobile.model.ItemMenu;
import java.util.List;

/**
 * Classe RecyclerView usada para criação do Menu
 * @Author Robson Coutinho
 * @version 1.0
 * @since  22/02/2017.
 */

public class RecyclerViewMenuAdapter  extends RecyclerView.Adapter<RecicleViewHolder> {

private List<ItemMenu> itemList;
private Context context;

public RecyclerViewMenuAdapter(Context context, List<ItemMenu> itemList) {
        this.itemList = itemList;
        this.context = context;
        }

@Override
public RecicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_menu_list, null);
        RecicleViewHolder rcv = new RecicleViewHolder(layoutView);
        return rcv;
        }

@Override
public void onBindViewHolder(RecicleViewHolder holder, int position) {
        holder.itemNome.setText(itemList.get(position).getNome());
        holder.ItemImage.setImageResource(itemList.get(position).getFoto());
        }

@Override
public int getItemCount() {
        return this.itemList.size();
        }
}


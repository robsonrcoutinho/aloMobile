package com.alo.alomobile.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alo.alomobile.R;
import com.alo.alomobile.adapters.RecyclerViewMenuAdapter;
import com.alo.alomobile.model.ItemMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rramo on 23/02/2017.
 */

public class MenuFragment extends Fragment{
    private static String TAG = MenuFragment.class.getSimpleName();
    private GridLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView()");

        final View rootView = inflater.inflate(R.layout.layout_opcao_recycler_menu, container, false);
        List<ItemMenu> listaItens = getAllItemList();
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);

        RecyclerView rView = (RecyclerView) rootView.findViewById(R.id.recycler_view_menu);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(layoutManager);

        RecyclerViewMenuAdapter rcAdapter = new RecyclerViewMenuAdapter(getActivity().getApplication(), listaItens);
        rView.setAdapter(rcAdapter);
        return rootView;

    }

    private List<ItemMenu> getAllItemList(){
        Log.i(TAG,".getAllItemList()");

        List<ItemMenu> allItems = new ArrayList<>();
        allItems.add(new ItemMenu("Produtos", R.drawable.produto));
        allItems.add(new ItemMenu("Pedidos", R.drawable.pedidos));
        allItems.add(new ItemMenu("Mensagens", R.drawable.mensagens));
        allItems.add(new ItemMenu("Promoções", R.drawable.promocoes));
        return allItems;
    }


    }

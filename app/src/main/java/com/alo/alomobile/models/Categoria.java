package com.alo.alomobile.models;

import java.util.Comparator;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 07/03/2017
 */

public class Categoria implements Comparator<Categoria> {
    private String idCategoria;
    private String nomeCategoria;

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public String toString(){
        return getNomeCategoria();
    }

    @Override
    public int compare(Categoria o1, Categoria o2) {
        return o1.getNomeCategoria().compareTo(o2.getNomeCategoria().toString());

    }
}

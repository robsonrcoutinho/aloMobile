package com.alo.alomobile.model;

/**
 * Created by rramo on 22/02/2017.
 */

public class ItemMenu {
    private String nome;
    private int foto;

    public ItemMenu(String nome, int foto) {
        this.nome = nome;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int photo) {
        this.foto = foto;
    }
}

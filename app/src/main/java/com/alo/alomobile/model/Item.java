package com.alo.alomobile.model;

/**
 * @version 1.0
 * @Author Robson Coutinho
 * @Since 09/03/2017
 */

public class Item {
    private String nome;
    private String preco;
    private String imagem;
    private String marca;


    public Item(){}

    public Item(String nome, String preco, String imagem, String marca){
        this.nome = nome;
        this.preco = preco;
        this.imagem = imagem;
        this.marca = marca;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}

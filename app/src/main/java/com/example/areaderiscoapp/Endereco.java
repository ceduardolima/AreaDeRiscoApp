package com.example.areaderiscoapp;

import java.io.Serializable;

public class Endereco implements Serializable {
    private Long id;
    private String logradouro;
    private String bairro;

    public Endereco(Long id, String logradouro, String bairro) {
        this.id = id;
        this.logradouro = logradouro;
        this.bairro = bairro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String toString(){
        return this.getLogradouro() +
                ", Bairro: " + this.getBairro();
    }
}
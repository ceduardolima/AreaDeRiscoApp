package com.example.areaderiscoapp.TSV_java;


import java.io.Serializable;

public class Chamado implements Serializable {
    int _id;
    int ano;
    String mes;
    String processo_numero;
    String solicitação_descrição;
    String solicitação_bairro;
    String solicitação_localidade;
    String solicitação_endereço;

    Chamado(int _id, int ano, String mes, String processo_numero, String solicitação_descrição
            , String solicitação_bairro, String solicitação_localidade, String solicitação_endereço){
        this._id=_id;

        this.ano=ano;

        this.mes=mes;

        //o numero do processo, pode ser linkado com outras bases de dados
        this.processo_numero=processo_numero;

        //o motivo da chamada
        this.solicitação_descrição=solicitação_descrição;

        this.solicitação_bairro=solicitação_bairro;

        this.solicitação_localidade=solicitação_localidade;

        //endereço pode não ter o numero
        this.solicitação_endereço=solicitação_endereço;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getProcesso_numero() {
        return processo_numero;
    }

    public void setProcesso_numero(String processo_numero) {
        this.processo_numero = processo_numero;
    }

    public String getSolicitação_descrição() {
        return solicitação_descrição;
    }

    public void setSolicitação_descrição(String solicitação_descrição) {
        this.solicitação_descrição = solicitação_descrição;
    }

    public String getSolicitação_bairro() {
        return solicitação_bairro;
    }

    public void setSolicitação_bairro(String solicitação_bairro) {
        this.solicitação_bairro = solicitação_bairro;
    }

    public String getSolicitação_localidade() {
        return solicitação_localidade;
    }

    public void setSolicitação_localidade(String solicitação_localidade) {
        this.solicitação_localidade = solicitação_localidade;
    }

    public String getSolicitação_endereço() {
        return solicitação_endereço;
    }

    public void setSolicitação_endereço(String solicitação_endereço) {
        this.solicitação_endereço = solicitação_endereço;
    }
}

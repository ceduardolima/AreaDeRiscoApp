package com.example.areaderiscoapp.TSV_java;


public class Chamado {
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
        this.processo_numero=processo_numero;
        this.solicitação_descrição=solicitação_descrição;
        this.solicitação_bairro=solicitação_bairro;
        this.solicitação_localidade=solicitação_localidade;
        this.solicitação_endereço=solicitação_endereço;
    }
}

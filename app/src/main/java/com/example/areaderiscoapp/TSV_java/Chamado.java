package com.example.areaderiscoapp.TSV_java;


import java.io.Serializable;

public class Chamado implements Serializable {
    int _id;
    int ano;
    String mes;
    String processo_numero;
    String descricao;
    String bairro;
    String localidade;
    String endereco;
    String houseNumber;


    Chamado(int _id, int ano, String mes, String processo_numero, String descricao
            , String bairro, String localidade, String endereco){
        this._id=_id;

        this.ano=ano;

        this.mes=mes;

        //o numero do processo, pode ser linkado com outras bases de dados
        this.processo_numero=processo_numero;

        //o motivo da chamada
        this.descricao = descricao;

        this.bairro = bairro.toLowerCase();

        this.localidade = localidade;

        //endereço pode não ter o numero
        filterAddress(endereco);
    }

    private boolean isHouseNumberAvailable(String houseNumber) {
        boolean isAvailable = true;
        for(int i = 0; i < houseNumber.length(); i++) {
            if(!Character.isDigit(houseNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void filterAddress(String address) {
        // Faz a separação entre a rua e o numero da casa.
        // A separação pode ser por virgula ou 'Nº'
        if(address.contains(",")) {
            this.endereco = address.split(",")[0].toLowerCase();

            //Verifica se o numero é valido, pois algumas strings possuem numero concatenado com caracter
            if(isHouseNumberAvailable(address.split(",")[1]))
                this.houseNumber = address.split(",")[1];
            else this.houseNumber = null;

        } else {
            if(address.contains("Nº")) {
                this.endereco = address.split("Nº")[0].toLowerCase();

                if(isHouseNumberAvailable(address.split(",")[1]))
                    this.houseNumber = address.split(",")[1];
                else this.houseNumber = null;
            }
        }
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}

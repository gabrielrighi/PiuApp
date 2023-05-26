package com.proj.piu;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class RetornoModal {

    private int id;
    private String especie;
    private String nomeCientifico;
    private String nomeComum;
    private float confianca;
    private String dataHora;

    public RetornoModal(){};

    public RetornoModal(int id,
                        String especie,
                        String nomeCientifico,
                        String nomeComum,
                        float confianca,
                        String dataHora){
        this.id = id;
        this.especie = especie;
        this.nomeCientifico = nomeCientifico;
        this.nomeComum = nomeComum;
        this.confianca = confianca;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public void setNomeComum(String nomeComum) {
        this.nomeComum = nomeComum;
    }

    public float getConfianca() {
        return confianca;
    }

    public String getConfiancaFormat(){
        int roundedConfianca = Math.round(confianca*100);
        String strConfianca = roundedConfianca + "%";

        return strConfianca;
    }

    public void setConfianca(float confianca) {
        this.confianca = confianca;
    }

    public String getDataHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String formattedDate = LocalDateTime.parse(dataHora, formatter)
                .atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault())
                .format(newFormat);
        return formattedDate;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}

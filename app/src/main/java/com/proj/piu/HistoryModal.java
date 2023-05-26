package com.proj.piu;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class HistoryModal {

    private String dataHora;
    private String arquivo;
    private int enviado;
    private String especie;
    private String nomeComum;
    private long id;


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

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public int getEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
    }

    public String getEspecie() {
        if(especie != null){
            return especie;
        }else {
            if(enviado == 0){
                return "Não enviado";
            }else{
                return "Não identificado";
            }
        }
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HistoryModal(){};

    public HistoryModal(long id,
                        String dataHora,
                        String arquivo,
                        int enviado,
                        String especie,
                        String nomeComum){
        this.id = id;
        this.dataHora = dataHora;
        this.arquivo = arquivo;
        this.enviado = enviado;
        this.especie = especie;
        this.nomeComum = nomeComum;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public void setNomeComum(String nomecomum) {
        this.nomeComum = nomecomum;
    }
}

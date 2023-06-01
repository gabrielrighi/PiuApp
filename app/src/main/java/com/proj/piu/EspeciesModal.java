package com.proj.piu;

public class EspeciesModal {
    private String especie;
    private String nomeCientifico;
    private String nomeComum;

    public EspeciesModal(){};

    public EspeciesModal(String especie,
                        String nomeCientifico,
                        String nomeComum){
        this.especie = especie;
        this.nomeCientifico = nomeCientifico;
        this.nomeComum = nomeComum;
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
}

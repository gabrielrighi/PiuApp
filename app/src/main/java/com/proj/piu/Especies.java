package com.proj.piu;

public class Especies {

    private String especieId;
    private String nomeCientifico;
    private String nomeComum;
    private String urlWikipedia;
    private String urlWikiaves;
    private String urlEbird;

    public Especies(){};

    public Especies(String especieId,
                    String nomeCientifico,
                    String nomeComum,
                    String urlWikipedia,
                    String urlWikiaves,
                    String urlEbird){
        this.especieId = especieId;
        this.nomeCientifico = nomeCientifico;
        this.nomeComum = nomeComum;
        this.urlWikipedia = urlWikipedia;
        this.urlWikiaves = urlWikiaves;
        this.urlEbird = urlEbird;
    }

    public String getEspecieId() {
        return especieId;
    }

    public void setEspecieId(String especieId) {
        this.especieId = especieId;
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

    public String getUrlWikipedia() {
        return urlWikipedia;
    }

    public void setUrlWikipedia(String urlWikipedia) {
        this.urlWikipedia = urlWikipedia;
    }

    public String getUrlWikiaves() {
        return urlWikiaves;
    }

    public void setUrlWikiaves(String urlWikiaves) {
        this.urlWikiaves = urlWikiaves;
    }

    public String getUrlEbird() {
        return urlEbird;
    }

    public void setUrlEbird(String urlEbird) {
        this.urlEbird = urlEbird;
    }
}

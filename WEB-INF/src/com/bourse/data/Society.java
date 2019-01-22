package com.bourse.data;
public class Society {
    ///Attributes
    private String idSociety;
    private String societyName;
    private int titreNumber;

    ///Setters and Getters

    public String getIdSociety() {
        return this.idSociety;
    }

    public void setIdSociety(String idSociety) {
        this.idSociety = idSociety;
    }

    public String getSocietyName() {
        return this.societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public int getTitreNumber() {
        return this.titreNumber;
    }

    public void setTitreNumber(int titreNumber) {
        this.titreNumber = titreNumber;
    }

    public Society idSociety(String idSociety) {
        this.idSociety = idSociety;
        return this;
    }

    public Society societyName(String societyName) {
        this.societyName = societyName;
        return this;
    }

    public Society titreNumber(int titreNumber) {
        this.titreNumber = titreNumber;
        return this;
    }

    ///Constructors

    public Society(String idSociety, String societyName, int titreNumber) {
        this.setIdSociety(idSociety);
        this.setSocietyName(societyName);
        this.setTitreNumber(titreNumber);
    }
    public Society(){}
}
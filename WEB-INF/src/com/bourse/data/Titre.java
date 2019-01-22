package com.bourse.data;
import com.bourse.principale.*;
public class Titre {
    ///Atributes
    private String idTitre;
    private String reference;
    private String idSociety;
    private String idClient;
    ///Setters and Getters

    public String getIdTitre() {
        return this.idTitre;
    }

    public void setIdTitre(String idTitre) {
        this.idTitre = idTitre;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getIdSociety() {
        return this.idSociety;
    }

    public void setIdSociety(String idSociety) {
        this.idSociety = idSociety;
    }

    public String getIdClient() {
        return this.idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public Titre idTitre(String idTitre) {
        this.idTitre = idTitre;
        return this;
    }

    public Titre reference(String reference) {
        this.reference = reference;
        return this;
    }

    public Titre idSociety(String idSociety) {
        this.idSociety = idSociety;
        return this;
    }

    public Titre idClient(String idClient) {
        this.idClient = idClient;
        return this;
    }

    ///Constructors

    public Titre(String idTitre, String reference, String idSociety, String idClient) {
        this.idTitre = idTitre;
        this.reference = reference;
        this.idSociety = idSociety;
        this.idClient = idClient;
    }
    public Titre() {
    }
    
    ///Others Method
    public Society getSociety(DbConnect connection) throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] societyList=myFunction.selectAllFromWithCondition("Society","com.bourse.data","idSociety like '"+this.getIdSociety()+"'", connection);
        if(societyList.length==0){
            throw new Exception("Society not Found");
        }
        return (Society)societyList[0];
    }
}
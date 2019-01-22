package com.bourse.data;
public class Client {
/// Attributes
    private String idClient;
    private String clientName;
/// Setters and Getters

    public String getIdClient() {
        return this.idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Client idClient(String idClient) {
        this.idClient = idClient;
        return this;
    }
    
    public Client clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }
    
    /// Constructors
    public Client(String idClient, String clientName) {
        this.setIdClient(idClient);
        this.setClientName(clientName);
    }
    public Client(){}
}
package com.bourse.data;
public class Broker {
    /// Attributes
    private String idBroker;
    private String brokerName;
    private double rate;
    private double money;
    private int note;

    /// Setters and Getters
    public String getIdBroker() {
        return this.idBroker;
    }

    public void setIdBroker(String idBroker) {
        this.idBroker = idBroker;
    }

    public String getBrokerName() {
        return this.brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getNote() {
        return this.note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public Broker idBroker(String idBroker) {
        this.idBroker = idBroker;
        return this;
    }

    public Broker brokerName(String brokerName) {
        this.brokerName = brokerName;
        return this;
    }

    public Broker rate(double rate) {
        this.rate = rate;
        return this;
    }

    public Broker money(double money) {
        this.money = money;
        return this;
    }

    public Broker note(int note) {
        this.note = note;
        return this;
    }

    /// Constructor
    public Broker(String idBroker, String brokerName, double rate, double money, int note) {
        this.setIdBroker(idBroker);
        this.setBrokerName(brokerName);
        this.setRate(rate);
        this.setMoney(money);
        this.setNote(note);
    }

    public Broker() {}
}
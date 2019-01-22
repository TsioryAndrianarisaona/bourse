package com.bourse.data;
import com.bourse.principale.*;
import java.sql.Timestamp;
public class OrderPursache {
    ///Attributes
    private String idOrderPursache;
    private Timestamp dateStart;
    private double quantity;
    private double price;
    private String idBroker;
    private String idSociety;
    private String idClient;
    private int states;
    ///Setters and Getters

    public String getIdOrderPursache() {
        return this.idOrderPursache;
    }

    public void setIdOrderPursache(String idOrderPursache) {
        this.idOrderPursache = idOrderPursache;
    }

    public Timestamp getDateStart() {
        return this.dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIdBroker() {
        return this.idBroker;
    }

    public void setIdBroker(String idBroker) {
        this.idBroker = idBroker;
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

    public int getStates() {
        return this.states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public OrderPursache idOrderPursache(String idOrderPursache) {
        this.idOrderPursache = idOrderPursache;
        return this;
    }

    public OrderPursache dateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public OrderPursache quantity(double quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderPursache price(double price) {
        this.price = price;
        return this;
    }

    public OrderPursache idBroker(String idBroker) {
        this.idBroker = idBroker;
        return this;
    }

    public OrderPursache idSociety(String idSociety) {
        this.idSociety = idSociety;
        return this;
    }

    public OrderPursache idClient(String idClient) {
        this.idClient = idClient;
        return this;
    }

    public OrderPursache states(int states) {
        this.states = states;
        return this;
    }

    ///Constructor

    public OrderPursache(String idOrderPursache, Timestamp dateStart, double quantity, double price, String idBroker, String idSociety, String idClient, int states) {
        this.setIdOrderPursache(idOrderPursache);
        this.setDateStart(dateStart);
        this.setQuantity(quantity);
        this.setPrice(price);
        this.setIdBroker(idBroker);
        this.setIdSociety(idSociety);
        this.setIdClient(idClient);
        this.setStates(states);
    }
    public OrderPursache(){}

    ///Others Methods
    public Broker getBroker(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] brokerList=myFunction.selectAllFromWithCondition("Broker","com.bourse.data","idBroker like '"+this.getIdBroker()+"'",connection);
        if(brokerList.length==0){
            throw new Exception("Broker of Pursache Order not Found");
        }
        return (Broker)brokerList[0];
    }
    public Society getSociety(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] societyList=myFunction.selectAllFromWithCondition("Society","com.bourse.data","idSociety like '"+this.getIdSociety()+"'",connection);
        if(societyList.length==0){
            throw new Exception("Society of Pursache Order not Found");
        }
        return (Society)societyList[0];
    }
    public Client getClient(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] clientList=myFunction.selectAllFromWithCondition("Cleient","com.bourse.data","idClient like '"+this.getIdClient()+"'",connection);
        if(clientList.length==0){
            throw new Exception("Client of Pursache Order not Found");
        }
        return (Client)clientList[0];
    }
}
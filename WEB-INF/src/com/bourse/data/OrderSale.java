package com.bourse.data;
import java.sql.Timestamp;
import com.bourse.principale.*;
public class OrderSale {
///Attributes
    private String idOrderSale;
    private Timestamp  dateStart;
    private int quantity;
    private int quantityDispo;
    private String idBroker;
    private String idSociety;
    private String idClient;
    private double price;
    private int states;
///Setters and Getters

    public String getIdOrderSale() {
        return this.idOrderSale;
    }

    public void setIdOrderSale(String idOrderSale) {
        this.idOrderSale = idOrderSale;
    }

    public Timestamp getDateStart() {
        return this.dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityDispo() {
        return this.quantityDispo;
    }

    public void setQuantityDispo(int quantityDispo) {
        this.quantityDispo = quantityDispo;
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

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStates() {
        return this.states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public OrderSale idOrderSale(String idOrderSale) {
        this.idOrderSale = idOrderSale;
        return this;
    }

    public OrderSale dateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public OrderSale quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderSale quantityDispo(int quantityDispo) {
        this.quantityDispo = quantityDispo;
        return this;
    }

    public OrderSale idBroker(String idBroker) {
        this.idBroker = idBroker;
        return this;
    }

    public OrderSale idSociety(String idSociety) {
        this.idSociety = idSociety;
        return this;
    }

    public OrderSale idClient(String idClient) {
        this.idClient = idClient;
        return this;
    }

    public OrderSale price(double price) {
        this.price = price;
        return this;
    }

    public OrderSale states(int states) {
        this.states = states;
        return this;
    }

///Constructors

    public OrderSale(String idOrderSale, Timestamp dateStart, int quantity, int quantityDispo, String idBroker, String idSociety, String idClient, double price, int states) {
        this.setIdOrderSale(idOrderSale);
        this.setDateStart(dateStart);
        this.setQuantity(quantity);
        this.setQuantityDispo(quantityDispo);
        this.setIdBroker(idBroker);
        this.setIdSociety(idSociety);
        this.setIdClient(idClient);
        this.setPrice(price);
        this.setStates(states);
    }
    public OrderSale(){

    }
///Others Methods
    public Broker getBroker(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] brokerList=myFunction.selectAllFromWithCondition("Broker","com.bourse.data","idBroker like '"+this.getIdBroker()+"'",connection);
        if(brokerList.length==0){
            throw new Exception("Broker for Order Sale not Found");
        }
        return (Broker)brokerList[0];
    }
    public Society getSociety(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] societyList=myFunction.selectAllFromWithCondition("Society","com.bourse.data","idSociety like '"+this.getIdSociety()+"'",connection);
        if(societyList.length==0){
            throw new Exception("Society for Order Sale not Found");
        }
        return (Society)societyList[0];
    }
    public Client getClient(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] clientList=myFunction.selectAllFromWithCondition("Client","com.bourse.data","idClient like '"+this.getIdClient()+"'",connection);
        if(clientList.length==0){
            throw new Exception("Client for Order Sale not Found");
        }
        return (Client)clientList[0];
    }
}
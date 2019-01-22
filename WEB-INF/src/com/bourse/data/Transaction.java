package com.bourse.data;
import java.sql.Timestamp;
import com.bourse.principale.*;
public class Transaction {
    ///Attributes
    private String idTransaction;
    private String idOrderPursache;
    private double price;
    private int quantity;
    private String idBrokerSale;
    private String idLastProprio;
    private String idNewProprio;
    private Timestamp dateTransaction;
    private int states;
    private String idBrokerPursache;
    
    ///Setters and Getters

    public String getIdTransaction() {
        return this.idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdOrderPursache() {
        return this.idOrderPursache;
    }

    public void setIdOrderPursache(String idOrderPursache) {
        this.idOrderPursache = idOrderPursache;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIdBrokerSale() {
        return this.idBrokerSale;
    }

    public void setIdBrokerSale(String idBrokerSale) {
        this.idBrokerSale = idBrokerSale;
    }

    public String getIdLastProprio() {
        return this.idLastProprio;
    }

    public void setIdLastProprio(String idLastProprio) {
        this.idLastProprio = idLastProprio;
    }

    public String getIdNewProprio() {
        return this.idNewProprio;
    }

    public void setIdNewProprio(String idNewProprio) {
        this.idNewProprio = idNewProprio;
    }

    public Timestamp getDateTransaction() {
        return this.dateTransaction;
    }

    public void setDateTransaction(Timestamp dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public int getStates() {
        return this.states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getIdBrokerPursache() {
        return this.idBrokerPursache;
    }

    public void setIdBrokerPursache(String idBrokerPursache) {
        this.idBrokerPursache = idBrokerPursache;
    }

    public Transaction idTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
        return this;
    }

    public Transaction idOrderPursache(String idOrderPursache) {
        this.idOrderPursache = idOrderPursache;
        return this;
    }

    public Transaction price(double price) {
        this.price = price;
        return this;
    }

    public Transaction quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Transaction idBrokerSale(String idBrokerSale) {
        this.idBrokerSale = idBrokerSale;
        return this;
    }

    public Transaction idLastProprio(String idLastProprio) {
        this.idLastProprio = idLastProprio;
        return this;
    }

    public Transaction idNewProprio(String idNewProprio) {
        this.idNewProprio = idNewProprio;
        return this;
    }

    public Transaction dateTransaction(Timestamp dateTransaction) {
        this.dateTransaction = dateTransaction;
        return this;
    }

    public Transaction states(int states) {
        this.states = states;
        return this;
    }

    public Transaction idBrokerPursache(String idBrokerPursache) {
        this.idBrokerPursache = idBrokerPursache;
        return this;
    }

    ///Constructors

    public Transaction(String idTransaction, String idOrderPursache, double price, int quantity, String idBrokerSale, String idLastProprio, String idNewProprio, Timestamp dateTransaction, int states, String idBrokerPursache) {
        this.setIdTransaction(idTransaction);
        this.setIdOrderPursache(idOrderPursache);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setIdBrokerSale(idBrokerSale);
        this.setIdLastProprio(idLastProprio);
        this.setIdNewProprio(idNewProprio);
        this.setDateTransaction(dateTransaction);
        this.setStates(states);
        this.setIdBrokerPursache(idBrokerPursache);
    }
    public Transaction(){

    }
    ///Others Methods
    public OrderPursache getPursacheOrder(DbConnect connection)throws Exception{
        MyReflection myfunction=new MyReflection();
        Object[] orderPursacheList=myfunction.selectAllFromWithCondition("OrderPursache","com.bourse.data","idOrderPursache like '"+this.getIdBrokerPursache()+"'",connection);
        if(orderPursacheList.length==0){
            throw new Exception("Pursache Order of transaction not Found");
        }
        return (OrderPursache)orderPursacheList[0];
    }
    public Broker getBrokerSale(DbConnect connection)throws Exception{
        MyReflection myfunction=new MyReflection();
        Object[] brokerList=myfunction.selectAllFromWithCondition("Broker","com.bourse.data","idBroker like '"+this.getIdBrokerSale()+"'",connection);
        if(brokerList.length==0){
            throw new Exception("Broker of Sale Order in Transcation not found");
        }
        return (Broker)brokerList[0];
    }
    public Broker getBrokerPursache(DbConnect connection)throws Exception{
        MyReflection myfunction=new MyReflection();
        Object[] brokerList=myfunction.selectAllFromWithCondition("Broker","com.bourse.data","idBroker like '"+this.getIdBrokerPursache()+"'",connection);
        if(brokerList.length==0){
            throw new Exception("Broker of Pursache Order in Transcation not found");
        }
        return (Broker)brokerList[0];
    }
    public Client getLastProprio(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] clientList=myFunction.selectAllFromWithCondition("Client","com.bourse.data","idClient like '"+this.getIdLastProprio()+"'",connection);
        if(clientList.length==0){
            throw new Exception("Last Owner does not in Transaction");
        }
        return (Client)clientList[0];
    }
    public Client getNewProprio(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] clientList=myFunction.selectAllFromWithCondition("Client","com.bourse.data","idClient like '"+this.getIdNewProprio()+"'",connection);
        if(clientList.length==0){
            throw new Exception("New Owner does not in Transaction");
        }
        return (Client)clientList[0];
    }
}
package com.bourse.data;
import com.bourse.principale.*;
public class OrderPursache{
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

    ///Constructor

    ///Others Methods
    public Broker getBroker(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] brokerList=myFunction.selectAllFromWithCondition("Broker","data","idBroker like '"+this.getIdBroker()+"'",connection);
        if(brokerList.length==0){
            throw new Exception("Broker of Pursache Order not Found");
        }
        return (Broker)brokerList[0];
    }
    public Society getSociety(DbConnect connection)throws Exception{
        MyReflection myFunction=new MyReflection();
        Object[] societyList=myFunction.selectAllFromWithCondition("Society","data","idSociety like '"+this.getIdSociety()+"'",connection);
        if(societyList.length==0){
            throw new Exception("Society of Pursache Order not Found");
        }
        return (Society)societyList[0];
    }
    public Client getClient(DbConnect connection)throws Exception{
        
    }
}
package com.bourse.data;
import java.util.ArrayList;
public class Exchange{
    ///Attributes
    private Society society;
    private ArrayList<OrderPursache> orderPursacheList;
    private ArrayList<OrderSale> orderSaleList;
    ///Setters and Getters
    public Society getSociety(){
        return this.society;
    }
    public void setSociety(Society society){
        this.society=society;
    }
    public Exchange society(Society society){
        this.society=society;
        return this;
    }
    public OrderPursache[] getOrderPursacheList(){
        return orderPursacheList.toArray(new OrderPursache[0]);
    }
    public void setOrderPursacheList(ArrayList<OrderPursache> orderPursacheList){
        this.orderPursacheList=orderPursacheList;
    }
    public Exchange orderPursacheList(ArrayList<OrderPursache> orderPursacheList){
        this.orderPursacheList=orderPursacheList;
        return this;
    }
    public OrderSale[] getOrderSaleList(){
        return this.orderSaleList.toArray(new OrderSale[0]);
    }
    public void setOrderSaleList(ArrayList<OrderSale> orderSaleList){
        this.orderSaleList=orderSaleList;
    }
    public Exchange orderSaleList(ArrayList<OrderSale> orderSaleList){
        this.orderSaleList=orderSaleList;
        return this;
    }
    ///Constructor
    public Exchange(Society society,ArrayList<OrderPursache> orderPursacheList,ArrayList<OrderSale> orderSaleList){
        this.setSociety(society);
        this.setOrderPursacheList(orderPursacheList);
        this.setOrderSaleList(orderSaleList);
    }
    public Exchange(){}
}
package com.bourse.principale;
import com.bourse.data.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
public class MyFunction extends MyServices{
    public Exchange[] getExchangeTable(DbConnect connection)throws Exception{
        ArrayList<Exchange> exchangeList=new ArrayList<Exchange>();
        Object[] societyList=this.selectAllFrom("Society","com.bourse.data",connection);
        Object[] allOrderPursache=this.selectAllFromWithCondition("OrderPursache","com.bourse.data","states like 0",connection);
        Object[] allOrderSale=this.selectAllFromWithCondition("OrderSale","com.bourse.data","states like 0",connection);
        for(int i=0;i<societyList.length;i++){
            Society society=(Society)societyList[i];
            ArrayList orderPursacheList=this.find(allOrderPursache,"idSociety","like",society.getIdSociety());
            ArrayList orderSaleList=this.find(allOrderSale,"idSociety","like",society.getIdSociety());
            if(orderPursacheList.size() !=0 || orderSaleList.size() !=0){
                exchangeList.add(new Exchange(society, orderPursacheList, orderSaleList));
            }
        }
        return exchangeList.toArray(new Exchange[0]);
    }
    public int insertPursacheOrder(String dateTime,String quantityString,String priceString,String idBroker,String idSociety,String idClient,DbConnect connection)throws Exception{
        int result=0;
        Timestamp dateStart=Timestamp.valueOf(dateTime);
        int quantity=Integer.parseInt(quantityString);
        double price=this.transformToDouble(priceString);
        Object[] brokerList=this.selectAllFromWithCondition("Broker","com.bourse.data","idBroker like '"+idBroker+"'", connection);
        if(brokerList.length==0){
            throw new Exception("Broker not Found");
        }
        Object[] societyList=this.selectAllFromWithCondition("Society","com.bourse.data","idSociety like '"+idSociety+"'", connection);
        if(societyList.length==0){
            throw new Exception("Society not Found");
        }
        try{
            OrderPursache orderPursache=new OrderPursache(this.generateId("idOrderPursache","OP", connection), dateStart, quantity, price, idBroker, idSociety, idClient, 0);
            result+=this.insertInto(orderPursache,"OrderPursache", connection);
            connection.commit();
        }
        catch(Exception e){
            connection.rollback();
        }
        return result;
    }
    public int insertSaleOrder(String dateTime,String quantityString,String priceString,String idBroker,String idSociety,String idClient,DbConnect connection)throws Exception{
        int result=0;
        Timestamp dateStart=Timestamp.valueOf(dateTime);
        int quantity=Integer.parseInt(quantityString);
        double price=this.transformToDouble(priceString);
        Object[] brokerList=this.selectAllFromWithCondition("Broker","com.bourse.data","idBroker like '"+idBroker+"'", connection);
        if(brokerList.length==0){
            throw new Exception("Broker not Found");
        }
        Object[] societyList=this.selectAllFromWithCondition("Society","com.bourse.data","idSociety like '"+idSociety+"'", connection);
        if(societyList.length==0){
            throw new Exception("Society not Found");
        }
        Object[] titreList=this.selectAllFromWithCondition("Titre","com.bourse.data","idClient like '"+idClient+"'", connection);
        if(quantity>titreList.length){
            throw new Exception("You have not enough Titre");
        }
        try{
            OrderSale orderSale=new OrderSale((this.generateId("idOrderSale","OS", connection) , dateStart, quantity, quantity, idBroker, idSociety, idClient, price, 0);
            result+=this.insertInto(orderSale,"OrderSale", connection);
            for(int i=0;i<titreList.length;i++){
                Titre titre=(Titre)titreList[i];
                TitreOnSale titreOnSale=new TitreOnSale((this.generateId("idTitreOrderSale","OS", connection),titre.getIdTitre(),0,orderSale.getIdOrderSale());
                result+=this.insertInto(titreOnSale,"TitreOnSale", connection);
            }
            connection.commit();
        }
        catch(Exception e){
            connection.rollback();
            throw e;
        }
        return result;
    }
    public int Buisness(String idOrderSale,String idOrderPursache,DbConnect connection)throws Exception{
        int result=0;
        idOrderSale=idOrderSale.trim();
        idOrderPursache=idOrderPursache.trim();
        Object[] orderSaleList=this.selectAllFromWithCondition("OrderSale","com.bourse.data","idOrderSale like '"+idOrderSale+"'", connection);
        if(orderSaleList.length==0){
            throw new Exception("Order Sale not Found");
        }
        Object[] orderPursacheList=this.selectAllFromWithCondition("OrderPursache","com.bourse.data","idPursacheOrder like '"+idOrderPursache+"'", connection);
        if(orderPursacheList.length==0){
            throw new Exception("Pursache Order not Found");
        }
        OrderPursache orderPursache=(OrderPursache)orderPursacheList[0];
        OrderSale orderSale=(OrderSale)orderSaleList[0];
        if(orderPursache.getQuantity()>orderSale.getQuantityDispo()){
            throw new Exception("Quantity in Sale is less tahn Quantity in Pursache");
        }
        if(orderPursache.getPrice()<orderSale.getPrice()){
            throw new Exception("Price in Pursache could not be less than Price in Order");
        }
        try{
            int restQuantity=orderSale.getQuantity()-orderPursache.getQuantity();
            Transaction transaction=new Transaction(this.generateId("idTransaction","TR", connection), idOrderPursache,orderPursache.getPrice(),orderPursache.getQuantity(),orderSale.getIdBroker(),orderSale.getIdClient(),orderPursache.getIdClient(),this.getTimestampNow(connection),0,orderPursache.getIdBroker());
            result+=this.insertInto(transaction,"Transaction", connection);
            Object[] titreExchange=this.selectAllFromWithCondition("Titre","com.bourse.data","idClient like '"+orderSale.getIdClient()+"'", connection);
            for(int i=0;i<orderPursache.getQuantity();i++){
                Titre titre=(Titre)titreExchange[i];
                titre.setIdClient(orderPursache.getIdClient());
                result+=this.update(titre,"Titre","com.bourse.data","idTitre", connection);
                Object[] titreOnSaleList=this.selectAllFromWithCondition("TitreOnSale","com.bourse.data","idTitre like '"+titre.getIdTitre()+"'", connection);
                for(int j=0;j<titreOnSaleList.length;j++){
                    TitreOnSale titreOnSale=(TitreOnSale)titreOnSaleList[j];
                    titreOnSale.setSates(10);
                    result+=this.update(titreOnSale,"TitreOnSale","com.data.bourse","idTitreOnSale", connection);
                }
            }
            Broker brokerPursache=orderPursache.getBroker(connection);
            brokerPursache.setMoney(orderPursache.getPrice()*orderPursache.getQuantity()*brokerPursache.getRate()+orderPursache.getPrice()*orderPursache.getQuantity());
            brokerPursache.setNote(brokerPursache.getNote()+1);
            Broker brokerSale=orderSale.getBroker(connection);
            brokerSale.setMoney(orderPursache.getPrice()*orderPursache.getQuantity()*brokerSale.getRate()+orderPursache.getPrice()*orderPursache.getQuantity());
            brokerSale.setNote(brokerSale.getNote()+1);
            orderSale.setQuantityDispo(restQuantity);
            orderPursache.setStates(10);
            if(restQuantity==0){
                orderSale.setStates(10);
            }
            result+=this.update(orderSale,"OrderSale","com.data.bourse","idOrderSale", connection);
            result+=this.update(orderPursache,"OrderPursache","com.data.bourse","idOrderPursache", connection);
            connection.commit();
        }
        catch(Exception e){
            connection.rollback();
            result=0;
            throw e;
        }
        return result;
    }
    public int brokerSaleValidation(String idOrderSale,String idBroker,DbConnect connection)throws Exception{
        int result=0;
        idOrderSale=idOrderSale.trim();
        idBroker=idBroker.trim();
        Object[] orderSaleList=this.selectAllFromWithCondition("OrderSale","com.bourse.data","idOrderSale like '"+idOrderSale+"'", connection);
        if(orderSaleList.length==0){
            throw new Exception("Order Sale Not Found");
        }
        Object[] brokerList=this.selectAllFromWithCondition("Broker","com.bourse.data","idBroker like '"+idBroker+"'", connection);
        if(brokerList.length==0){
            throw new Exception("Broker not Found");
        }
        OrderSale orderSale=(OrderSale)orderSaleList[0];
        Broker broker=(Broker)brokerList[0];
        if(orderSale.getIdBroker().compareToIgnoreCase(broker.getIdBroker())!=0){
            throw new Exception("Broker who validate and Ask not Match");
        }
        orderSale.setStates(0);
        try {
            result+=this.update(orderSale,"OrderSale","com.bourse.data","idOrderSale", connection);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        return result;
    }
    public int brokerPursacheValidation(String idOrderPursache,String idBroker,DbConnect connection)throws Exception{
        int result=0;
        idOrderPursache=idOrderPursache.trim();
        idBroker=idBroker.trim();
        Object[] orderPursacheList=this.selectAllFromWithCondition("OrderPursache","com.bourse.data","idOrderPursache like '"+idOrderPursache+"'", connection);
        if(orderPursacheList.length==0){
            throw new Exception("Order Pursache Not Found");
        }
        Object[] brokerList=this.selectAllFromWithCondition("Broker","com.bourse.data","idBroker like '"+idBroker+"'", connection);
        if(brokerList.length==0){
            throw new Exception("Broker not Found");
        }
        OrderPursache orderPursache=(OrderPursache)orderPursacheList[0];
        Broker broker=(Broker)brokerList[0];
        if(orderPursache.getIdBroker().compareToIgnoreCase(broker.getIdBroker())!=0){
            throw new Exception("Broker who validate and Ask not Match");
        }
        orderPursache.setStates(0);
        try {
            result+=this.update(orderPursache,"OrderPursache","com.bourse.data","idOrderPursache", connection);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        return result;
    }
    
}
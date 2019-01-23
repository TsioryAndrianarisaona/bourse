package com.bourse.principale;
import com.bourse.data.*;
import java.util.ArrayList;
public class MyFunction extends MyServices{
    public ArrayList toArrayList(Object[] array){
        ArrayList result=new ArrayList();
        for(int i=0;i<array.length;i++){
            result.add(array[i]);
        }
        return result;
    } 
    public Exchange[] getExchangeTable(DbConnect connection){
        ArrayList<Exchange> exchangeList=new ArrayList<Exchange>();
        Object[] societyList=this.selectAllFrom("Society","com.bourse.data",connection);
        Object[] allOrderPursache=this.selectAllFromWithCondition("OrderPursache","com.bourse.data","states like 0",connection);
        Object[] allOrderSale=this.selectAllFromWithCondition("OrderSale","com.bourse.data","states like 0",connection);
        for(int i=0;i<societyList.length;i++){
            Society society=(Society)societyList[i];
        }
    }
}
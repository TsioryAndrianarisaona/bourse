package com.bourse.principale;
import java.sql.*;
import java.io.*;
import java.util.regex.*;
public class MyServices extends MyReflection{ 
    public Date treatmentDate(String date) throws Exception{
        Date result=null;
        Pattern formatEng=Pattern.compile("[0-9]{4}.[0-1][1-9].[0-9]{2}");
        Pattern formatFr=Pattern.compile("[0-9]{2}.[0-1][1-9].[0-9]{4}");
        Matcher matchEng=formatEng.matcher(date);
        Matcher matchFr=formatFr.matcher(date);
        Pattern splitter=Pattern.compile("\\W");
        if(matchEng.matches()){
            String[] tableauDate=splitter.split(date,3);
            date=""+tableauDate[0]+"-"+tableauDate[1]+"-"+tableauDate[2]+"";
            result=Date.valueOf(date);
        }
        else{
            if(matchFr.matches()){
                String[] tableauDate=splitter.split(date,3);
                date=""+tableauDate[2]+"-"+tableauDate[1]+"-"+tableauDate[0]+"";
                result=Date.valueOf(date);
            }
            else{
                throw new Exception("The date format is not valide");
            }
        }
        return result;
    }
}
package com.bourse.principale;
import java.lang.reflect.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
public class MyReflection {
	 // Upper the first char
    public String upperfirst(String string)throws Exception{
        //string.toLowerCase();
        char[] tabString=string.toCharArray();
        tabString[0]=Character.toUpperCase(tabString[0]);
        return String.valueOf(tabString);
    }
    public Field getSpecificField(Object objet,String fieldName)throws Exception{
        try{
            return objet.getClass().getDeclaredField(fieldName);
        }
        catch(NoSuchFieldException e){
            Field[] listeAttribut=objet.getClass().getDeclaredFields();
            for(int i=0;i<listeAttribut.length;i++){
                if(listeAttribut[i].getName().compareToIgnoreCase(fieldName)==0){
                    return listeAttribut[i];
                }
            }
            return null;
        }
        

    }
    // Get the function get of one field
    public Method getGettersOf(Object[] tabObject,String fieldName)throws Exception{
        fieldName=this.upperfirst(fieldName);
        String functionName="get".concat(fieldName);
        Class classOfObject=tabObject[0].getClass();
        try{
            Method getterFieldName=classOfObject.getMethod(functionName);
            return getterFieldName;
        }
        catch(NoSuchMethodException e){
            Field[] attributList=classOfObject.getDeclaredFields();
            for(int i=0;i<attributList.length;i++){
                if(attributList[i].getName().compareToIgnoreCase(fieldName)==0){
                    Method getterFieldName=classOfObject.getMethod("get".concat(this.upperfirst(attributList[i].getName())));
                    return getterFieldName;            
                }
            }
        }
       return null; 
    }
    public Method getGettersOf(Object object,String fieldName)throws Exception{
        fieldName=this.upperfirst(fieldName);
        String functionName="get".concat(fieldName);
        Class classOfObject=object.getClass();
        try{
            Method getterFieldName=classOfObject.getMethod(functionName);
            return getterFieldName;
        }
        catch(NoSuchMethodException e){
            Field[] attributList=classOfObject.getDeclaredFields();
            for(int i=0;i<attributList.length;i++){
                if(attributList[i].getName().compareToIgnoreCase(fieldName)==0){
                    Method getterFieldName=classOfObject.getMethod("get".concat(this.upperfirst(attributList[i].getName())));
                    return getterFieldName;            
                }
            }
        }
       return null; 
    }
    // Get the function With of one field
    public Method getWithof(Object object, String fieldName)throws Exception{
        Class classOfObject=object.getClass();
        Field specificField=classOfObject.getDeclaredField(fieldName);
        String functionName="with".concat(this.upperfirst(fieldName));
        Method withFieldName=classOfObject.getMethod(functionName, specificField.getType());
        return withFieldName;
    }
    // Get the function Set of one field
    public Method getSetof(Object object, String fieldName)throws Exception{
        Class classOfObject=object.getClass();
        Field specificField=this.getSpecificField(object, fieldName);
        String functionName="set".concat(this.upperfirst(fieldName));
        // System.out.println(specificField.getGenericType().toString());  
            Method setFieldName=classOfObject.getMethod(functionName, specificField.getType());
            return setFieldName;
       
    }

    // If the field is a number
    public boolean isItANumber(Class classOfObject, String fieldName)throws Exception{
        boolean result=false;
        Field[] fieldList=classOfObject.getDeclaredFields();
        for(int i=0;i<fieldList.length;i++){
            if(fieldName.toLowerCase().compareTo(fieldList[i].getName().toLowerCase())==0){
                if(fieldList[i].getGenericType().toString().compareTo("int")==0 || 
                   fieldList[i].getGenericType().toString().compareTo("double")==0
                ){
                    result=true;
                    break;
                }
            }
        }
        return result;
    }

    // Sum with reflect
    public double sum(Object[] tabObject, String fieldName)throws Exception{
        Field specificField=tabObject[0].getClass().getDeclaredField(fieldName);
        double result=0;
        Method getFieldName=this.getGettersOf(tabObject[0], fieldName);
      
        for(int i=0;i<tabObject.length;i++){
            if(specificField.getGenericType().toString().compareTo("java.lang.String")==0){
                result+=Double.parseDouble((String)getFieldName.invoke(tabObject[i]));
            }
            else{
                result+=Double.parseDouble(String.valueOf(getFieldName.invoke(tabObject[i])));
            }
        }
        return result;
    }
    public double avg(Object[] tabObject, String fieldName)throws Exception{
        Field specificField=tabObject[0].getClass().getDeclaredField(fieldName);
        double result=0;
        Method getFieldName=this.getGettersOf(tabObject[0], fieldName);
        for(int i=0;i<tabObject.length;i++){
            if(specificField.getGenericType().toString().compareTo("java.lang.String")==0){
                result+=Double.parseDouble((String)getFieldName.invoke(tabObject[i]));
            }
            else{
                result+=Double.parseDouble(String.valueOf(getFieldName.invoke(tabObject[i])));
            }
        }
        return result/tabObject.length;
    }

    // Insert Object to database
    public int insertInto(Object objet,String tableName,DbConnect connexion)throws Exception{
        Field[] fieldList=objet.getClass().getDeclaredFields();
        String sql="SELECT * FROM ".concat(tableName.toUpperCase());
        // System.out.println(sql);
        ResultSet resultTableName=connexion.getStatement().executeQuery(sql);
        ResultSetMetaData managerTableName=resultTableName.getMetaData();
        String queryInsert="INSERT INTO ".concat(tableName).concat(" VALUES (");
        for(int i=0;i<managerTableName.getColumnCount();i++){
            String columnName=managerTableName.getColumnName(i+1);
            for(int indiceFieldList=0;indiceFieldList<fieldList.length;indiceFieldList++){
                if(columnName.toLowerCase().compareToIgnoreCase(fieldList[indiceFieldList].getName())==0){
                    Method getfieldspecific=getGettersOf(objet,fieldList[indiceFieldList].getName());
                    if(managerTableName.getColumnTypeName(i+1).compareToIgnoreCase("NUMBER")==0){
                        queryInsert+=String.valueOf(getfieldspecific.invoke(objet));
                    }
                    else{
                            queryInsert+="'"+String.valueOf(getfieldspecific.invoke(objet))+"'";
                        
                    }
                    if( i!=managerTableName.getColumnCount()-1){
                        queryInsert+=",";
                    }
                }
            }
        }
        queryInsert+=")";
        int insertion=0;
        try{
            // System.out.println(queryInsert);
            insertion=connexion.getStatement().executeUpdate(queryInsert);
         
        }
        catch(Exception e){            
          
            throw e;
        }
       
        return insertion;
    }
    
    // Function select * from table 
    public Object[] selectAllFrom(String tableName,String packageName,DbConnect connexion)throws Exception{
        ArrayList<Object> tabObjet=new ArrayList();
        String querySelect="SELECT * FROM ".concat(tableName);
        try{
            ResultSet resultTableName=connexion.getStatement().executeQuery(querySelect);
            ResultSetMetaData managerTableName=resultTableName.getMetaData();
            Class classOfObject=Class.forName(packageName.concat(".").concat(tableName));
            while(resultTableName.next()){
                Object objet=classOfObject.newInstance();
                Field[] listeField=classOfObject.getDeclaredFields();
                for(int i=0;i<managerTableName.getColumnCount();i++){
                    for(int j=0;j<listeField.length;j++){
                    if(managerTableName.getColumnName(i+1).compareToIgnoreCase(listeField[j].getName())==0){
                        String fieldName=listeField[j].getName();
                        Method getFieldName=this.getSetof(objet,fieldName);
                        if(this.isItANumber(classOfObject, fieldName)){
                          
                            getFieldName.invoke(objet, resultTableName.getInt(i+1));
                        }
                        else{
                            if(listeField[j].getType().toString().compareToIgnoreCase("class java.sql.Timestamp")==0){
                                getFieldName.invoke(objet,resultTableName.getTimestamp(i+1));
                            }
                            else{
                                if(listeField[j].getType().toString().compareToIgnoreCase("class java.sql.Date")==0){
                                    getFieldName.invoke(objet,resultTableName.getDate(i+1));
                                }
                                else{
                                    getFieldName.invoke(objet,resultTableName.getString(i+1));
                                }
                            }
                        }
                }
                }
            }
                tabObjet.add(objet);
            }
        }
        catch(Exception e){
            throw e;
        }
        
        return tabObjet.toArray();
    }
    // Select Object with where
    public Object[] selectAllFromWithCondition(String tableName,String packageName,String condition,DbConnect connexion)throws Exception{
        ArrayList<Object> tabObjet=new ArrayList();
        String querySelect="SELECT * FROM ".concat(tableName).concat(" where ").concat(condition);
        System.out.println(querySelect);
        try{
            ResultSet resultTableName=connexion.getStatement().executeQuery(querySelect);
            ResultSetMetaData managerTableName=resultTableName.getMetaData();
            Class classOfObject=Class.forName(packageName.concat(".").concat(tableName));
            while(resultTableName.next()){
                Object objet=classOfObject.newInstance();
                Field[] listeField=classOfObject.getDeclaredFields();
                for(int i=0;i<managerTableName.getColumnCount();i++){
                    for(int j=0;j<listeField.length;j++){
                    if(managerTableName.getColumnName(i+1).compareToIgnoreCase(listeField[j].getName())==0){
                        String fieldName=listeField[j].getName();
                        Method getFieldName=this.getSetof(objet,fieldName);
                        if(this.isItANumber(classOfObject, fieldName)){
                            if(listeField[i].getGenericType().toString().compareToIgnoreCase("int")==0){
                                getFieldName.invoke(objet, resultTableName.getInt(i+1));

                            }
                            else{
                                getFieldName.invoke(objet, resultTableName.getDouble(i+1));

                            }
                        }
                        else{
                            if(listeField[j].getType().toString().compareToIgnoreCase("class java.sql.Timestamp")==0){
                                getFieldName.invoke(objet,resultTableName.getTimestamp(i+1));
                            }
                            else{
                                if(listeField[j].getType().toString().compareToIgnoreCase("class java.sql.Date")==0){
                                    getFieldName.invoke(objet,resultTableName.getDate(i+1));
                                }
                                else{
                                    getFieldName.invoke(objet,resultTableName.getString(i+1));
                                }
                            }
                        }
                }
                }
            }
                tabObjet.add(objet);
            }
        }
        catch(Exception e){
            throw e;
        }
        
        Object[] result=new Object[tabObjet.size()];
        for(int i=0;i<result.length;i++){
            result[i]=tabObjet.get(i);
        }
        return result;
    }
    // Trier tab en decroissant with one field
    public  void trierTableauendecr(Object[] liste, String trieur)throws Exception{
        Method getTrieur=this.getGettersOf(liste, trieur);
        for(int i=0;i<liste.length;i++){
            for(int j=0;j<liste.length-1;j++){
                if((double)getTrieur.invoke(liste[j])<(double)getTrieur.invoke(liste[j+1])){
                   Object temporary=liste[j+1];
                   liste[j+1]=liste[j];
                   liste[j]=temporary; 
                }   
            }
    
        }
    }

    // Trier tab en decr with  field[]
    public void trierTableauendecr(Object[] liste,String[] tabtrieur)throws Exception{
        for(int k=tabtrieur.length-1;k>=0;k--){
            Method use=this.getGettersOf(liste, tabtrieur[k]);
            for(int i=0;i<liste.length;i++){
                for(int j=0;j<liste.length-1;j++){
                    if((double)use.invoke(liste[j])<(double)use.invoke(liste[j+1])){
                    Object temp=liste[j+1];
                    liste[j+1]=liste[j];
                    liste[j]=temp; 
                    }   
                }    
            }
        }
    }
    public void setAndCaster(Object objet,Field field,String insertion) throws Exception{
        Method withtFieldName=this.getSetof(objet,field.getName());
        if(this.isItANumber(objet.getClass(), field.getName())){
            withtFieldName.invoke(objet, Integer.parseInt(insertion));
        }
        else{
            if(field.getType().toString().compareTo("class java.sql.Timestamp")==0){
                withtFieldName.invoke(objet,Timestamp.valueOf(insertion));
            }
            else{
                withtFieldName.invoke(objet,insertion);
            }
        }
    }

    public String[] splitNumberToHundreds(double number){
        int numberInt = (int)number;
        String numberString = String.valueOf(numberInt);
        int numberLength = numberString.length();
        int resultLength = (int)(numberLength/3);
        if(numberLength%3!=0)
        {
            resultLength++;
        }
        String[] result = new String[resultLength];
        // String[] resultTemp = new String[resultLength];
        char[] resultTempChar = new char[3];
        int rest = numberLength-1;
        int restResult = resultLength - 1;
        while(rest >= 0)
        {
            if(rest >= 2)
            {
                for(int j = 2; j>=0; j--)
                {
                    resultTempChar[j] = numberString.charAt(rest);
                    rest--;
                    
                }
            }else{
                resultTempChar = new char[rest+1];
                for(int restTemp = rest; restTemp >= 0; restTemp--)
                {
                    resultTempChar[restTemp] = numberString.charAt(rest);
                    rest--;
                }
            }
            result[restResult] = String.valueOf(resultTempChar);
            restResult--;
        }
        return result;
    }
    public String hundredToString(int number){
        String result = "";
        if(number < 1000){
            String[] unity1 = {"","un","deux","trois","quatre","cinq","six","sept","huit","neuf"};
            String[] unity2 = {"","onze","douze","treize","quatorze","quinze","seize","dix-sept","dix-huit","dix-neuf"};
            String[] decade = {"","dix","vingt","trente","quarante","cinquante","soixante","soixante","quatre-vingt","quatre-vingt"};
            int[] divideNumber = new int[3];
            int puissance = 2, numberTemp = number, puissanceCalcul = 0;
            for (int i = 0; i < divideNumber.length; i++) {
                puissanceCalcul = (int)Math.pow(10,puissance);
                divideNumber[i] =(int)numberTemp/puissanceCalcul;
                numberTemp =(int) numberTemp-(divideNumber[i]*puissanceCalcul);
                puissance--;
            }
            if(divideNumber[0] != 0){
                if(divideNumber[0] != 1)
                {
                    result +=unity1[divideNumber[0]]+" cent";
                }else{
                    result += "cent";
                }
                
            }
            if(divideNumber[1] != 0){
                if(divideNumber[1] != 1){
                    result +=" "+decade[divideNumber[1]];
                    if(divideNumber[2] != 0)
                    {
                        if(divideNumber[1] == 7 || divideNumber[1] == 9)
                        {
                            result +=" "+unity2[divideNumber[2]];
                        }else{
                            result +=" "+unity1[divideNumber[2]];
                        }
                    }
                }else{
                    result +=" "+unity2[divideNumber[2]];
                }

            }else{
                result += unity1[divideNumber[2]];
            }
        }else{
            result = "non calculable";
        }
        return result;
    }
    public String lettrer(double number){
        String result = "";
        String[] plusHundred = {"","mille","million","milliard"};
        String[] hundredSplit = this.splitNumberToHundreds(number);
        int[] hundredSplitInt = new int[hundredSplit.length];
        for (int i = 0; i <hundredSplit.length; i++) {
            hundredSplitInt[i] = Integer.parseInt(hundredSplit[i]);
        }
        String[] resultTemp = new String[hundredSplit.length];
        for (int i = 0; i < resultTemp.length; i++) {
            resultTemp[i] = this.hundredToString(hundredSplitInt[i]);
        }
        int indice = resultTemp.length - 1;
        String hundredTemp = "";
        for (int i = 0; i < resultTemp.length; i++) {
            if(hundredSplitInt[i]==1 || indice == 0){
                hundredTemp = plusHundred[indice];
            }else{
                hundredTemp = plusHundred[indice]+"s";
            }
            if(hundredSplitInt[i]==1 && indice == 1){
                result +=" "+hundredTemp;
                indice--;
            }else{
                result +=" "+resultTemp[i]+" "+hundredTemp;
                indice--;
            }
        }
        return result;
    }
	
    public String generateId(String sequenceName,String sequenceInitial, DbConnect connexion)throws Exception{
        String requestGenerateSequence="select "+sequenceName+".nextval from dual";
        ResultSet sequence=connexion.getStatement().executeQuery(requestGenerateSequence);
        String indiceSequence="";
        while(sequence.next()){
            indiceSequence=sequence.getString(1);
        }
        String result=sequenceInitial.concat(indiceSequence);
        return result;
    }
        // Get date Now
    public Date getDateNow(DbConnect connexion)throws Exception{
        String sql="select sysdate from dual";
        ResultSet dateBase=connexion.getStatement().executeQuery(sql);
        String date="";
        while(dateBase.next()){
            date+=dateBase.getString(1);
        }
        String[] temp=date.split(" ",2);
        return Date.valueOf(temp[0]);
    }
    public Timestamp getTimestampNow(DbConnect connection)throws Exception{
        String sql="select sysdate from dual";
        ResultSet dateBase=connection.getStatement().executeQuery(sql);
        String date="";
        while(dateBase.next()){
            date+=dateBase.getString(1);
        }
        return Timestamp.valueOf(date);
    }
    public ArrayList find(Object[] listeObject,String attribut,String operation,String condition)throws Exception{
        ArrayList resultObject=new ArrayList();
        ArrayList listeObjectVect=new ArrayList(Arrays.asList(listeObject));
        for(int i=0;i<listeObjectVect.size();i++){
            Method getAttribut=this.getGettersOf(listeObjectVect.get(i),attribut);
            Field specificField=this.getSpecificField(listeObjectVect.get(i),attribut);
            if(operation.compareToIgnoreCase("<")==0 || operation.compareToIgnoreCase(">")==0){
                if(specificField.getGenericType().toString().compareToIgnoreCase("java.sql.Timestamp")==0){
                    Timestamp dateCondition=Timestamp.valueOf(condition);
                    Timestamp dateObjet=Timestamp.valueOf(String.valueOf(getAttribut.invoke(listeObjectVect.get(i))));
                    if(operation.compareTo("<")==0){
                        if(dateObjet.compareTo(dateCondition)<0){
                            resultObject.add(listeObjectVect.get(i));
                            listeObjectVect.remove(i);
                            i--;
                        }
                    }
                    else{
                        if(dateObjet.compareTo(dateCondition)>0){
                            resultObject.add(listeObjectVect.get(i));
                            listeObjectVect.remove(i);
                            i--;
                        } 
                    }
                }
                else{
                    if(specificField.getGenericType().toString().compareToIgnoreCase("java.sql.Date")==0){
                        Date dateCondition=Date.valueOf(condition);
                        Date dateObjet=Date.valueOf(String.valueOf(getAttribut.invoke(listeObjectVect.get(i))));
                        if(operation.compareTo("<")==0){
                            if(dateObjet.compareTo(dateCondition)<0){
                                resultObject.add(listeObjectVect.get(i));
                                listeObjectVect.remove(i);
                                i--;
                            }
                        }
                        else{
                            if(dateObjet.compareTo(dateCondition)>0){
                                resultObject.add(listeObjectVect.get(i));
                                listeObjectVect.remove(i);
                                i--;
                            } 
                        }
                    }
                    else{
                    double chiffreCond=Double.parseDouble(condition);
                    double chiffreObjet=Double.parseDouble(String.valueOf(getAttribut.invoke(listeObjectVect.get(i))));
                        if(operation.compareTo("<")==0){
                            if(chiffreObjet<chiffreCond){
                                resultObject.add(listeObjectVect.get(i));
                                listeObjectVect.remove(i);
                                i--;
                            }      
                        }
                        else{
                            resultObject.add(listeObjectVect.get(i));
                            listeObjectVect.remove(i);
                            i--;
                        }
                    }
                    }    
                }
                else{
                    // System.out.println("||"+String.valueOf(getAttribut.invoke(listeObjectVect.get(i)))+"|| vs ||"+condition+"||");
                    if(String.valueOf(getAttribut.invoke(listeObjectVect.get(i))).compareToIgnoreCase(condition)==0){
                        resultObject.add(listeObjectVect.get(i));
                        listeObjectVect.remove(i);
                        i--;
                    }
                }
            }
        listeObject=listeObjectVect.toArray();
        return resultObject;
    }

    // Trier tab en cr with  field[]
        public void trierTableauencr(Object[] liste,String[] tabtrieur)throws Exception{
            for(int k=tabtrieur.length-1;k>=0;k--){
                Method use=this.getGettersOf(liste, tabtrieur[k]);
                for(int i=0;i<liste.length;i++){
                    for(int j=0;j<liste.length-1;j++){
                        if((double)use.invoke(liste[j])>(double)use.invoke(liste[j+1])){
                        Object temp=liste[j+1];
                        liste[j+1]=liste[j];
                        liste[j]=temp; 
                        }   
                    }    
                }
            }
        }
    //Day of week
    public String[] getAllDayOfWeek(){
        String[] listeDay=new String[7];
        listeDay[0]="Sunday";
        listeDay[1]="Monday";
        listeDay[2]="Tuesday";
        listeDay[3]="Wednesday";
        listeDay[4]="Thursday";
        listeDay[5]="Friday";
        listeDay[6]="Saturday";
        return listeDay;
    }
    //Transform String to number
    public double transformToDouble(String chiffre)throws Exception{
        String[] tableau=chiffre.split("\\W");
        String chiffreD="";
        for(int i=0;i<tableau.length;i++){
            chiffreD+=tableau[i];
        }
        double result=0;
        try{
            result=Double.parseDouble(chiffreD);
        }
        catch(NumberFormatException e){
            throw new Exception("Not a Number");
        }
        return result;
    }
    public String espacement(String number){
        System.out.println(number);
        String result="";
        char[] tabNumb=number.toCharArray();
        String numberInverse="";
        for(int i=tabNumb.length-1;i>=0;i--){
            if(i%3==0 &&  i!= tabNumb.length-1){
                numberInverse+=".";
            }
            numberInverse+=tabNumb[i];
        }
        char[] tabInverse=numberInverse.toCharArray();
        for(int i=tabInverse.length-1;i>=0;i--){
            result+=tabInverse[i];
        }
        return result;
    }
    public String format(double number)throws Exception{
        String numberSt=String.valueOf(number);
        String[] tableau=numberSt.split("\\W");
        String partDec=tableau[1];
        String partEn=tableau[0];
        if(partDec.compareTo("0")==0){
            partDec="";
        }
        else{
            partDec=","+partDec;
        }
        partEn=this.espacement(partEn);
        return partEn+partDec;
    }
    public int update(Object object,String tableName,String packageName,String idName,DbConnect connexion)throws Exception{
        int lineUpdate=0;
        Method getIdValue=this.getGettersOf(object,idName);
        String idValue=String.valueOf(getIdValue.invoke(object));
        Object[] listUpdate=this.selectAllFromWithCondition(tableName, packageName,idName+" like '"+idValue+"'",connexion);
        if(listUpdate.length==0){
            throw new Exception("Line to Update not found");
        }
        Field[] attributList=object.getClass().getDeclaredFields();
        String sql="SELECT * FROM ".concat(tableName.toUpperCase());
        ResultSet resultTableName=connexion.getStatement().executeQuery(sql);
        ResultSetMetaData managerTableName=resultTableName.getMetaData();
        String sqlUpdate="UPDATE ".concat(tableName).concat(" set ");
        for(int i=0;i<managerTableName.getColumnCount();i++){
            for(int j=0;j<attributList.length;j++){
                if(managerTableName.getColumnName(i+1).compareToIgnoreCase(attributList[j].getName())==0){
                    Method getAttribut=this.getGettersOf(object, attributList[j].getName());
                    String value=String.valueOf(getAttribut.invoke(object));
                    sqlUpdate+=managerTableName.getColumnName(i+1).concat(" = ");
                    if(managerTableName.getColumnTypeName(i+1).compareToIgnoreCase("NUMBER")==0){
                        sqlUpdate+=value;
                    }
                    else{
                        sqlUpdate+="'"+value+"'";
                    }
                    if( i!=managerTableName.getColumnCount()-1){
                        sqlUpdate+=",";
                    }
                }
            }
        }
        sqlUpdate+= " where "+idName+" like '"+idValue+"'"; 
        // System.out.print(sqlUpdate);
        try{
            lineUpdate=connexion.getStatement().executeUpdate(sqlUpdate);
        }
        catch(Exception e){
            throw new Exception("Update could not be Etablishe");
        }
        finally{
            resultTableName.close();
            return lineUpdate;
        }
    }
    public int between2Date(Date date1, Date date2) {
		long diff = Math.abs(date1.getTime()-date2.getTime());
		long enleverHMS = 86400000l;
		int res = new Integer(""+diff/enleverHMS).intValue();
		return res;
	}
}
package com.bourse.principale;
import java.sql.*;
public class DbConnect {
	private Statement stmt;
    private Connection connexion;
        
    public Statement getStatement(){
        return stmt;
    }
    public Connection getConnection(){
        return connexion;
    }
    public DbConnect()throws Exception{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connexion=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bourse","bourse");
            stmt=connexion.createStatement(); 
            this.getStatement().executeQuery("alter session set nls_date_format ='YYYY-MM-DD' ");
            this.getStatement().executeQuery("alter session set nls_timestamp_format ='YYYY-MM-DD H24:MI:SS.FFF' ");
            connexion.setAutoCommit(false);
    }
    public void closeDbConnect() throws Exception{
        try{
            this.getConnection().close();
            this.getStatement().close();
        }
        catch(Exception e){
           throw new Exception("Connection could not be closed");
        }
    }
    public void commit()throws Exception{
        try{
            this.getConnection().commit();
        }
        catch(Exception e){
            throw e;
        }
    }
    public void rollback()throws Exception{
        try{
            this.getConnection().rollback();
        }
        catch(Exception e){
            throw e;
        }
    }
}

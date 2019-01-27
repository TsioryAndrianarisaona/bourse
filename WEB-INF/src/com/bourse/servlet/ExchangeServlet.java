package com.bourse.servlet;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bourse.principale.*;
import com.bourse.data.*;
public class ExchangeServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ServletOutputStream out = response.getOutputStream();
        out.println("<!DOCTYPE html> <html lang='en'> <meta charset='UTF-8'> <meta name='viewport' content='width=device-width, initial-scale=1.0'> <meta http-equiv='X-UA-Compatible' content='ie=edge'><link rel='stylesheet' href='css/bootstrap.min.css'> <link rel='stylesheet' href='css/all.min.css'><script src='js/all.min.js'></script><title>Bourse</title></head><body>");
        DbConnect connection=new DbConnect();
        try {
            MyFunction myFunction=new MyFunction();
            Exchange[] exchange=myFunction.getExchangeTable(connection);

        } catch (Exception e) {
            
        }
        finally{
            connection.closeDbConnect();
        }
        out.println("<script src='js/jQuery.js'></script><script>window.jQuery || document.write('<script src='js/jquery-slim.min.js'><\/script>')</script><script src='js/popper.min.js'></script><script src='js/bootstrap.min.js'></script></body></html>");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
    }
}
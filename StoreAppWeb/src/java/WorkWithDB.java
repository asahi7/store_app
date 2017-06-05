/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.*;

/**
 *
 * @author sanzharaubakir
 */
@WebServlet(urlPatterns = {"/WorkWithDB"})
public class WorkWithDB extends HttpServlet {
    private String task, code, quantity, name, price;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        code = request.getParameter("code").toString();
        quantity = request.getParameter("quantity").toString();
        task = request.getParameter("task").toString();
        try{
                        
            Class.forName("org.postgresql.Driver");
            String ip = "10.64.128.175";
            Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://" + ip + ":5432/storeapp", "postgres", "helloworld123");
            Statement statement = connection.createStatement();
            if (task.equals("register"))
            {
                name = request.getParameter("name").toString();
                price = request.getParameter("price").toString();
                ResultSet rs = statement.executeQuery("insert into core_goods (name, category, price, quantity, bar_code, store_id) values "
                                                    + "('" + name + "', 'uncategorized', " + price + ", " + quantity + ", '" + code + "', 1)");
            
            }
            else
            {
                ResultSet rs = statement.executeQuery("select * from core_goods where bar_code=('" + code + "')");
                rs.next();
                price = rs.getString("price");
                int good_id = rs.getInt("id");
                int money = (Integer.parseInt(price))*(Integer.parseInt(quantity));
                Date currentDate = new Date();
                Instant instant = Instant.now();
                Timestamp ts = instant != null ? new Timestamp(instant.toEpochMilli()) : null;
                String time_ts = ts.toString();
                
                Logger.getLogger (WorkWithDB.class.getName()).log(Level.INFO, "price - " + price);
                Logger.getLogger (WorkWithDB.class.getName()).log(Level.INFO, "good_id - " + good_id);
                Logger.getLogger (WorkWithDB.class.getName()).log(Level.INFO, "time_ts - " + time_ts);
                
                
                rs = statement.executeQuery("insert into core_purchases (money, quantity, good_id, seller_id, date) values "
                                                    + "(" + money + ", " + quantity + ", " + good_id + ", 1, '" + time_ts +"')");
            
            }
            
            }
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
         }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

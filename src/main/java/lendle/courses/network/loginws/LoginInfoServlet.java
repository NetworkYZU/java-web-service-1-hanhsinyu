/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendle.courses.network.loginws;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lendle
 */
@WebServlet(name = "LoginInfoServlet", urlPatterns = {"/login_info"})
public class LoginInfoServlet extends HttpServlet {
    static{
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getImpl1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //select from login
            //output in id:password style
            //this time, consider the id parameter
            String id=request.getParameter("id");
            PreparedStatement pstmt=conn.prepareStatement("select * from login where id=?");
            pstmt.setString(1, id);
            ResultSet rs=pstmt.executeQuery();
            if (rs.next()) {
                Map map=new HashMap();
                map.put("id", rs.getString("ID"));
                map.put("password", rs.getString("PASSWORD"));
                out.print(new Gson().toJson(map));
            }else{
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            
            }
            //////////////////////////////
        }catch(Exception e){
            throw new ServletException(e);
        }
    }
    
    private void getImpl2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //re-implement getImpl1
            //this time reponde in json-format
            //and use gson
            String id=request.getParameter("id");
            
            //////////////////////////////
        }catch(Exception e){
            throw new ServletException(e);
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
        getImpl1(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //update the corresponding user
            String id=request.getParameter("id");
            String password=request.getParameter("password");
            PreparedStatement stmt=conn.prepareStatement("update LOGIN set PASSWORD=? where ID=?");
            stmt.setString(1, password);
            stmt.setString(2, id);
            getServletContext().log("id="+id+", pass="+password);
            int ret=stmt.executeUpdate();
            //////////////////////////////
            out.println(ret);
        }catch(Exception e){
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //delete the corresponding user
            String id=request.getParameter("id");
            PreparedStatement stmt=conn.prepareStatement("delete from login where ID=?");
            stmt.setString(1, id);
            int ret=stmt.executeUpdate();
            //////////////////////////////
            out.println(ret);
        }catch(Exception e){
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //insert the corresponding user
            String id=request.getParameter("id");
            String password=request.getParameter("password");
            PreparedStatement pstmt=conn.prepareStatement("insert into login (ID,PASSWORD) values (?,?)");
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            int ret=pstmt.executeUpdate();
            //////////////////////////////
            out.print(ret);
            //out.println("success");
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

}
//ID:<input type="text" id="qid"/>
  //      <input type="button" value="Query" onclick="query();"/><br/>
    //    <div id="dlg">
      //  id:<input type="text" v-model="login.id"/><br/>
        //password:<input type="text" v-model="login.password">
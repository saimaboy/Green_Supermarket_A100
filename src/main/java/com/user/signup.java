/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.user;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */

@WebServlet(name = "signup", value = "/signup")
public class signup extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Connection connection = null;
        PreparedStatement statement = null;


        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);


            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, username);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                HttpSession session = req.getSession();
                session.setAttribute("userEmail", email);

                resp.sendRedirect("/getUserData");
            } else {
                resp.sendRedirect("../login/error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) dbConnection.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
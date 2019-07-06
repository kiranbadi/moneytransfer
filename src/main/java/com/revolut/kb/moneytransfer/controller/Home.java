/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kiran
 */

@WebServlet("/home")
public class Home extends HttpServlet{
    private static final long serialVersionUID = -3462096228274971485L;
    
    protected void doGET(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/home.jsp");  
        rd.forward(request, response);  
        
    }
}

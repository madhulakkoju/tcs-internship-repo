package com.backend.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	public boolean authenticate(String id, String pwd)
	{
		//Authenticate
		return true;
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(authenticate((String)request.getParameter("email"),(String)request.getParameter("password")))
		{
			HttpSession session = request.getSession();
			session.setAttribute("loggedIn", true);
			System.out.println("User Authenticated");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.html");
			dispatcher.forward(request,response);
		}
	}
}

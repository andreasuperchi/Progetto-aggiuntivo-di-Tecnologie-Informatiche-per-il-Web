package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
    
    public RegisterUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String experience = request.getParameter("experience");
		Blob photo = (Blob) request.getAttribute("photo");
		String role = request.getParameter("role");
		
		if (role.equals("manager")) {
			ManagerDAO manDAO = new ManagerDAO(connection, );
			User manBean = null;
		} else {
			
		}
	}

}

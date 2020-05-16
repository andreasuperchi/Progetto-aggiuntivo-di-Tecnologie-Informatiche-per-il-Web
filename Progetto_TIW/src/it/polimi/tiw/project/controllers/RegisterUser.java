package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.project.dao.UserDAO;

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
		
		UserDAO userDAO = new UserDAO(connection);
		
		try {
			userDAO.addUser(username, password, email, experience, photo, role);
		} catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Registration failure!");
		}
		
		String loginPath = getServletContext().getContextPath() + "/index.html";
		
		response.sendRedirect(loginPath);	//reindirizzo l'utente appena registrato alla pagina di login
	}

}

package it.polimi.tiw.project.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
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

	public void init() throws ServletException {
		try {
			ServletContext context = getServletContext();
			String driver = context.getInitParameter("dbDriver");
			String url = context.getInitParameter("dbUrl");
			String user = context.getInitParameter("dbUser");
			String password = context.getInitParameter("dbPassword");
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Error! Unable to load database driver.");
		} catch (SQLException e) {
			throw new UnavailableException("Error! Couldn't get database connection.");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String experience = request.getParameter("experience");

		File photo = new File(request.getParameter("photo"));
		String photoPath = photo.getAbsolutePath();

		String role = request.getParameter("role");

		UserDAO userDAO = new UserDAO(connection);

		try {
			userDAO.addUser(username, password, email, experience, photoPath, role);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Registration failure!");
		}

		String loginPath = getServletContext().getContextPath() + "/index.html";

		response.sendRedirect(loginPath); // reindirizzo l'utente appena registrato alla pagina di login
	}

}

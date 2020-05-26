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
import javax.servlet.http.HttpSession;

import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.ManagerDAO;
import it.polimi.tiw.project.dao.WorkerDAO;

@WebServlet("/ChangePersonalInfo")
public class ChangePersonalInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	public ChangePersonalInfo() {
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
		HttpSession session = request.getSession();
		User user = null;
		String path = getServletContext().getContextPath();

		user = (User) session.getAttribute("user");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		if (user.getRole().equals("manager")) {
			ManagerDAO manDAO = new ManagerDAO(connection, user.getId());

			try {
				manDAO.changeInfo(username, password, email);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
			}
		} else {
			WorkerDAO worDAO = new WorkerDAO(connection, user.getId());
			String experience = request.getParameter("experience");
			File photo = new File(request.getParameter("photo"));
			String finalPhoto = photo.getAbsolutePath();

			try {
				worDAO.changeInfo(username, password, email, experience, finalPhoto);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
			}

		}

		response.sendRedirect(path);
	}

}

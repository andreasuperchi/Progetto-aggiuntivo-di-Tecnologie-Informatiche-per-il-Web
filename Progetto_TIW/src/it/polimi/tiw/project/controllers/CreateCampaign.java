package it.polimi.tiw.project.controllers;

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

@WebServlet("/CreateCampaign")
public class CreateCampaign extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public CreateCampaign() {
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
		User userBean = null;
		HttpSession session = request.getSession(); // salvo la sessione

		userBean = (User) session.getAttribute("user");
		String campaignName = request.getParameter("name");
		String customer = request.getParameter("customer");

		int userID = userBean.getId();
		ManagerDAO manDAO = new ManagerDAO(connection, userID);

		try {
			manDAO.createCampaign(campaignName, customer);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY,
					"There was an error in the creation of the campaign!");
		}

		String path = "/Progetto_TIW/GoToHomeManager";
		response.sendRedirect(path);
	}

	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {

		}
	}
}

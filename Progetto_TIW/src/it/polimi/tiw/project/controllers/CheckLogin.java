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

import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.UserDAO;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
    public CheckLogin() {
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
			throw new UnavailableException("Can't load database driver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");	//mi salvo gli attributi che mi arrivano dalla request
		String password = request.getParameter("password");
		UserDAO userDAO = new UserDAO(connection);
		User userBean = null;
		
		try {
			userBean = userDAO.checkCredentials(username, password);
		} catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Credential checking error!");
		}
		
		String path = getServletContext().getContextPath();	//mi salvo il path
		
		if (userBean == null) {
			path = getServletContext().getContextPath() + "/index.html";	//se la creazione del bean fallisce, reindirizzo alla homepage
		} else {
			request.getSession().setAttribute("user", userBean);	//salvo nella sessione, nel campo user, il bean appena creato
			String target = (userBean.getRole().equals("manager")) ? "/GoToHomeManager" : "/GoToHomeWorker";	//mi salvo il path corrispondente a seconda del tipo di utente che ho
			path = path + target;	//costruisco il path completo
		}
		
		response.sendRedirect(path);	//redirigo l'utente alla corretta homepage
	}
	
	public void destroy() {	//mi permette di chiudere la connessione, se � ancora attiva
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			
		}
	}

}
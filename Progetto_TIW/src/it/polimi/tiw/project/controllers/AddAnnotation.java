package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.ImageDAO;

@WebServlet("/AddAnnotation")
public class AddAnnotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection;
       
    public AddAnnotation() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		String finalDate = formatter.format(date);
		String validity = request.getParameter("validity");
		String trust = request.getParameter("trust");
		String note = request.getParameter("note");
		
		int imageID = Integer.parseInt(request.getParameter("imageID"));
		int cID = Integer.parseInt(request.getParameter("cID"));
		
		ImageDAO imageDAO = new ImageDAO(connection, imageID);
			
		try {
			imageDAO.addAnnotation(finalDate, validity, trust, note, imageID, user.getId());
		}
		catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Loading Error!");
		}
		
		String path = "/Progetto_TIW/GoToImageDetailsPage?id=" + imageID + "&idCampaign=" + cID;
		response.sendRedirect(path);
	}

}

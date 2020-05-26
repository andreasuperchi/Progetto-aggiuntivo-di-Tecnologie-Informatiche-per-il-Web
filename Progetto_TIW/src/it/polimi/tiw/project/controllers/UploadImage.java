package it.polimi.tiw.project.controllers;

import java.io.File;
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

import it.polimi.tiw.project.dao.CampaignDAO;

@WebServlet("/UploadImage")
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	public UploadImage() {
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
		int campaignId = Integer.parseInt(request.getParameter("id"));

		CampaignDAO cDAO = new CampaignDAO(connection, campaignId);

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		File photo = new File(request.getParameter("photo"));
		String photoPath = photo.getAbsolutePath();

		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String county = request.getParameter("county");
		String municipality = request.getParameter("municipality");
		String source = request.getParameter("source");
		String finalDate = formatter.format(date);
		String resolution = request.getParameter("resolution");

		try {
			cDAO.addImage(photoPath, latitude, longitude, county, municipality, source, finalDate, resolution);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
		}

		String path = "/Progetto_TIW/GoToCampaignDetailsPage?id=" + campaignId;
		response.sendRedirect(path);
	}

}

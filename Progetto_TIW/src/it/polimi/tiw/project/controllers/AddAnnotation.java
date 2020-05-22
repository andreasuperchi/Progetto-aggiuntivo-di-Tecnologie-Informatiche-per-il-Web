package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Connection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.project.dao.ImageDAO;

@WebServlet("/AddAnnotation")
public class AddAnnotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAnnotation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		String finalDate = formatter.format(date);
		String validity = request.getParameter("validity");
		String trust = request.getParameter("trust");
		String note = request.getParameter("note");
		
		int imageID = Integer.parseInt(request.getParameter("imageID"));
		
		ImageDAO imageDAO = new ImageDAO(connection, imageID);
		
		try {
			imageDAO.addAnnotation(finalDate, validity, trust, note, imageID);
		}
		catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Loading Error!");
		}
		
		String path = "/WEB-INF/ImageDetailsPage";
		response.sendRedirect(path);
	}

}

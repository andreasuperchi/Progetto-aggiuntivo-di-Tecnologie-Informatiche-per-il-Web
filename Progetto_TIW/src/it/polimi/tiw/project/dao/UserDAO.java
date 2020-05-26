package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.project.beans.Campaign;
import it.polimi.tiw.project.beans.User;

public class UserDAO {
	private Connection connection;

	public UserDAO(Connection connection) {
		this.connection = connection;
	}

	public User checkCredentials(String username, String password) throws SQLException {
		String query = "SELECT id, username, role FROM user WHERE username = ? AND password = ?";

		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setString(1, username);
			pstatement.setString(2, password);

			try (ResultSet result = pstatement.executeQuery();) {
				if (!result.isBeforeFirst()) { // corrispondenza non trovata
					return null;
				} else {
					result.next();
					User user = new User();
					user.setId(result.getInt("id"));
					user.setUsername(result.getString("username"));
					user.setRole(result.getString("role"));
					return user;
				}
			}
		}
	}

	public void addUser(String username, String password, String email, String experience, String photo, String role)
			throws SQLException {
		if (role.equals("manager")) {
			String query = "INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)";

			try (PreparedStatement pstatement = connection.prepareStatement(query);) {
				pstatement.setString(1, username);
				pstatement.setString(2, password);
				pstatement.setString(3, email);
				pstatement.setString(4, role);

				pstatement.executeUpdate();
			}
		} else {
			String query = "INSERT INTO user (username, password, email, experience, photo, role) VALUES (?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstatement = connection.prepareStatement(query);) {
				pstatement.setString(1, username);
				pstatement.setString(2, password);
				pstatement.setString(3, email);
				pstatement.setString(4, experience);
				pstatement.setString(5, photo);
				pstatement.setString(6, role);

				pstatement.executeUpdate();
			}
		}
	}

	public Campaign getCampaign(int id) throws SQLException {
		String query = "SELECT * FROM campaign WHERE id = ?";
		Campaign campaign = new Campaign();

		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id);

			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					campaign.setId(result.getInt("id"));
					campaign.setName(result.getString("name"));
					campaign.setState(result.getString("state"));
					campaign.setCustomer(result.getString("customer"));
					campaign.setIdManager(result.getInt("id_manager"));
				}
			}
		}

		return campaign;
	}

}
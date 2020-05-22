package it.polimi.tiw.project.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.project.beans.Campaign;

public class WorkerDAO {
	private int id;
	private Connection connection;

	public WorkerDAO(Connection connection, int id) {
		this.connection = connection;
		this.id = id;
	}

	public List<Campaign> findSubmittedCampaigns() throws SQLException {
		List<Campaign> submittedCampaigns = new ArrayList<Campaign>();
		String query = "SELECT id, name, state, customer, id_manager FROM campaign AS c JOIN worker_campaign AS wc WHERE wc.id_worker = ? AND wc.id_campaign = c.id ";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, this.id);
			
			try (ResultSet result = pstatement.executeQuery()) {
				while (result.next()) {
					Campaign campaign = new Campaign();
					
					campaign.setId(result.getInt("id"));
					campaign.setName(result.getString("name"));
					campaign.setState(result.getString("state"));
					campaign.setCustomer(result.getString("customer"));
					campaign.setIdManager(result.getInt("id_manager"));
					
					submittedCampaigns.add(campaign);
				}
			}
		}
		return submittedCampaigns;
	}
	

	public List<Campaign> findAvailableCampaigns() throws SQLException {
		List<Campaign> submittedCampaigns = new ArrayList<Campaign>();
		String query = "select * from campaign where state = 'started' and id not in (select id_campaign from worker_campaign where id_worker = ?)";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, this.id);
			
			try (ResultSet result = pstatement.executeQuery()) {
				while (result.next()) {
					Campaign campaign = new Campaign();
					
					campaign.setId(result.getInt("id"));
					campaign.setName(result.getString("name"));
					campaign.setState(result.getString("state"));
					campaign.setCustomer(result.getString("customer"));
					campaign.setIdManager(result.getInt("id_manager"));
					
					submittedCampaigns.add(campaign);
				}
			}
		}
		return submittedCampaigns;
	}	
	



	public void submitCampaign(int id_campaign) throws SQLException {
		String query = "INSERT INTO worker_campaign (id_worker, id_campaign) VALUES (?, ?)";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, id);
			pstatement.setInt(2, id_campaign);
			pstatement.executeUpdate();
		}
	}

	public void changeUsername(String username) throws SQLException {
		String query = "UPDATE user SET username = ? WHERE id = ?";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setString(1, username);
			pstatement.setInt(2, id);
			
			pstatement.executeUpdate();
		}
	}

	public void changePassword(String password) throws SQLException {
		String query = "UPDATE user SET password = ? where id =?";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setString(1, password);
			pstatement.setInt(2, id);
			
			pstatement.executeUpdate();
		}
	}

	public void changeEmail(String email) throws SQLException {
		String query = "UPDATE user SET email = ? where id =?";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setString(1, email);
			pstatement.setInt(2, id);
			
			pstatement.executeUpdate();
		}
	}

	public void changeExperience(String experience) throws SQLException {
		String query = "UPDATE user SET experience = ? where id =?";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setString(1, experience);
			pstatement.setInt(2, id);
			
			pstatement.executeUpdate();
		}
	}

	public void changePhoto(Blob photo) throws SQLException {
		String query = "UPDATE user SET photo = ? where id =?";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setBlob(1, photo);
			pstatement.setInt(2, id);
			
			pstatement.executeUpdate();
		}
	}

}

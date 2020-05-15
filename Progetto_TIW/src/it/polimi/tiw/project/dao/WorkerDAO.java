package it.polimi.tiw.project.dao;

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
	
	public List<Campaign> findSubmittedCampaign() throws SQLException{
		List <Campaign> submittedCampaigns = new ArrayList <Campaign>();
		String query = "SELECT id FROM worker_campaign WHERE id_worker  = ? ORDERED BY name ASC ";
		try (PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setInt(1, this.id);
			try (ResultSet result = pstatement.executeQuery()){
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
	
	
	
	public List<Campaign> findAvailableCampaign() throws SQLException{
		List <Campaign> availableCampaigns = new ArrayList <Campaign>();
		String query = "SELECT id FROM worker_campaign WHERE id_worker  != ? ORDERED BY name ASC ";
		try (PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setInt(1, this.id);
			try (ResultSet result = pstatement.executeQuery()){
				while (result.next()) {
					Campaign campaign = new Campaign();
					campaign.setId(result.getInt("id"));
					campaign.setName(result.getString("name"));
					campaign.setState(result.getString("state"));
					campaign.setCustomer(result.getString("customer"));
					campaign.setIdManager(result.getInt("id_manager"));
					availableCampaigns.add(campaign);
				}
			}
		}
		return availableCampaigns;
	}
	
	
	public void submitCampaign(int id_campaign) throws SQLException {
		String query = "INSERT into worker_campaign (id_worker, id_campaign) VALUES (?,?)";
		try (PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setInt(1, id);
			pstatement.setInt(2, id_campaign);
			pstatement.executeUpdate();
		}
	}
	
	
	
}
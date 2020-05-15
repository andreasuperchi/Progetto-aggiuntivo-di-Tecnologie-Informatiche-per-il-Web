package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import it.polimi.tiw.project.beans.Campaign;

public class ManagerDAO {
	private Connection connection;
	private int id;
	
	public ManagerDAO(Connection connection, int id) {
		this.connection = connection;
		this.id = id;
	}
	
	public List<Campaign> findCampaigns() throws SQLException {
		List<Campaign> campaigns = new ArrayList<Campaign>();
		String query = "SELECT id, name FROM campaign WHERE id_manager = ? ORDER BY name ASC";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {	//preparo una query SQL precompilata
			pstatement.setInt(1, this.id);	//setto il mio ID come parametro
			
			try (ResultSet result = pstatement.executeQuery();) {	//provo a eseguire la query verso il db
				while(result.next()) {	//scorro le varie righe che mi restituisce la query
					Campaign campaign = new Campaign();	//uso il costruttore di default
					
					//prendo i vari campi del risultato della query e li salvo in un bean di tipo Campaign
					campaign.setId(result.getInt("id"));
					campaign.setName(result.getString("name"));
					campaign.setState(result.getString("state"));
					campaign.setCustomer(result.getString("customer"));
					campaign.setIdManager(result.getInt("id_manager"));
					
					campaigns.add(campaign);
				}
			}
		}
		
		return campaigns;
	}
	
	public void createCampaign(String campaignName, String customer) throws SQLException {
		String query = "INSERT INTO campaign (name, state, customer, id_manager) VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setString(1, campaignName);
			pstatement.setString(2, "created");
			pstatement.setString(3, customer);
			pstatement.setInt(4, this.id);
			
			pstatement.executeUpdate();	//eseguo l'inserimento della campagna nel db
		}
	}
}

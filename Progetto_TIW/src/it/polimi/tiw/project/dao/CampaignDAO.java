package it.polimi.tiw.project.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.project.beans.CampaignStats;
import it.polimi.tiw.project.beans.Image;

public class CampaignDAO {
	int id;
	Connection connection;
	
	
	public void changeToActive() throws SQLException {
		String query = "UPDATE campaign SET state='active' WHERE id = ? ";
		try (PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setInt(1,id);
			pstatement.executeQuery();
		}
	}
	
	
	public void changeToClose() throws SQLException {
		String query = "UPDATE campaign SET state= 'close' WHERE id = ? ";
		try (PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setInt(1,id);
			pstatement.executeQuery();
		}
	}
	
	public List<Image> campaignImages()throws SQLException{
		List<Image> campaignImages = new ArrayList<Image>();
		String query = "SELECT * FROM image WHERE id_campaign= ? ORDERED BY name ASC";
		
		try(PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setInt(1, id);
			
			try (ResultSet result = pstatement.executeQuery()){
				while (result.next()) {
					Image image = new Image();
					image.setId(result.getInt("id"));
					image.setPhoto(result.getBlob("photo"));
					image.setLatitude(result.getFloat("latitude"));
					image.setLongitude(result.getDouble("longitude"));
					image.setCounty(result.getString("country"));
					image.setMunicipality(result.getString("municipality"));
					image.setSource(result.getString("source"));
					image.setDate(result.getDate("date"));
					image.setResolution(result.getString("resolution"));
					image.setIdCampaign(result.getInt("id_campaign"));
					campaignImages.add(image);
					
				}
			}
		}
		return campaignImages;
	}
	
	
	
	public void addImage(Blob photo, double latitude, double longitude, String country, String municipality, String source, Date date, String resolution) throws SQLException {
		String query = "INSERT INTO image (photo, latitude, longitude, country, municipality, source, date, resolution, id_campaign) VALUES (?,?,?,?,?,?,?,?,?) ";
		
		try(PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setBlob(1,photo);
			pstatement.setDouble(2, latitude);
			pstatement.setDouble(3, longitude);
			pstatement.setString(4, country);
			pstatement.setString(5, municipality);
			pstatement.setString(6, source);
			pstatement.setDate(7, date);
			pstatement.setString(8, resolution);
			pstatement.setInt(9, id);
			
			pstatement.executeQuery();
		}
	}
	
	
	public CampaignStats createStats() throws SQLException {
		CampaignStats stats = new CampaignStats();
		String query = "SELECT COUNT(*) FROM annotation WHERE id_image = ?";
		List <Image> images = new ArrayList<Image>();
		int numberOfAnnotations = 0;
		int conflictual = 0;
		
		images = campaignImages();
		stats.setNumberOfImages(images.size());
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)){
			for(int i=0; i<images.size();i++){
				pstatement.setInt(1, images.get(i).getId());
				
				try (ResultSet result = pstatement.executeQuery()){
					numberOfAnnotations += result.getInt("COUNT(*)");
				}
				
				query = "SELECT DISTINCT COUNT(validity) FROM annotation WHERE id_image = ?";
				try (PreparedStatement pstatement1 = connection.prepareStatement(query)){
					pstatement1.setInt(1, images.get(i).getId());
					
					try (ResultSet result1 = pstatement1.executeQuery()){
						conflictual += result1.getInt("COUNT(validity)")-1;
					}
					
				}				
			}
			
			stats.setNumberOfAnnotations(numberOfAnnotations);
			stats.setAverageAnnotationsPerImages(numberOfAnnotations/images.size());
			stats.setConflictalAnnotation(conflictual);
			
		}
		return stats;
	}
	
	
	
}
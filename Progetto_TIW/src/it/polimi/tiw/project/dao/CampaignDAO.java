package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.project.beans.CampaignStats;
import it.polimi.tiw.project.beans.Image;

public class CampaignDAO {
	int id;
	private Connection connection;

	public CampaignDAO(Connection connection, int id) {
		this.connection = connection;
		this.id = id;
	}

	public void changeToActive() throws SQLException {
		String query = "UPDATE campaign SET state = 'started' WHERE id = ?";

		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, id);

			pstatement.executeUpdate();
		}
	}

	public void changeToClosed() throws SQLException {
		String query = "UPDATE campaign SET state = 'closed' WHERE id = ? ";

		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, id);

			pstatement.executeUpdate();
		}
	}

	public List<Image> campaignImages() throws SQLException {
		List<Image> campaignImages = new ArrayList<Image>();
		String query = "SELECT * FROM image WHERE id_campaign = ?";

		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, this.id);

			try (ResultSet result = pstatement.executeQuery()) {
				while (result.next()) {
					Image image = new Image();

					image.setId(result.getInt("id"));
					image.setPhoto(result.getString("photo"));
					image.setLatitude(result.getFloat("latitude"));
					image.setLongitude(result.getDouble("longitude"));
					image.setCounty(result.getString("county"));
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

	public void addImage(String photo, String latitude, String longitude, String county, String municipality,
			String source, String date, String resolution) throws SQLException {
		String query = "INSERT INTO image (photo, latitude, longitude, county, municipality, source, date, resolution, id_campaign) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setString(1, photo);
			pstatement.setString(2, latitude);
			pstatement.setString(3, longitude);
			pstatement.setString(4, county);
			pstatement.setString(5, municipality);
			pstatement.setString(6, source);
			pstatement.setString(7, date);
			pstatement.setString(8, resolution);
			pstatement.setInt(9, this.id);

			pstatement.executeUpdate();
		}
	}

	public CampaignStats createStats() throws SQLException {
		CampaignStats stats = new CampaignStats();
		String query = "SELECT COUNT(*) FROM annotation WHERE id_image = ?";
		List<Image> images = new ArrayList<Image>();
		int numberOfAnnotations = 0;
		float avgAnnotations = 0;
		int conflictual = 0;

		images = campaignImages();
		stats.setNumberOfImages(images.size());

		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			for (int i = 0; i < images.size(); i++) {
				pstatement.setInt(1, images.get(i).getId());

				try (ResultSet result = pstatement.executeQuery();) {
					while (result.next()) {
						numberOfAnnotations += result.getInt("COUNT(*)");
					}
				}

				query = "SELECT COUNT(DISTINCT validity) FROM annotation WHERE id_image = ?";
				try (PreparedStatement pstatement1 = connection.prepareStatement(query);) {
					pstatement1.setInt(1, images.get(i).getId());

					try (ResultSet result1 = pstatement1.executeQuery()) {
						while (result1.next()) {
							if (result1.getInt("COUNT(DISTINCT validity)") == 2) {
								conflictual++;
							}
						}
					}

				}
			}
			
			if (images.size() == 0) {
				avgAnnotations = 0;
			} else {
				avgAnnotations = (float) numberOfAnnotations / images.size();
			}
			
			stats.setNumberOfAnnotations(numberOfAnnotations);
			stats.setAverageAnnotationsPerImage(avgAnnotations);
			stats.setConflictualAnnotations(conflictual);

		}
		return stats;
	}

	public Image getImage(int imageID) throws SQLException {
		Image image = new Image();
		String query = "SELECT * FROM image WHERE id = ? AND id_campaign = ?";

		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, imageID);
			pstatement.setInt(2, this.id);

			try (ResultSet result = pstatement.executeQuery()) {
				while (result.next()) {
					image.setId(result.getInt("id"));
					image.setPhoto(result.getString("photo"));
					image.setLatitude(result.getFloat("latitude"));
					image.setLongitude(result.getDouble("longitude"));
					image.setCounty(result.getString("county"));
					image.setMunicipality(result.getString("municipality"));
					image.setSource(result.getString("source"));
					image.setDate(result.getDate("date"));
					image.setResolution(result.getString("resolution"));
					image.setIdCampaign(result.getInt("id_campaign"));
				}
			}

		}

		return image;
	}
}

package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.project.beans.Annotation;;

public class ImageDAO {
	private Connection connection;
	private int id;

	public ImageDAO(Connection connection, int id) {
		this.connection = connection;
		this.id = id;
	}

	public void addAnnotation(Date creationDate, String validity, String trust, String note, int id_worker) {
		String query = "INSERT INTO annotation (creation_date, validity, trust, note, id_image, id_worker) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setDate(1, creationDate);
			pstatement.setString(2, validity);
			pstatement.setString(3, trust);
			pstatement.setString(4, note);
			pstatement.setInt(5, this.id);
			pstatement.setInt(6, id_worker);
			
			pstatement.executeUpdate();
		}
	}

	public List<Annotation> findAnnotations() throws SQLException {	//trova le annotazioni relative a una certa immagine
		List<Annotation> annotations = new ArrayList<Annotation>();
		String query = "SELECT * FROM annotation WHERE id_image = ?";

		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, this.id);

			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Annotation annotation = new Annotation();

					annotation.setId(result.getInt("id"));
					annotation.setCreationDate(result.getDate("creation_date"));
					annotation.setValidity(result.getString("validity"));
					annotation.setTrust(result.getString("trust"));
					annotation.setNote(result.getString("note"));
					annotation.setIdImage(result.getInt("id_image"));

					annotations.add(annotation);
				}
			}
		}
		
		return annotations;
	}
}

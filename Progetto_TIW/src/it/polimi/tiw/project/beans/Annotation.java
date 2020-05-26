package it.polimi.tiw.project.beans;

public class Annotation {
	private int id;
	private String creationDate;
	private String validity;
	private String trust;
	private String note;
	private int idImage;
	private int idWorker;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getTrust() {
		return trust;
	}

	public void setTrust(String trust) {
		this.trust = trust;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getIdImage() {
		return idImage;
	}

	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}

	public int getIdWorker() {
		return idWorker;
	}

	public void setIdWorker(int idWorker) {
		this.idWorker = idWorker;
	}

}

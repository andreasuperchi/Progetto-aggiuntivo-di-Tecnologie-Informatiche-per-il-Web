package it.polimi.tiw.project.beans;

public class CampaignStats {
	private int numberOfImages;
	private int numberOfAnnotations;
	private float averageAnnotationsPerImage;
	private int conflictualAnnotations;

	public int getConflictualAnnotations() {
		return conflictualAnnotations;
	}

	public void setConflictualAnnotations(int conflictalAnnotations) {
		this.conflictualAnnotations = conflictalAnnotations;
	}

	public int getNumberOfImages() {
		return numberOfImages;
	}

	public void setNumberOfImages(int numberOfImages) {
		this.numberOfImages = numberOfImages;
	}

	public int getNumberOfAnnotations() {
		return numberOfAnnotations;
	}

	public void setNumberOfAnnotations(int numberOfAnnotations) {
		this.numberOfAnnotations = numberOfAnnotations;
	}

	public float getAverageAnnotationsPerImage() {
		return averageAnnotationsPerImage;
	}

	public void setAverageAnnotationsPerImage(float averageAnnotationsPerImage) {
		this.averageAnnotationsPerImage = averageAnnotationsPerImage;
	}
}

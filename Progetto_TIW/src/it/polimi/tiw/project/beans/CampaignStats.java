package it.polimi.tiw.project.beans;

public class CampaignStats {
	private int numberOfImages;
	private int numberOfAnnotations;
	private float averageAnnotationsPerImage;
	
	
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
	public float getAverageAnnotationsPerImages() {
		return averageAnnotationsPerImage;
	}
	public void setAverageAnnotationsPerImages(float averageAnnotationsPerImages) {
		this.averageAnnotationsPerImage = averageAnnotationsPerImages;
	}
	
	
}

package sample;

import java.util.concurrent.locks.Condition;

public class Property {

	private String propertyNumber;
	private String propertyType;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private int stories;
	private int yearBuilt;
	private int bedrooms;
	private int bathrooms;
	private String condition;
	private String saleStatus;
	private double marketValue;
	private String pictureFile;

	public Property() {}

	public Property(String propertyNumber, String city, int stories,
									int yearBuilt, int bedrooms, int bathrooms) {
		this.propertyNumber = propertyNumber;
		this.city = city;
		this.stories = stories;
		this.yearBuilt = yearBuilt;
		this.bedrooms = bedrooms;
		this.bathrooms = bathrooms;
	}

	public Property(String propertyNumber, String city, int stories,
									int yearBuilt, int bedrooms, int bathrooms,
									String condition, String saleStatus, Double marketValue) {
		this.propertyNumber = propertyNumber;
		this.city = city;
		this.stories = stories;
		this.yearBuilt = yearBuilt;
		this.bedrooms = bedrooms;
		this.bathrooms = bathrooms;
		this.condition = condition;
		this.saleStatus = saleStatus;
		this.marketValue = marketValue;
	}

	public String getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public int getStories() {
		return stories;
	}

	public void setStories(int stories) {
		this.stories = stories;
	}

	public int getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}

	public int getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}

	public int getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

		public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public String getPictureFile() {
		return pictureFile;
	}

	public void setPictureFile(String pictureFile) {
		this.pictureFile = pictureFile;
	}
}

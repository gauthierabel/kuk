package mobiledev.heia.ch.kuk.data.database;

public class Category {
	// Data members/fields
	// each data member/field represents a field in each piece of data
	// received from the web API
	// choose the appropriate modifiers for each field
	private String categoryID;
	private String categoryName;

	// Media resources
	private String categoryThumbnail;

	// Description
	private String categoryDescription;

	// Constructor
	// each data field must be initialized correctly upon construction
	// based on data received from the web API
	public Category(
			String categoryID, String categoryName, String categoryThumbnail,
			String categoryDescription) {
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.categoryThumbnail = categoryThumbnail;
		this.categoryDescription = categoryDescription;
	}

	// Accessor methods used for accessing specific data fields
	@SuppressWarnings("unused")
	public String getCategoryID() {
		return categoryID;
	}

	@SuppressWarnings("unused")
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	@SuppressWarnings("unused")
	public String getCategoryName() {
		return categoryName;
	}

	@SuppressWarnings("unused")
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@SuppressWarnings("unused")
	public String getCategoryThumbnail() {
		return categoryThumbnail;
	}

	@SuppressWarnings("unused")
	public void setCategoryThumbnail(String categoryThumbnail) {
		this.categoryThumbnail = categoryThumbnail;
	}

	@SuppressWarnings("unused")
	public String getCategoryDescription() {
		return categoryDescription;
	}

	@SuppressWarnings("unused")
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
}

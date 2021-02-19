package mobiledev.heia.ch.kuk.data.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mobiledev.heia.ch.kuk.data.database.Category;
import mobiledev.heia.ch.kuk.data.database.Entry;
import mobiledev.heia.ch.kuk.data.database.Query;

class JsonParser {
	// Method called for parsing the response
	// ResponseStr is the JSON response stored in a String
	public static Response parse(Query.Action action, final String responseStr) throws JSONException {
		// First get a JSON object from the received string
		JSONObject input = new JSONObject(responseStr);
		// Then get all entries from the jsonObject
		Response rp;
		if (action == Query.Action.CATEGORIES) {
			rp = new Response(categoriesFromJson(input));
		} else rp = new Response(entriesFromJson(input));
		// Return the response that contains all entries
		return rp;
	}

	// This method receives the JSON object representing the response received from the web API
	private static Entry[] entriesFromJson(final JSONObject json) throws JSONException {
		// Get the JSON array representing all entries (e.g. one entry represents one weather forecast
		// Entry among all weather forecasts)
		JSONArray jsonArray = json.getJSONArray("meals");

		// Allocate the array for all news entries
		Entry[] entries = new Entry[jsonArray.length()];
		// Get all entries from the JSON array
		for (int i = 0; i < jsonArray.length(); i++) {
			// Get the JSON object representing the specific entry
			JSONObject oneObject = jsonArray.getJSONObject(i);
			// From this JSON object get the entry
			entries[i] = entryFromJson(oneObject);
		}
		// Return the array of entries
		return entries;
	}

	// This method returns one entry instance for a specific JSON object
	private static Entry entryFromJson(final JSONObject jsonEntry) throws JSONException {
		int recipeID = Integer.parseInt(jsonEntry.getString("idMeal"));
		String recipeName = jsonEntry.getString("strMeal");

		// Description
		String recipeCategory = jsonEntry.getString("strCategory");
		String recipeArea = jsonEntry.getString("strArea");
		String[] recipeTags = jsonEntry.getString("strTags").split(",");

		// Media resources
		String recipeThumbnail = jsonEntry.getString("strMealThumb");
		String recipeYouTube = jsonEntry.getString("strYoutube");

		// Recipe contents
		String recipeInstructions = jsonEntry.getString("strInstructions");
		// To receive the 20 API inputs related to ingredients and measurement
		String[] recipeIngredients = new String[20];
		String[] recipeMeasure = new String[20];
		for (int i = 0; i < 20; i++) {
			recipeIngredients[i] = jsonEntry.getString("strIngredient" + (i + 1));
			recipeMeasure[i] = jsonEntry.getString("strMeasure" + (i + 1));
		}
		// Return one entry instance
		return new Entry(recipeID, recipeName, recipeCategory, recipeArea, recipeTags, recipeThumbnail,
				recipeYouTube, recipeInstructions, recipeIngredients, recipeMeasure, null);
	}

	// This method receives the JSON object representing the response received from the web API
	private static Category[] categoriesFromJson(final JSONObject json) throws JSONException {
		// Get the JSON array representing all meal categories
		JSONArray jsonArray = json.getJSONArray("categories");
		// Allocate the array for all news categories
		Category[] categories = new Category[jsonArray.length()];
		// Get all category from the JSON array
		for (int i = 0; i < jsonArray.length(); i++) {
			// Get the JSON object representing the specific category
			JSONObject oneObject = jsonArray.getJSONObject(i);
			// From this JSON object get the category
			categories[i] = categoryFromJson(oneObject);
		}
		// Return the array of categories
		return categories;
	}

	// This method returns one category instance for a specific JSON object
	private static Category categoryFromJson(final JSONObject jsonCategory) throws JSONException {
		String categoryID = jsonCategory.getString("idCategory");
		String categoryName = jsonCategory.getString("strCategory");

		// Media resources
		String categoryThumbnail = jsonCategory.getString("strCategoryThumb");

		// Description
		String categoryDescription = jsonCategory.getString("strCategoryDescription");

		return new Category(categoryID, categoryName, categoryThumbnail, categoryDescription);
	}
}

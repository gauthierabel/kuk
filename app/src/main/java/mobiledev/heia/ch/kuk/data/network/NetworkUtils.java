package mobiledev.heia.ch.kuk.data.network;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import mobiledev.heia.ch.kuk.data.database.Query;

class NetworkUtils {
	// Constant strings that are URL related
	private static final String RANDOM_PATH = "random.php";     // Random Recipe Object, no param
	private static final String LIST_PATH = "categories.php";   // Category List, no param
	private static final String NAME_PATH = "search.php";
	// Search by Name, string param for search string
	private static final String ID_PATH = "lookup.php";
	// Requested Recipe Object, string param for recipe ID
	private static final String FILTER_PATH = "filter.php";
	// Recipes in Category List, string param for cat ID

	// Retrieves the proper URL to query the web API
	static URL getUrl(Query.Action action, String param) {
		return buildUrlWithQueryParameters(action, param);
	}

	// Build the url for specific query parameters
	@SuppressWarnings("DuplicateBranchesInSwitch")
	private static URL buildUrlWithQueryParameters(Query.Action action, String queryString) {
		String actionPath;   // default path

		//Select the correct action path
		switch (action) {
			case RANDOM:
				actionPath = RANDOM_PATH;
				break;
			case CATEGORIES:
				actionPath = LIST_PATH;
				break;
			case SEARCH:
				actionPath = NAME_PATH;
				break;
			case ID:
				actionPath = ID_PATH;
				break;
			case CAT_RECIPES:
				actionPath = FILTER_PATH;
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + action);
		}

		try {
			// Build the Uri
			Uri.Builder builder = new Uri.Builder();
			builder
					.scheme("https")
					.authority("www.themealdb.com")
					.appendPath("api")
					.appendPath("json")
					.appendPath("v1")
					.appendPath("1")    // API key
					.appendPath(actionPath);

			//Add query paramater
			switch (action) {
				case RANDOM:
					break;
				case CATEGORIES:
					break;
				case SEARCH:
					builder.appendQueryParameter("s", queryString);
					break;
				case ID:
					builder.appendQueryParameter("i", queryString);
					break;
				case CAT_RECIPES:
					builder.appendQueryParameter("c", queryString);
					break;
			}
			// Build the URL instance based on the Uri instance
			// and return it to the caller
			return new URL(builder.build().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Called for getting the response from a specific URL
	static String getResponseFromHttpUrl(URL url) throws IOException {
		// HttpURLConnection instance for getting the response from the URL
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		try {
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Accept", "application/json");
			InputStream in = urlConnection.getInputStream();

			Scanner scanner = new Scanner(in);
			scanner.useDelimiter("\\A");

			boolean hasInput = scanner.hasNext();
			String response = null;
			if (hasInput) {
				response = scanner.next();
			}
			scanner.close();
			return response;
		} finally {
			urlConnection.disconnect();
		}
	}
}

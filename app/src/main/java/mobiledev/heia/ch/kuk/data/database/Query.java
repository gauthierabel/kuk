package mobiledev.heia.ch.kuk.data.database;

import java.io.Serializable;

/* This class represents an object containing all the info needed to perform a database query to
 * the API and that can be passed through a bundle from a fragment to another, which is used for
 * the RecipeList fragment.
 */

public class Query implements Serializable {
	// Action available for api requests
	public enum Action {
		SEARCH,
		RANDOM,
		CATEGORIES,
		ID,
		CAT_RECIPES,
		RECENT
	}

	// Data member
	private Action action;
	private String param;

	public Query(Action action, String param) {
		this.action = action;
		this.param = param;
	}

	// Accessor methods used for accessing specific data members
	public Action getAction() {
		return action;
	}

	@SuppressWarnings("unused")
	public void setAction(Action action) {
		this.action = action;
	}

	public String getParam() {
		return param;
	}

	@SuppressWarnings("unused")
	public void setParam(String param) {
		this.param = param;
	}
}

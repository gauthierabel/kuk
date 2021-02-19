package mobiledev.heia.ch.kuk.data.network;

import androidx.annotation.NonNull;

import java.util.Objects;

import mobiledev.heia.ch.kuk.data.database.Category;
import mobiledev.heia.ch.kuk.data.database.Entry;

public class Response {
	// Data members (array of entries or categories)
	private final Entry[] mEntries;
	private final Category[] mCategories;

	// Constructor for entries
	public Response(@NonNull final Entry[] entries) {
		mEntries = entries;
		mCategories = null;
	}

	// Constructor for categories
	public Response(@NonNull final Category[] categories) {
		mCategories = categories;
		mEntries = null;
	}

	// Accessor methods used for accessing all entries or a specific Entry

	public Entry[] getEntries() {
		return mEntries;
	}

	public Entry getEntry(int index) {
		return Objects.requireNonNull(mEntries)[index];
	}

	// Accessor methods used for accessing all categories or a specific Category
	@SuppressWarnings("unused")
	public Category[] getCategories() {
		return mCategories;
	}

	@SuppressWarnings("unused")
	public Category getCategory(int index) {
		return Objects.requireNonNull(mCategories)[index];
	}
}

package mobiledev.heia.ch.kuk.data.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EntryRepository {
	// Data member
	private final EntryDao mEntryDao;
	private final LiveData<List<Entry>> mAllEntries;

	public EntryRepository(Application application, boolean isInsertOnly) {
		EntryRoomDatabase db = EntryRoomDatabase.getDatabase(application);
		mEntryDao = db.entryDao();
		// If the fragment only inserts data into the repo, fetching the existing data is useless
		mAllEntries = !isInsertOnly ? mEntryDao.getAllEntries() : null;
	}

	// For observation
	public LiveData<List<Entry>> getAllEntries() {
		return mAllEntries;
	}

	// Wrapper method for inserting a name
	public void insert(Entry entry) {
		new insertAsyncTask(mEntryDao).execute(entry);
	}

	// Internal class for inserting a new Entry into the database
	private static class insertAsyncTask extends AsyncTask<Entry, Void, Void> {
		private final EntryDao mAsyncTaskDao;

		insertAsyncTask(EntryDao dao) {
			mAsyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final Entry... params) {
			mAsyncTaskDao.insert(params[0]);
			return null;
		}
	}
}

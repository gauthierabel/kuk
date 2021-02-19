package mobiledev.heia.ch.kuk.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@SuppressWarnings("unused")
@Dao
public interface EntryDao {
	// Insert one entry in the class
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(Entry entry);

	// Delete all entries in the class
	@Query("DELETE FROM recently_viewed")
	void deleteAll();

	// Get all entries order by uid (recently opened)
	@Query("SELECT * FROM recently_viewed ORDER BY uid")
	LiveData<List<Entry>> getAllEntries();
}

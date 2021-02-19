package mobiledev.heia.ch.kuk.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Entry.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class EntryRoomDatabase extends RoomDatabase {
	private static EntryRoomDatabase INSTANCE;
	private static final String DATABASE_NAME = "recipe_database";

	public abstract EntryDao entryDao();

	// Factory (delivers a singleton to the DB)
	public static EntryRoomDatabase getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (EntryRoomDatabase.class) {
				if (INSTANCE == null) {
					// Create database
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							EntryRoomDatabase.class, EntryRoomDatabase.DATABASE_NAME).build();
				}
			}
		}
		return INSTANCE;
	}
}

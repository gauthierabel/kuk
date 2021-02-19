package mobiledev.heia.ch.kuk.data.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

// Converts types not compatible with the database to compatible types.
// Calling these methods is done automatically by Room when calling or saving an unsupported type.
class Converters {
	@TypeConverter
	public static String fromArray(String[] strings) {
		StringBuilder string = new StringBuilder();

		for (String s : strings)
			string.append(s).append(",");

		return string.toString();
	}

	@TypeConverter
	public static String[] toArray(String string) {
		return string.split(",");
	}

	@TypeConverter
	public static byte[] toByteArray(Bitmap bmp) {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		if (bmp != null)
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bs);
		return bs.toByteArray();
	}

	@TypeConverter
	public static Bitmap fromByteArray(byte[] bytes) {
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
}

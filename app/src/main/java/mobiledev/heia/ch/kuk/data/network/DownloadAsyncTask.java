package mobiledev.heia.ch.kuk.data.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	// To observe the results in the owner of the instance of DownloadAsyncTask
	private final MutableLiveData<Bitmap> mBitmap = new MutableLiveData<>();

	// To observe the results by the viewModel
	public LiveData<Bitmap> getBitmap() {
		return mBitmap;
	}

	public DownloadAsyncTask() {
	}

	@Override
	protected Bitmap doInBackground(@NonNull String... strings) {
		return downloadImage(strings[0]);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		mBitmap.postValue(bitmap);
	}

	// To download the image
	private static Bitmap downloadImage(@SuppressWarnings("SameParameterValue") String urlStr) {
		InputStream inputStream = null;
		Bitmap bitmap = null;
		try {
			// Open the url connection
			URL url = new URL(urlStr);
			URLConnection urlConnection = url.openConnection();
			inputStream = urlConnection.getInputStream();

			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}
}

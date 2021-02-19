package mobiledev.heia.ch.kuk.data.network;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mobiledev.heia.ch.kuk.data.database.Query;

public class FetchAsyncTask extends AsyncTask<Void, Void, Response> {
	// Data members
	private final String mRequParam;
	private final Query.Action mAction;

	// For observation of the results by the owner of the instance of FetchAsyncTask
	private final MutableLiveData<Response> mResponse = new MutableLiveData<>();

	public FetchAsyncTask(Query query) {
		super();
		this.mRequParam = query.getParam();
		this.mAction = query.getAction();
	}

	// For observation by the ViewModel
	public LiveData<Response> getResponse() {
		return mResponse;
	}

	@Override
	protected Response doInBackground(Void... voids) {
		try {
			// Get the url for the action and its parameter
			URL url = NetworkUtils.getUrl(this.mAction, this.mRequParam);
			// Get the answer to the query
			String output = NetworkUtils.getResponseFromHttpUrl(url);
			// Parse the answer (categories or entries)
			final Response rp = JsonParser.parse(this.mAction, output);
			Log.d("debug", "Fetched entries");
			return rp;
		} catch (Exception e) { // Error management (no connection)
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(Response response) {
		mResponse.postValue(response);
	}
}

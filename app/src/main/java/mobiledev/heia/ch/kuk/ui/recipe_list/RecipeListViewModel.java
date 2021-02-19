package mobiledev.heia.ch.kuk.ui.recipe_list;

import android.app.Application;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mobiledev.heia.ch.kuk.data.database.Entry;
import mobiledev.heia.ch.kuk.data.database.EntryRepository;
import mobiledev.heia.ch.kuk.data.database.Query;
import mobiledev.heia.ch.kuk.data.network.FetchAsyncTask;
import mobiledev.heia.ch.kuk.data.network.Response;

@SuppressWarnings("WeakerAccess")
public class RecipeListViewModel extends ViewModel {
	// data members
	private final MutableLiveData<Entry[]> mDataset;
	// data members dealing with repository
	private final EntryRepository rep;

	// constructor (builds the repository)
	public RecipeListViewModel(Application application) {
		this.rep = new EntryRepository(application, false);
		this.mDataset = new MutableLiveData<>();
	}

	@SuppressWarnings("unchecked")
	public LiveData<Entry[]> fetchEntries(FragmentActivity activity, Query query) {
		if (query.getAction() == Query.Action.RECENT) {
			// Fetch all the saved entries from the "recently_viewed" database
			rep.getAllEntries().observe(activity, (Observer) (response) -> {
				int length = ((List<Entry>) response).toArray().length;
				Entry[] entries = new Entry[length];

				for (int i = 0; i < length; i++) {
					entries[i] = ((List<Entry>) response).get(length - 1 - i);
				}

				this.mDataset.postValue(entries);
			});
		} else {
			// Check if the query isn't an empty search
			if (!(query.getAction() == Query.Action.SEARCH && query.getParam().length() == 0)) {
				FetchAsyncTask fetch = new FetchAsyncTask(query);
				fetch.getResponse().observe(activity, (Observer) (response) -> {
					Response resp = (Response) response;
					if (resp != null)
						this.mDataset.postValue(resp.getEntries());
					else
						this.mDataset.postValue(new Entry[]{});
				});
				fetch.execute();
			} else
				this.mDataset.postValue(new Entry[]{});
		}

		return this.mDataset;
	}

	// getter methods for live data
	public Entry[] getEntries() {
		return this.mDataset.getValue();
	}
}
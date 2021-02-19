package mobiledev.heia.ch.kuk.ui.recipe;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mobiledev.heia.ch.kuk.data.database.Entry;
import mobiledev.heia.ch.kuk.data.database.EntryRepository;
import mobiledev.heia.ch.kuk.data.network.DownloadAsyncTask;

class RecipeViewModel extends ViewModel {
	private final EntryRepository rep;
	@SuppressWarnings({"unused"})
	private Entry entry;
	private final MutableLiveData<Bitmap> bitmap;

	public RecipeViewModel(Application application) {
		this.rep = new EntryRepository(application, true);
		this.bitmap = new MutableLiveData<>();
	}

	public Entry getEntry() {
		return entry;
	}

	// Create a new Entry object (to force Room to generate a new UID each time the recipe is viewed,
	// even from the list of recently viewed recipes) and store it into the ViewModel
	public void setEntry(Entry entry) {
		this.entry = new Entry(entry.getRecipeID(), entry.getRecipeName(), entry.getRecipeCategory(),
				entry.getRecipeArea(), entry.getRecipeTags(), entry.getRecipeThumbnail(),
				entry.getRecipeYouTube(), entry.getRecipeInstructions(), entry.getRecipeIngredients(),
				entry.getRecipeMeasures(), entry.getImg());
		rep.insert(this.entry);
	}

	// Method to download the recipe's image is none is received from the fragment's caller
	public LiveData<Bitmap> downloadImg(FragmentActivity activity) {
		DownloadAsyncTask asyncTask = new DownloadAsyncTask();
		asyncTask.getBitmap().observe(activity, bmp -> {
			this.bitmap.postValue(bmp);
			this.entry.setImg(bmp);
			rep.insert(this.entry);
		});

		try {
			asyncTask.execute(this.entry.getRecipeThumbnail());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.bitmap;
	}
}

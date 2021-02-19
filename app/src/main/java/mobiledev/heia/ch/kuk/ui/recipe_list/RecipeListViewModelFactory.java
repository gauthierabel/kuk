package mobiledev.heia.ch.kuk.ui.recipe_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class RecipeListViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
	// data members
	private final Application mApplication;

	public RecipeListViewModelFactory(Application application) {
		super(application);
		mApplication = application;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		//noinspection unchecked
		return (T) new RecipeListViewModel(mApplication);
	}
}

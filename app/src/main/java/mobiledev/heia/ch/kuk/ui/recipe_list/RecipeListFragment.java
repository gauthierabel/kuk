package mobiledev.heia.ch.kuk.ui.recipe_list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Objects;

import mobiledev.heia.ch.kuk.R;
import mobiledev.heia.ch.kuk.data.database.Entry;
import mobiledev.heia.ch.kuk.data.database.Query;

@SuppressWarnings("WeakerAccess")
public class RecipeListFragment extends Fragment {
	private RecipeListViewModel viewModel;
	private RecyclerView recycler;
	@SuppressWarnings({"FieldCanBeLocal", "RedundantSuppression"})
	private TextView lblErr;
	@SuppressWarnings({"FieldCanBeLocal", "RedundantSuppression"})
	private RecyclerView.Adapter<MyAdapter.MyViewHolder> adapter;
	@SuppressWarnings({"FieldCanBeLocal", "RedundantSuppression"})
	private RecyclerView.LayoutManager manager;

	private Query mQuery = new Query(Query.Action.RECENT, "");
	private String title = "Recipe List";

	public View onCreateView(@NonNull LayoutInflater inflater,
													 ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_recipe_list, container, false);

		// The ViewModel is tasked with storing the dataset and fetching its contents
		ViewModelProvider.AndroidViewModelFactory factory = new RecipeListViewModelFactory(
				requireActivity().getApplication());
		viewModel = new ViewModelProvider(this, factory).get(RecipeListViewModel.class);

		// Execute the query received from caller
		if (getArguments() != null && getArguments().containsKey("query")) {
			this.mQuery = (Query) getArguments().getSerializable("query");
			Log.d("debug", Objects.requireNonNull(mQuery).getParam());

			// Set title according to query
			switch (mQuery.getAction()) {
				case RANDOM:
					Log.wtf("debug",
							"A recipe list displayed with the random action? This shouldn't happen!");
					break;
				case CATEGORIES:
					Log.wtf("debug",
							"A list displayed with the categories action? This shouldn't happen!");
					break;
				case ID:
					Log.wtf("debug",
							"A list displayed with the ID action? This shouldn't happen!");
					break;
				case SEARCH: {
					if (mQuery.getParam().length() == 0)
						title = "Empty search query";
					else
						title = "Search results for : \"" + mQuery.getParam() + "\"";

					break;
				}
				case CAT_RECIPES: {
					title = "Recipes in Category \"" + mQuery.getParam() + "\"";
					break;
				}
				case RECENT: {
					title = "Recently Viewed";
					break;
				}
			}

			Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(title);
			Log.d("debug", "Title set to \"" + title + "\"");
		}

		return root;
	}

	@Override
	public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		lblErr = view.findViewById(R.id.lbl_error);
		recycler = Objects.requireNonNull(requireView()).findViewById(R.id.recycler);

		// Different spacing for home screen and other recipe lists
		if (mQuery.getAction() == Query.Action.RECENT)
			recycler.setPadding(24, 32, 24, 316);
		else
			recycler.setPadding(24, 32, 24, 200);

		// RecyclerView display options
		recycler.setClipToPadding(false);
		recycler.setHasFixedSize(true);
		recycler.setItemViewCacheSize(30);


		if (this.viewModel.getEntries() == null)
			viewModel.fetchEntries(getActivity(), this.mQuery)
					.observe(requireActivity(), this::populateRecycler);
		else
			populateRecycler(viewModel.getEntries());
	}

	private void populateRecycler(Entry[] dataset) {
		// Set the RecyclerView's dataset
		if (dataset.length > 0) {
			manager = new LinearLayoutManager(this.getContext());
			adapter = new MyAdapter(dataset, this);
			recycler.setLayoutManager(manager);
			recycler.setAdapter(adapter);
		} else {
			// Inform the user on the cause of the absence of any recipe card
			if (mQuery.getAction() == Query.Action.RECENT)
				lblErr.setText(R.string.recent_empty);
			else
				lblErr.setText(R.string.query_error);

			lblErr.setVisibility(View.VISIBLE);
		}
	}
}

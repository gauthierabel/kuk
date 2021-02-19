package mobiledev.heia.ch.kuk.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import java.util.Objects;

import mobiledev.heia.ch.kuk.R;
import mobiledev.heia.ch.kuk.data.network.FetchAsyncTask;
import mobiledev.heia.ch.kuk.data.network.Response;
import mobiledev.heia.ch.kuk.data.database.Query;

@SuppressWarnings("WeakerAccess")
public class HomeFragment extends Fragment {
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
													 ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Bundle mArgs = new Bundle();

		// Navigate to a random recipe with the UI's "Random" button
		ImageButton btn_random = view.findViewById(R.id.btn_rand);
		btn_random.setOnClickListener(v -> {
			FetchAsyncTask fetch = new FetchAsyncTask(new Query(Query.Action.RANDOM, ""));
			fetch.getResponse().observe(Objects.requireNonNull(requireActivity()),
					(Observer) (response) -> {
						Response resp = (Response) response;
						if (resp != null) {
							mArgs.putSerializable("entry", resp.getEntry(0));
							Navigation.findNavController(view)
									.navigate(R.id.action_homeFragment_to_recipeFragment, mArgs);
						}
					});
			fetch.execute();
		});

		// Navigate to the Favorites fragment (not impl.) with the UI's "Favorites" button
		ImageButton btn_fav = view.findViewById(R.id.btn_fav);
		btn_fav.setOnClickListener(v -> {
			mArgs.putSerializable("query", new Query(Query.Action.SEARCH, "Favorites!"));
			// TODO create action FAVORITES
			Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_recipeListFragment,
					mArgs);
		});

		// Navigate to the Search fragment with the UI's "Search" button
		ImageButton btn_search = view.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(v -> Navigation.findNavController(view)
				.navigate(R.id.action_homeFragment_to_searchFragment));
	}
}

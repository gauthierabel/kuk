package mobiledev.heia.ch.kuk.ui.elements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mobiledev.heia.ch.kuk.R;

@SuppressWarnings("WeakerAccess")
public class SearchFragment extends Fragment {

	public SearchFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
													 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Button btnSearch = view.findViewById(R.id.btn_search_by_name);
		btnSearch.setOnClickListener(v -> Navigation.findNavController(view).navigate(
				R.id.action_searchFragment_to_searchByName));

		Button btnCat = view.findViewById(R.id.btn_categories);
		btnCat.setOnClickListener(v -> Navigation.findNavController(view).navigate(
				R.id.action_searchFragment_to_categoriesFragment));
	}
}

package mobiledev.heia.ch.kuk.ui.elements;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import mobiledev.heia.ch.kuk.R;
import mobiledev.heia.ch.kuk.data.database.Query;

@SuppressWarnings("WeakerAccess")
public class SearchByNameFragment extends Fragment {
	public SearchByNameFragment() {
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
		return inflater.inflate(R.layout.fragment_search_by_name, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		EditText fieldName = view.findViewById(R.id.field_name);
		Button btnPostSearch = view.findViewById(R.id.btn_search_name_post);

		// Add search function when keyboard's "Done" button is pressed
		fieldName.setFocusedByDefault(true);
		fieldName.setOnEditorActionListener((v, actionId, event) -> {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				// hide virtual keyboard
				InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(
						Context.INPUT_METHOD_SERVICE);
				Objects.requireNonNull(imm).hideSoftInputFromWindow(fieldName.getWindowToken(),
						InputMethodManager.RESULT_UNCHANGED_SHOWN);

				post(view, fieldName.getText().toString());
				return true;
			}
			return false;
		});

		// Simulate "Done" press on keyboard when the UI's "Search" button is pressed
		btnPostSearch.setOnClickListener(v -> fieldName.onEditorAction(EditorInfo.IME_ACTION_DONE));
	}

	private void post(View view, String search) {
		Log.d("debug", "Searched for \"" + search + "\"");

		// Set query type and parameter then navigate
		Bundle args = new Bundle();
		args.putSerializable("query", new Query(Query.Action.SEARCH, search));

		Navigation.findNavController(view).navigate(R.id.action_searchByName_to_recipeListFragment,
				args);
	}
}

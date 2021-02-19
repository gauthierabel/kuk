package mobiledev.heia.ch.kuk.ui.categories_list;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import mobiledev.heia.ch.kuk.R;

/*
 * THIS FRAGMENT IS NOT FINAL AND DATES BACK TO A PURELY VISUAL BUILD OF THE APP
 * (Will not be implemented for evaluation)
 */

@SuppressWarnings("WeakerAccess")
public class CategoriesListFragment extends Fragment {
	public CategoriesListFragment() {
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
		return inflater.inflate(R.layout.fragment_categories_list, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		LinearLayout list = Objects.requireNonNull(requireView()).findViewById(R.id.recycler);
		list.removeAllViews();

		for (int i = 0; i < 10; i++)
			addCard("Category n°" + i, getString(R.string.lorem),
					view);
	}

	@SuppressLint("ClickableViewAccessibility")
	private void addCard(String title, String desc, View view) {
		LinearLayout container = Objects.requireNonNull(requireView()).findViewById(R.id.recycler);

		final CardView card = new CardView(Objects.requireNonNull(requireView()).getContext());

		// Inflate the card layout into the newly created element
		@SuppressLint("InflateParams") View cardContent = getLayoutInflater()
				.inflate(R.layout.recipe_elt, null);
		card.addView(cardContent);

		// To force the LinearLayout to fiil its ScrollView parent
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
		);

		card.setLayoutParams(params);

		// Card display parameters
		card.setUseCompatPadding(true);
		card.setRadius(16);
		card.setContentPadding(0, 2, 0, 0);
		card.setCardElevation(8);

		// Add click action to open the corresponding category when card is clicked
		card.setOnClickListener(v -> {
			Bundle args = new Bundle();
			args.putString("title", title);

			Navigation.findNavController(view).navigate(
					R.id.action_categoriesFragment_to_recipeListFragment, args);
		});

		// Set card contents to be displayed on list
		TextView titleView = cardContent.findViewById(R.id.recipe_elt_title);
		titleView.setText(title);

		TextView descView = cardContent.findViewById(R.id.recipe_elt_desc);
		descView.setText(desc);

		container.addView(card);

		Log.d("card", "Card \"" + title + "\" created");
	}
}

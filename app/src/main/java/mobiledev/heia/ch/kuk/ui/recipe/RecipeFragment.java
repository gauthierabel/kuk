package mobiledev.heia.ch.kuk.ui.recipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import mobiledev.heia.ch.kuk.R;
import mobiledev.heia.ch.kuk.data.database.Entry;

@SuppressWarnings("WeakerAccess")
public class RecipeFragment extends Fragment {

	private boolean favState = false;
	@SuppressWarnings({"unused"})
	private RecipeViewModel mVM;
	@SuppressWarnings("FieldCanBeLocal")
	private Bitmap img;
	private int scrollState = 0;

	public View onCreateView(@NonNull LayoutInflater inflater,
													 ViewGroup container, Bundle savedInstanceState) {
		try {
			scrollState = savedInstanceState.getInt("scroll");
		} catch (NullPointerException ignore) {
		}

		return inflater.inflate(R.layout.fragment_recipe, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore scroll when rotating device
		requireView().findViewById(R.id.recipe_scroll).setScrollY(this.scrollState);

		// Display the entry received either from the Recipe List fragment or the Random feature
		if (getArguments() != null && requireArguments().containsKey("entry")) {
			// The ViewModel is tasked with storing the Recipe Fragment's Entry and downloading its image
			this.mVM = new RecipeViewModel(this.requireActivity().getApplication());
			this.mVM.setEntry((Entry) Objects.requireNonNull(requireArguments()
					.getSerializable("entry")));

			// Either display the bitmap received from the Recipe List fragment
			// or download it when there's none
			if (getArguments().containsKey("bitmap")) {
				// Reform bitmap from ByteArray parameter and display it
				this.img = BitmapFactory.decodeByteArray(requireArguments().getByteArray("bitmap"),
						0, Objects.requireNonNull(requireArguments().getByteArray("bitmap")).length);

				ImageView recipeImg = Objects.requireNonNull(requireView()).findViewById(R.id.img_recipe);
				recipeImg.setImageBitmap(this.img);
			} else {
				// Make the ViewModel download the recipe's image and display it
				mVM.downloadImg(requireActivity()).observe(Objects.requireNonNull(requireActivity()),
						bitmap -> ((ImageView) requireView().findViewById(R.id.img_recipe))
								.setImageBitmap(bitmap));
			}

			Log.d("debug", this.mVM.getEntry().toString());

			// Set ActionBar title to the name of the recipe
			Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar())
					.setTitle(this.mVM.getEntry().getRecipeName());
			Log.d("debug", "Title set to \"" + this.mVM.getEntry().getRecipeName() + "\"");

			// Display all of the recipe's information
			TextView txtMeasures = requireView().findViewById(R.id.text_measures);
			TextView txtIngredients = requireView().findViewById(R.id.text_ingredients);
			TextView txtInstructions = requireView().findViewById(R.id.text_instructions);
			TextView txtDetails = requireView().findViewById(R.id.text_details);
			Button btnYouTube = requireView().findViewById(R.id.btn_youtube);

			// Arrange the list of ingredients to be displayed
			String measures = "";
			String ingredients = "";
			int ingLen = this.mVM.getEntry().getRecipeIngredients().length;
			for (int i = 0; i < ingLen - 1; i++) {
				if (!this.mVM.getEntry().getRecipeIngredients()[i].contains("null") &&
						this.mVM.getEntry().getRecipeIngredients()[i].length() > 1) {
					if (i > 0) {
						measures = measures + "\n";
						ingredients = ingredients + "\n";
					}

					// Some recipes have fewer measures than ingredients
					try {
						measures = measures + "  • " + this.mVM.getEntry().getRecipeMeasures()[i];
					} catch (ArrayIndexOutOfBoundsException ignored) {
					}

					ingredients = ingredients + "-   " + this.mVM.getEntry().getRecipeIngredients()[i];
				}

				txtMeasures.setText(measures);
				txtIngredients.setText(ingredients);
			}

			txtInstructions.setText(this.mVM.getEntry().getRecipeInstructions());

			txtDetails.setText("");
			txtDetails.append(getString(R.string.desc_category) + this.mVM.getEntry().getRecipeCategory());
			txtDetails.append("\n" + getString(R.string.desc_area) + this.mVM.getEntry().getRecipeArea());

			// Arrange the list of tags to be displayed
			if (!this.mVM.getEntry().getRecipeTags()[0].contains("null") &&
					this.mVM.getEntry().getRecipeTags()[0].length() > 1) {
				txtDetails.append("\n" + getString(R.string.desc_tags));
				for (int i = 0; i < this.mVM.getEntry().getRecipeTags().length; i++) {
					if (!this.mVM.getEntry().getRecipeTags()[i].contains("null"))
						txtDetails.append(this.mVM.getEntry().getRecipeTags()[i]);
					if (i < this.mVM.getEntry().getRecipeTags().length - 1)
						txtDetails.append(", ");
				}
			}

			// Display a button to view the recipe's YouTube video if there is one
			if (this.mVM.getEntry().getRecipeYouTube().length() > 5)
				btnYouTube.setOnClickListener(v -> {
					Intent launchBrowser = new Intent(Intent.ACTION_VIEW,
							Uri.parse(this.mVM.getEntry().getRecipeYouTube()));
					startActivity(launchBrowser);
				});
			else
				btnYouTube.setVisibility(View.GONE);
		}

		// Favorite toggle (Not impl. for evalutaion)
		// TODO
		ImageButton btnAddFav = view.findViewById(R.id.btn_recipe_fav);
		btnAddFav.setOnClickListener(v -> {
			favState = !favState;

			if (favState)
				btnAddFav.setImageResource(R.drawable.favorite_btn_selected);
			else
				btnAddFav.setImageResource(R.drawable.favorite_btn);

			Log.d("debug", "Recipe added to favorites");
		});
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		ScrollView scroll = requireView().findViewById(R.id.recipe_scroll);
		outState.putInt("scroll", scroll.getScrollY());
	}
}
package mobiledev.heia.ch.kuk.ui.recipe_list;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;

import mobiledev.heia.ch.kuk.R;
import mobiledev.heia.ch.kuk.data.database.Entry;
import mobiledev.heia.ch.kuk.data.network.DownloadAsyncTask;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
	private final Entry[] mDataset;
	private final RecipeListFragment parent;

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		final CardView card;
		Bitmap img;

		MyViewHolder(CardView v) {
			super(v);
			card = v;
		}
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public MyAdapter(Entry[] myDataset, RecipeListFragment parent) {
		this.parent = parent;
		this.mDataset = myDataset;
	}

	// Create new views (invoked by the layout manager)
	@NonNull
	@Override
	public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
																									 int viewType) {
		// create a new card view
		CardView v = (CardView) LayoutInflater.from(parent.getContext())
				.inflate(R.layout.recipe_elt, parent, false);

		return new MyViewHolder(v);
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(MyViewHolder holder, int pos) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element

		// Card display parameters
		holder.card.setUseCompatPadding(true);
		holder.card.setRadius(16);
		holder.card.setContentPadding(0, 2, 0, 0);
		holder.card.setCardElevation(8);

		Log.d("cards", "Card pos: " + pos + " instantiated.");

		// Set the card's name to be displayed
		TextView title = holder.card.findViewById(R.id.recipe_elt_title);
		title.setText(mDataset[pos].getRecipeName());

		// Format and display the recipe's information onto the card
		TextView desc = holder.card.findViewById(R.id.recipe_elt_desc);
		desc.setText("");
		desc.append(this.parent.getString(R.string.desc_category) + mDataset[pos].getRecipeCategory());
		desc.append("\n" + this.parent.getString(R.string.desc_area) + mDataset[pos].getRecipeArea());

		// Arrange the tags to be displayed
		if (mDataset[pos].getRecipeTags().length > 0)
			if (!mDataset[pos].getRecipeTags()[0].equals("null")
					&& mDataset[pos].getRecipeTags()[0].length() > 1) {
				desc.append("\n" + this.parent.getString(R.string.desc_tags));
				for (int i = 0; i < mDataset[pos].getRecipeTags().length; i++) {
					if (!mDataset[pos].getRecipeTags()[i].equals("null"))
						desc.append(mDataset[pos].getRecipeTags()[i]);
					if (i < mDataset[pos].getRecipeTags().length - 1)
						desc.append(", ");
				}
			}

		// Either download the recipe's image or fecth it from the Entry saved in the Room database
		if (mDataset[pos].getImg() == null) {
			DownloadAsyncTask asyncTask = new DownloadAsyncTask();
			asyncTask.getBitmap().observe(this.parent, bitmap -> {
				final ImageView imageView = holder.card.findViewById(R.id.recipe_elt_preview);
				imageView.setImageBitmap(bitmap);
				holder.img = bitmap;
				mDataset[pos].setImg(bitmap);

				Log.d("cards", "Image n°" + pos + " loaded with URL: "
						+ mDataset[pos].getRecipeThumbnail());
			});
			asyncTask.execute(mDataset[pos].getRecipeThumbnail());
		} else {
			final ImageView imageView = holder.card.findViewById(R.id.recipe_elt_preview);
			imageView.setImageBitmap(mDataset[pos].getImg());
			holder.img = mDataset[pos].getImg();
		}

		// Add click action to open the corresponding recipe when card is clicked
		holder.card.setOnClickListener(v -> {
			Bundle args = new Bundle();

			// Pass the element's Entry to the Recipe fragment to display the recipe's details
			args.putSerializable("entry", mDataset[pos]);

			// Pass the card's downloaded image (if any) to avoid having to download it again
			if (holder.img != null) {
				ByteArrayOutputStream bs = new ByteArrayOutputStream();
				holder.img.compress(Bitmap.CompressFormat.JPEG, 100, bs);
				args.putByteArray("bitmap", bs.toByteArray());
			}

			try {
				Navigation.findNavController(this.parent.requireView())
						.navigate(R.id.action_global_recipeFragment, args);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		});
	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.length;
	}
}

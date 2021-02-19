package mobiledev.heia.ch.kuk;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewParent;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import mobiledev.heia.ch.kuk.data.database.Query;

public class MainActivity extends AppCompatActivity {

	private DrawerLayout drawerLayout;
	private NavController navController;
	private NavigationView navigationView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_drawer);

		drawerLayout = findViewById(R.id.drawer_layout);
		navigationView = findViewById(R.id.nav_view);
		navController = Navigation.findNavController(this, R.id.nav_host_fragment);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
		NavigationUI.setupWithNavController(navigationView, navController);
	}

	@Override
	public boolean onSupportNavigateUp() {
		return NavigationUI.navigateUp(navController, drawerLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		navigationView.setNavigationItemSelectedListener(item -> {
			boolean success = false;
			Bundle args = new Bundle();

			NavOptions.Builder builder = new NavOptions.Builder()
					.setLaunchSingleTop(true)
					.setEnterAnim(R.anim.nav_default_enter_anim)
					.setExitAnim(R.anim.nav_default_exit_anim)
					.setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
					.setPopExitAnim(R.anim.nav_default_pop_exit_anim);

			builder.setPopUpTo(R.id.homeFragment, false);
			NavOptions options = builder.build();

			String itemSelected = "No item found.";
			if (item == navigationView.getMenu().getItem(0)) {
				itemSelected = "Home Fragment selected";

				try {
					navController.navigate(R.id.action_global_homeFragment, args, options);
					success = true;
				} catch (IllegalArgumentException e) {
					success = false;
				}
			}
			if (item == navigationView.getMenu().getItem(1)) {
				itemSelected = "Favorites Fragment selected";

				// TODO create action FAVORITES
				args.putSerializable("query", new Query(Query.Action.SEARCH, "Favorites!"));
				try {
					navController.navigate(R.id.action_global_recipeListFragment, args, options);
					success = true;
				} catch (IllegalArgumentException e) {
					success = false;
				}
			}
			if (item == navigationView.getMenu().getItem(2)) {
				itemSelected = "Search Fragment selected";
				try {
					navController.navigate(R.id.action_global_searchFragment, args, options);
					success = true;
				} catch (IllegalArgumentException e) {
					success = false;
				}
			}

			Log.d("menuItem", itemSelected);

			ViewParent parent = navigationView.getParent();
			if (parent instanceof DrawerLayout) {
				((DrawerLayout) parent).closeDrawer(navigationView);
			}
			return success;
		});
		return super.onCreateOptionsMenu(menu);
	}
}

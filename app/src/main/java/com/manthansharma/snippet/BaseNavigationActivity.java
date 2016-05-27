package com.manthansharma.snippet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BaseNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	public static final String TAG = BaseNavigationActivity.class.getSimpleName();

	public void setContentView(@LayoutRes int layoutResID) {
		DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation, null);
		FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
		getLayoutInflater().inflate(layoutResID, activityContainer, true);
		super.setContentView(fullView);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setTitle("Activity Title");

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		assert drawer != null;
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		assert navigationView != null;
		navigationView.setNavigationItemSelectedListener(this);


		View headerView = navigationView.getHeaderView(0);

		TextView navHeaderName = (TextView) headerView.findViewById(R.id.nav_header_name);
		TextView navHeaderEmail = (TextView) headerView.findViewById(R.id.nav_header_email);

		SharedPreferences sharedPreferences = this.getSharedPreferences("com.manthansharma.snippet", MODE_PRIVATE);

		navHeaderName.setText(sharedPreferences.getString("user_name", null));
		navHeaderEmail.setText(sharedPreferences.getString("user_email", null));
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.nav_snippet_list) {
			Intent intent = new Intent(getApplicationContext(), SnippetList.class);
			startActivity(intent);
			finish();
		} else if (id == R.id.nav_logout) {
			Intent intent = new Intent(getApplicationContext(), LogoutActivity.class);
			startActivity(intent);
			finish();
		} else if (id == R.id.nav_exit) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("Exit", true);
			startActivity(intent);
			finish();
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		assert drawer != null;
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
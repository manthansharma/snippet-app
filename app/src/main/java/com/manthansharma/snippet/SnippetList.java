package com.manthansharma.snippet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.manthansharma.snippet.api.MyApiEndpointInterface;
import com.manthansharma.snippet.api.model.Snippet;
import com.manthansharma.snippet.api.model.snippetList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnippetList extends BaseNavigationActivity {
	private static final int REQUEST_CREATE_SNIPPET = 0;
	public static final String TAG = SnippetList.class.getSimpleName();
	private static snippetList snippetList;
	private static ListView listSnippet;
	private static Boolean loadMore = false;
	private static String url;
	private static SnippetListAdapter adapterSnippet;

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		SnippetList.url = url;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snippet_list);
		setTitle("Snippet List");

		listSnippet = (ListView) findViewById(R.id.snippet_list);
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		assert navigationView != null;
		navigationView.getMenu().getItem(0).setChecked(true);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_snippet_fab);
		assert fab != null;
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), CreateSnippet.class);
				startActivityForResult(intent, REQUEST_CREATE_SNIPPET);
				overridePendingTransition(R.anim.slide_up, R.anim.no_change);
			}
		});

		if (adapterSnippet == null) {
			setUrl("snippet/");
			new SnippetListTask().execute();
		} else {
			System.out.println(adapterSnippet.getCount());
		}

		listSnippet.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			                     int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;
				if ((lastInScreen == totalItemCount) && (!loadMore)) {
					if (snippetList != null) {
						try {
							URL tempURL = new URL(snippetList.getNext());
							setUrl(tempURL.getFile());
							new SnippetListTask().execute();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	private class SnippetListTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog progressDialog = new ProgressDialog(SnippetList.this);

		@Override
		protected void onPreExecute() {
			loadMore = true;
			progressDialog.setMessage("Getting your data... Please wait...");
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			SharedPreferences sharedPreferences = getSharedPreferences("com.manthansharma.snippet", MODE_PRIVATE);
			String auth_token = sharedPreferences.getString("auth_token", null);
			MyApiEndpointInterface apiService = ServiceGenerator.createService(MyApiEndpointInterface.class, auth_token);
			System.out.println(getUrl());
			apiService.list_snippet(getUrl()).enqueue(new Callback<snippetList>() {
				@Override
				public void onResponse(Call<snippetList> call, Response<snippetList> response) {
					List<Snippet> listTemp = new ArrayList<Snippet>();
					if (response.isSuccessful()) {
						if (snippetList != null) {
							listTemp = snippetList.getResults();
						}
						snippetList = response.body();
						snippetList.updateResults(listTemp);
						onSnippetListSuccess();
					} else {
						onSnippetListFailed();
					}
				}

				@Override
				public void onFailure(Call<snippetList> call, Throwable t) {
					onSnippetListFailed();
				}
			});
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			loadMore = false;
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}

	private void onSnippetListFailed() {
		Toast.makeText(this, "Network Faliure. Try again after some time", Toast.LENGTH_LONG).show();
	}

	private void onSnippetListSuccess() {
		int listPosition = listSnippet.getFirstVisiblePosition();
		adapterSnippet = new SnippetListAdapter(this, snippetList.getResults());
		assert listSnippet != null;
		listSnippet.setAdapter(adapterSnippet);

		adapterSnippet.notifyDataSetChanged();
		listSnippet.setSelection(listPosition);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CREATE_SNIPPET) {
			if (resultCode == RESULT_OK) {
				setUrl("snippet/");
				new SnippetListTask().execute();
				onCreateSnippetSuccess();
			}
		}
	}
	
	private void onCreateSnippetSuccess() {
		Toast.makeText(this, "Snippet Created Successfully!!", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		assert drawer != null;
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
}

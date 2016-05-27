package com.manthansharma.snippet;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.manthansharma.snippet.api.MyApiEndpointInterface;
import com.manthansharma.snippet.api.model.Snippet;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSnippet extends AppCompatActivity {
	public static final String TAG = CreateSnippet.class.getSimpleName();

	@InjectView(R.id.input_create_snippet_name)
	EditText _nameText;
	@InjectView(R.id.wrapper_create_snippet_name)
	TextInputLayout _nameWrapper;
	@InjectView(R.id.input_create_snippet_code)
	EditText _codeText;
	@InjectView(R.id.wrapper_create_snippet_code)
	TextInputLayout _codeWrapper;
	@InjectView(R.id.input_create_snippet_language)
	Spinner _languageSpinner;
	@InjectView(R.id.input_create_snippet_style)
	Spinner _styleSpinner;
	@InjectView(R.id.create_snippet_btn)
	Button _snippetButton;

	public static Snippet snippet = new Snippet();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_snippet);
		ButterKnife.inject(this);

		ActionBar actionBar = getSupportActionBar();
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Create Snippet");

		String[] languageArray = getResources().getStringArray(R.array.language);
		String[] styleArray = getResources().getStringArray(R.array.style);

		Spinner snipperLanguage = (Spinner) findViewById(R.id.input_create_snippet_language);
		Spinner snipperStyle = (Spinner) findViewById(R.id.input_create_snippet_style);

		ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(this, R.layout.spinner_item, languageArray);
		ArrayAdapter<String> adapterStyle = new ArrayAdapter<String>(this, R.layout.spinner_item, styleArray);

		assert snipperLanguage != null;
		snipperLanguage.setAdapter(adapterLanguage);
		snipperLanguage.setSelection(adapterLanguage.getPosition("Python 3"));

		assert snipperStyle != null;
		snipperStyle.setAdapter(adapterStyle);
		snipperStyle.setSelection(adapterStyle.getPosition("friendly"));

		EditText snippetCodeEdit = (EditText) findViewById(R.id.input_create_snippet_code);
		assert snippetCodeEdit != null;
		snippetCodeEdit.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				if (view.getId() == R.id.input_create_snippet_code) {
					view.getParent().requestDisallowInterceptTouchEvent(true);
					switch (event.getAction() & MotionEvent.ACTION_MASK) {
						case MotionEvent.ACTION_UP:
							view.getParent().requestDisallowInterceptTouchEvent(false);
							break;
					}
				}
				return false;
			}
		});

		_snippetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				create_snippet();
			}
		});
	}

	public void create_snippet() {
		if (!validate()) {
			onCreateSnippetFailed();
			return;
		}

		_snippetButton.setEnabled(false);

		String name = _nameText.getText().toString();
		String code = _codeText.getText().toString();
		String language = _languageSpinner.getSelectedItem().toString();
		String style = _styleSpinner.getSelectedItem().toString();

		snippet = new Snippet(name, language, style, code);
		new CreateSnippetTask().execute();
	}

	private class CreateSnippetTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog progressDialog = new ProgressDialog(CreateSnippet.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Creating...");
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			SharedPreferences sharedPreferences = getSharedPreferences("com.manthansharma.snippet", MODE_PRIVATE);
			String auth_token = sharedPreferences.getString("auth_token", null);
			MyApiEndpointInterface apiService = ServiceGenerator.createService(MyApiEndpointInterface.class,auth_token);
			apiService.create_snippet(snippet).enqueue(new Callback<Snippet>() {
				@Override
				public void onResponse(Call<Snippet> call, Response<Snippet> response) {

					if (response.isSuccessful()) {
						snippet = response.body();
						onCreateSnippetSuccess();
					} else {
						onCreateSnippetFailed();
					}
				}

				@Override
				public void onFailure(Call<Snippet> call, Throwable t) {
					t.printStackTrace();
					onCreateSnippetFailed();
				}
			});
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}

	public void onCreateSnippetSuccess() {
		_snippetButton.setEnabled(true);
		setResult(RESULT_OK, null);
		finish();
		overridePendingTransition(R.anim.no_change, R.anim.slide_down);
	}

	public void onCreateSnippetFailed() {
		_snippetButton.setEnabled(true);
		Toast.makeText(this, "Network Faliure. Try again after some time", Toast.LENGTH_LONG).show();
	}

	public boolean validate() {
		boolean valid = true;

		String name = _nameText.getText().toString();
		String code = _codeText.getText().toString();

		if (name.isEmpty() || name.length() < 3) {
			_nameWrapper.setError("at least 3 characters");
			valid = false;
		} else {
			_nameWrapper.setError(null);
		}

		if (code.isEmpty() || code.length() < 20) {
			_codeWrapper.setError("at least 20 characters");
			valid = false;
		} else {
			_codeWrapper.setError(null);
		}

		return valid;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {
			finish();
		}

		return super.onOptionsItemSelected(item);
	}



}

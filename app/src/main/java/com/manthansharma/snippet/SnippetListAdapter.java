package com.manthansharma.snippet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manthansharma.snippet.api.model.Snippet;

import java.util.List;

public class SnippetListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Snippet> snippetItems;

	public SnippetListAdapter(Activity activity, List<Snippet> snippetItems) {
		this.activity = activity;
		this.snippetItems = snippetItems;
	}

	@Override
	public int getCount() {
		return snippetItems.size();
	}

	@Override
	public Object getItem(int i) {
		return snippetItems.get(i);
	}

	@Override
	public long getItemId(int i) {
		return snippetItems.get(i).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.snippet_list_item, null);

		TextView name = (TextView) convertView.findViewById(R.id.snippet_item_name);
		TextView created = (TextView) convertView.findViewById(R.id.snippet_item_created);
		TextView extract = (TextView) convertView.findViewById(R.id.snippet_item_extract);
		WebView highlight = (WebView) convertView.findViewById(R.id.snippet_item_highlight);

		Snippet item = snippetItems.get(position);
		name.setText(item.getTitle());
		extract.setText(String.format("Language: %s, Style: %s", item.getLanguage(), item.getStyle()));
		highlight.loadUrl(item.getHighlight());
		created.setText(item.getDaysAgo());

		return convertView;
	}

}
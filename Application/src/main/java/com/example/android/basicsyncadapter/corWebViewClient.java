package com.example.android.basicsyncadapter;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by madair on 29/06/2015.
 */
public class corWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// hardcoded host url for defining app limited webview browsing
		if (Uri.parse(url).getHost().equals("coradviser.com.au")) {
			return false;
		}

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		view.getContext().startActivity(intent);
		return true;
	}
}

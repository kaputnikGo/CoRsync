package com.example.android.basicsyncadapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Activity for holding EntryListFragment.
 */
public class EntryListActivity extends FragmentActivity {

    GoogleCloudMessaging gcm;
		String regId;
		private final String PROJECT_NUMBER = "444961801699";

		private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
				//Log.i("GCM: ", "onCreate start...");
				getRegId();

				setContentView(R.layout.activity_entry_list);

				// enable contained webview for viewing articles listed in main activity
				webView = (WebView) findViewById(R.id.activity_main_webview);
				// custom webViewClient that views all coradviser.com.au site within this app,
				// external urls will still invoke the client-side browser
				webView.setWebViewClient(new corWebViewClient());

				WebSettings webSettings = webView.getSettings();
				webSettings.setJavaScriptEnabled(true); //enable javascript (default is off)
    }

	public void getRegId() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "starting getRegId...";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					}
					regId = gcm.register(PROJECT_NUMBER);
					msg = "Device registered, registration ID= " + regId;
					Log.i("GCM:", msg);
				} catch (IOException ex) {
					msg = "ERROR: " + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				// do nothing, yet
			}
		}.execute(null, null, null);
	}

	public void loadSelectedArticle(String articleUrlString) {
		if (articleUrlString == null) {
			//go away
			return;
		}
		else {
			// have a string
			webView.loadUrl(articleUrlString);
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// check for BACK button and a history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		// if no, default to sys behaviour
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}

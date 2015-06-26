package com.example.android.basicsyncadapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Activity for holding EntryListFragment.
 */
public class EntryListActivity extends FragmentActivity {

    GoogleCloudMessaging gcm;
		String regId;
		private final String PROJECT_NUMBER = "444961801699";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
				//Log.i("GCM: ", "onCreate start...");
				getRegId();

				setContentView(R.layout.activity_entry_list);
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

		@Override
		public void onDestroy() {
			super.onDestroy();
		}
}

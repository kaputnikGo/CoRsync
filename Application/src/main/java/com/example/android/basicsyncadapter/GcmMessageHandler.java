package com.example.android.basicsyncadapter;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by madair on 25/06/2015.
 */
public class GcmMessageHandler extends IntentService {
	String message; //full gcm push notification message
	String title; // title of message
	private Handler handler;
	// notification vars
	private NotificationCompat.Builder mBuilder;
	private NotificationManager mNotificationManager;
	private static final int notifyID = 24; //this id must be same? and more unique?
	private Intent resultIntent;
	private PendingIntent resultPendingIntent;

	public GcmMessageHandler() {
		super("GcmMessageHandler");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		handler = new Handler();
		buildNotification();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		message = extras.getString("data");
		title = extras.getString("title");

		if (title == null) {
			title = "title not extracted from message.";
		}
		Log.i("GCM", title);

		if (message == null) {
			message = "message came through as null.";
		}
		showToast(title + ": " + message);
		Log.i("GCM", "Received : (" + messageType + ") " + extras.getString("data"));

		updateNotification(title, message);
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	public void showToast(String displayMessage) {
		final String toastMessage = displayMessage;

		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
			}
		});
	}

	private void buildNotification() {
		mBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.notification_icon)
				.setContentTitle("CoR Notification")
				.setContentText("This is app start notify.")
				.setAutoCancel(true);

		resultIntent = new Intent(this, EntryListActivity.class);

		// Because clicking the notification opens a new ("special") activity, there's
		// no need to create an artificial back stack.
		resultPendingIntent = PendingIntent.getActivity(
				this,
				0,
				resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT
		);
		mBuilder.setContentIntent(resultPendingIntent);

		mNotificationManager =
				(NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
		mNotificationManager.notify(notifyID, mBuilder.build());
	}

	public void updateNotification(String title, String message) {
		mBuilder
				.setContentTitle(title)
				.setContentText(message)
				.setSmallIcon(R.drawable.notification_icon);

		mNotificationManager.notify(
				notifyID,
				mBuilder.build());
	}
}

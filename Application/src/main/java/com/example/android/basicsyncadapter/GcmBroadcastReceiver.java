package com.example.android.basicsyncadapter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by madair on 25/06/2015.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		// GcmMessageHandler to handle intent
		ComponentName comp = new ComponentName(context.getPackageName(),
				GcmMessageHandler.class.getName());

		// start service, keep awake
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
	}
}

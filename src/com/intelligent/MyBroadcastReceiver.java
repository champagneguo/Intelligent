package com.intelligent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

	private String TAG = "MyBroadcastReceiver";
	static final String action = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "onReceive");
		// TODO Auto-generated method stub

		Intent StartIntent = new Intent(context, MainActivity.class);
		StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(StartIntent);

	}

}

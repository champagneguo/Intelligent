package com.intelligent.search;

import com.example.nfc.util.Consts;
import com.intelligent.load.DataCheckActivity;
import com.intelligent.load.DataLoadActivity;
import com.intelligent.service.NFCService;
import com.intelligent.util.IDentity;
import com.intelligent.util.Utils;
import com.intenlligent.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PipeRecognition extends Activity {
	private Button detect, read, load, inspection, report;
	private ImageView image;
	private boolean flag = false;
	private AnimationDrawable anim;
	private String TAG = "PipeRecognition";
	private MyBroadcast mReceiver; // 广播接受者用于接收服务返回的数据
	private String Uid = null;
	private int cmdCode = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piperecognition);
		Log.e(TAG, "onCreate");
		detect = (Button) findViewById(R.id.piperecognition_detect);
		read = (Button) findViewById(R.id.piperecognition_read);
		load = (Button) findViewById(R.id.piperecognition_load);
		inspection = (Button) findViewById(R.id.piperecognition_inspection);
		report = (Button) findViewById(R.id.piperecognition_report);
		image = (ImageView) findViewById(R.id.piperecognition_imageView);
		image.setImageResource(R.drawable.search);
		anim = (AnimationDrawable) image.getDrawable();
		if (anim == null) {
			Log.e("1======", "anim");
		}

		detect.setOnClickListener(new MyClick());
		read.setOnClickListener(new MyClick());
		load.setOnClickListener(new MyClick());
		inspection.setOnClickListener(new MyClick());
		report.setOnClickListener(new MyClick());

		// 注册广播接收者
		mReceiver = new MyBroadcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.intelligent.search.PipeRecognition");
		registerReceiver(mReceiver, filter);

	}

	public class MyClick implements OnClickListener {
		Intent cmdIntent = new Intent(PipeRecognition.this, NFCService.class);

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.piperecognition_detect:
				Log.e(TAG, "piperecognition_detect");
				cmdCode = Consts.Init_14443a;
				cmdIntent.putExtra("cmd", cmdCode);
				Log.e(TAG, "7");
				startService(cmdIntent);
				anim.start();
				Log.e(TAG, "send read cmd==" + cmdCode);
				break;
			case R.id.piperecognition_read:
				Log.e(TAG, "piperecognition_read");
				anim.stop();
				cmdCode = 0;
				cmdIntent.putExtra("cmd", cmdCode);
				startService(cmdIntent);
				Intent intent = new Intent(PipeRecognition.this,
						DataCheckActivity.class);
				intent.putExtra("UID", Uid);
				Log.e(TAG, "R.id.piperecognition_read==" + Uid);
				startActivity(intent);
				flag = false;
				setButtonClickable(flag);
				break;
			case R.id.piperecognition_load:
				Log.e(TAG, "piperecognition_load");
				anim.stop();
				cmdCode = 0;
				cmdIntent.putExtra("cmd", cmdCode);
				startService(cmdIntent);
				Intent intent1 = new Intent(PipeRecognition.this,
						DataLoadActivity.class);
				intent1.putExtra("UID", Uid);
				Log.e(TAG, "R.id.piperecognition_load==" + Uid);
				startActivity(intent1);
				flag = false;
				setButtonClickable(flag);
				break;

			case R.id.piperecognition_inspection:
				Log.e(TAG, "piperecognition_inspection");
				anim.stop();
				cmdCode = 0;
				cmdIntent.putExtra("cmd", cmdCode);
				startService(cmdIntent);
				Intent intent2 = new Intent(PipeRecognition.this,
						Inspection.class);
				intent2.putExtra("UID", Uid);
				Log.e(TAG, "R.id.piperecognition_inspection==" + Uid);
				startActivity(intent2);
				flag = false;
				setButtonClickable(flag);
				break;
			case R.id.piperecognition_report:
				Log.e(TAG, "piperecognition_report");
				anim.stop();
				cmdCode = 0;
				cmdIntent.putExtra("cmd", cmdCode);
				startService(cmdIntent);
				Intent intent3 = new Intent(PipeRecognition.this, Report.class);
				intent3.putExtra("UID", Uid);
				Log.e(TAG, "R.id.piperecognition_report==" + Uid);
				startActivity(intent3);
				flag = false;
				setButtonClickable(flag);
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setButtonClickable(flag);
	}

	private void setButtonClickable(boolean flag) {
		read.setClickable(flag);
		load.setClickable(flag);
		inspection.setClickable(flag);
		report.setClickable(flag);
		if (!flag) {
			read.setTextColor(getResources().getColor(R.color.back));
			load.setTextColor(getResources().getColor(R.color.back));
			inspection.setTextColor(getResources().getColor(R.color.back));
			report.setTextColor(getResources().getColor(R.color.back));
		} else {
			read.setTextColor(getResources().getColor(R.color.green));
			load.setTextColor(getResources().getColor(R.color.green));
			inspection.setTextColor(getResources().getColor(R.color.green));
			report.setTextColor(getResources().getColor(R.color.green));

		}

	}

	// 广播接收者；
	public class MyBroadcast extends BroadcastReceiver {
		String[] idInfo;
		IDentity identity;

		@Override
		public void onReceive(Context context, Intent intent) {

			// 服务返回的数据
			String receivedata = intent.getStringExtra("result");

			if (receivedata != null) {
				Log.e(TAG, "onReceive  :" + receivedata);
				// 接收返回的ID
				if (cmdCode == Consts.Init_14443a) {
					Utils.playMedia(PipeRecognition.this);
					idInfo = receivedata.split(" ");
					identity = new IDentity();
					identity.setIdtype(idInfo[0]);
					identity.setUid(idInfo[1]);
					Log.e(TAG, "MyBroadcast UID==" + Uid);
					Uid = identity.getUid();
					if (Uid != null) {
						flag = true;
						setButtonClickable(flag);
					}
				}
			}

		}

	}

	@Override
	protected void onDestroy() {
		Intent stopService = new Intent();
		stopService.setAction("com.intelligent.service.NFCService");
		stopService.putExtra("stopflag", true);
		sendBroadcast(stopService); // 给服务发送广播,令服务停止
		Log.e(TAG, "send stop");
		// 卸载注册
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

}

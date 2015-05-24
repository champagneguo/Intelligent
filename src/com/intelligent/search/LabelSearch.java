package com.intelligent.search;

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

import com.example.nfc.util.Consts;
import com.intelligent.load.DataCheckActivity;
import com.intelligent.load.DataLoadActivity;
import com.intelligent.service.NFCService;
import com.intelligent.util.IDentity;
import com.intelligent.util.Utils;
import com.intenlligent.R;

public class LabelSearch extends Activity {
	private ImageView image;
	private Button search, start, load, inspection, report;
	private boolean flag = false;
	private AnimationDrawable anim;
	// private MediaPlayer mplayer;
	private MyBroadcast mReceiver; // 广播接受者用于接收服务返回的数据
	private String TAG = "LabelSearch";
	private int cmdCode = 0;
	private String Uid = null;
	private Double Latitude = -1d, Longitude = -1d;
	private String from = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.labelsearch);
		Log.e("1========", "oncreate()");
		image = (ImageView) findViewById(R.id.labelsearch_imageview);
		search = (Button) findViewById(R.id.labelsearch_search);
		start = (Button) findViewById(R.id.labelsearch_start);
		load = (Button) findViewById(R.id.labelsearch_load);
		inspection = (Button) findViewById(R.id.labelsearch_inspection);
		report = (Button) findViewById(R.id.labelsearch_report);
		Log.e("4=======", "start");
		// 设置image的背景
		image.setImageResource(R.drawable.circle);
		Log.e("5======", "image");
		anim = (AnimationDrawable) image.getDrawable();
		Latitude = this.getIntent().getDoubleExtra("Latitude", -1d);
		Longitude = this.getIntent().getDoubleExtra("Longitude", -1d);
		from = this.getIntent().getStringExtra("from");

		// 初始化本地音乐资源文件
		// mplayer = MediaPlayer.create(LabelSearch.this, R.raw.thunder);

		start.setOnClickListener(new Myclick());
		search.setOnClickListener(new Myclick());
		load.setOnClickListener(new Myclick());
		inspection.setOnClickListener(new Myclick());
		report.setOnClickListener(new Myclick());

		// 注册广播接收者
		mReceiver = new MyBroadcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.intelligent.search.LabelSearch");
		registerReceiver(mReceiver, filter);

		// 启动服务
		Intent intent = new Intent(LabelSearch.this, NFCService.class);
		startService(intent);
		super.onStart();

	}

	/** 按钮监听类 ***/
	private class Myclick implements OnClickListener {

		Intent cmdIntent = new Intent(LabelSearch.this, NFCService.class);

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.labelsearch_search:
				Log.e(TAG, "labelsearch_search");
				cmdCode = Consts.Init_14443a;
				cmdIntent.putExtra("cmd", cmdCode);
				Log.e(TAG, "7");
				startService(cmdIntent);
				anim.start();
				Log.e(TAG, "send read cmd==" + cmdCode);
				break;
			case R.id.labelsearch_start:
				Log.e(TAG, "labelsearch_start");
				anim.stop();
				cmdCode = 0;
				cmdIntent.putExtra("cmd", cmdCode);
				startService(cmdIntent);
				Intent intent = new Intent(LabelSearch.this,
						DataCheckActivity.class);
				intent.putExtra("UID", Uid);
				intent.putExtra("Latitude", Latitude);
				intent.putExtra("Longitude", Longitude);
				intent.putExtra("from", from);

				Log.e(TAG, "R.id.labelsearch_start==" + Uid);
				startActivity(intent);
				if ("Navigation".equals(from)) {
					finish();
				}
				flag = false;
				setButtonClickable(flag);
				break;
			case R.id.labelsearch_load:
				Log.e(TAG, "labelsearch_load");
				anim.stop();
				cmdCode = 0;
				cmdIntent.putExtra("cmd", cmdCode);
				startService(cmdIntent);
				Intent intent1 = new Intent(LabelSearch.this,
						DataLoadActivity.class);
				intent1.putExtra("UID", Uid);
				intent1.putExtra("Latitude", Latitude);
				intent1.putExtra("Longitude", Longitude);
				intent1.putExtra("from", from);
				Log.e(TAG, "R.id.labelsearch_load==" + Uid);
				startActivity(intent1);
				flag = false;
				setButtonClickable(flag);
				if ("Navigation".equals(from)) {
					finish();
				}
				break;
			case R.id.labelsearch_inspection:
				Log.e(TAG, "labelsearch_inspection");
				anim.stop();
				cmdCode = 0;
				cmdIntent.putExtra("cmd", cmdCode);
				startService(cmdIntent);
				Intent intent2 = new Intent(LabelSearch.this, Inspection.class);
				intent2.putExtra("UID", Uid);
				intent2.putExtra("Latitude", Latitude);
				intent2.putExtra("Longitude", Longitude);
				intent2.putExtra("from", from);
				Log.e(TAG, "R.id.labelsearch_inspection==" + Uid);
				startActivity(intent2);
				flag = false;
				setButtonClickable(flag);
				if ("Navigation".equals(from)) {
					finish();
				}
				break;
			case R.id.labelsearch_report:
				Log.e(TAG, "labelsearch_report");
				anim.stop();
				cmdCode = 0;
				cmdIntent.putExtra("cmd", cmdCode);
				startService(cmdIntent);
				Intent intent3 = new Intent(LabelSearch.this, Report.class);
				intent3.putExtra("UID", Uid);
				intent3.putExtra("Latitude", Latitude);
				intent3.putExtra("Longitude", Longitude);
				intent3.putExtra("from", from);
				Log.e(TAG, "R.id.labelsearch_report==" + Uid);
				startActivity(intent3);
				flag = false;
				setButtonClickable(flag);
				if ("Navigation".equals(from)) {
					finish();
				}
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
		start.setClickable(flag);
		load.setClickable(flag);
		inspection.setClickable(flag);
		report.setClickable(flag);
		if (!flag) {
			start.setTextColor(getResources().getColor(R.color.back));
			load.setTextColor(getResources().getColor(R.color.back));
			inspection.setTextColor(getResources().getColor(R.color.back));
			report.setTextColor(getResources().getColor(R.color.back));
		} else {
			start.setTextColor(getResources().getColor(R.color.Blue));
			load.setTextColor(getResources().getColor(R.color.Blue));
			inspection.setTextColor(getResources().getColor(R.color.Blue));
			report.setTextColor(getResources().getColor(R.color.Blue));
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

	/* 广播接收者 */
	private class MyBroadcast extends BroadcastReceiver {
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
					Utils.playMedia(LabelSearch.this);
					idInfo = receivedata.split(" ");
					identity = new IDentity();
					identity.setIdtype(idInfo[0]);
					identity.setUid(idInfo[1]);
					Log.e(TAG, "MyBroadcast UID==" + Uid);
					Uid = identity.getUid();
					if (Uid != null) {
						flag = true;
						setButtonClickable(flag);
						// 播放本地音乐
						// mplayer.start();
					}
				}
			}
		}
	}

}

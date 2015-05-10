package com.intelligent.search;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.example.nfc.util.Tools;
import com.example.uartdemo.SerialPort;
import com.intelligent.load.DataCheckActivity;
import com.intelligent.load.DataLoadActivity;
import com.intelligent.service.NFCService;
import com.intelligent.util.Utils;
import com.intenlligent.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PipeRecognition extends Activity {

	private Button detect, read, load, inspection, report;
	private ImageView image;
	private boolean flag = false;
	private AnimationDrawable anim;
	private String TAG = "PipeRecognition";
	private String Uid = null;
	private int cmdCode = 0;
	private SerialPort mSerialPort;
	private InputStream is;
	private OutputStream os;
	private int port = 13;
	private int buad = 9600;
	private RecvThread recvThread;
	private boolean isHexRecv = true;
	private boolean isOpen = false;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x01) {
				Utils.playMedia(PipeRecognition.this);
				flag = true;
				setButtonClickable(flag);
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piperecognition);
		Log.e(TAG, "onCreate");

		initView();

	}

	public void initView() {

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

	}

	public class MyClick implements OnClickListener {
		Intent cmdIntent = new Intent(PipeRecognition.this, NFCService.class);

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.piperecognition_detect:

				if (!isOpen) {

					open();
					anim.start();
					detect.setText("地标识别 ：结束");

				} else {
					anim.stop();
					close();
					detect.setText("地标识别 ：开始");

				}

				break;
			case R.id.piperecognition_read:
				Log.e(TAG, "piperecognition_read");
				anim.stop();

				Intent intent = new Intent(PipeRecognition.this,
						DataCheckActivity.class);
				intent.putExtra("UID",
						Uid.subSequence(Uid.length() - 10, Uid.length()));
				startActivity(intent);
				setButtonClickable(flag);
				break;
			case R.id.piperecognition_load:
				Log.e(TAG, "piperecognition_load");
				anim.stop();

				Intent intent1 = new Intent(PipeRecognition.this,
						DataLoadActivity.class);
				intent1.putExtra("UID",
						Uid.subSequence(Uid.length() - 10, Uid.length()));
				startActivity(intent1);
				setButtonClickable(flag);
				break;

			case R.id.piperecognition_inspection:
				Log.e(TAG, "piperecognition_inspection");
				anim.stop();
				Intent intent2 = new Intent(PipeRecognition.this,
						Inspection.class);
				intent2.putExtra("UID",
						Uid.subSequence(Uid.length() - 10, Uid.length()));
				startActivity(intent2);
				setButtonClickable(flag);
				break;
			case R.id.piperecognition_report:
				Log.e(TAG, "piperecognition_report");
				anim.stop();

				Intent intent3 = new Intent(PipeRecognition.this, Report.class);
				intent3.putExtra("UID",
						Uid.subSequence(Uid.length() - 10, Uid.length()));

				startActivity(intent3);
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
		detect.setText("地标识别 ：开始");
		flag = false;
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
			read.setTextColor(getResources().getColor(R.color.Blue));
			load.setTextColor(getResources().getColor(R.color.Blue));
			inspection.setTextColor(getResources().getColor(R.color.Blue));
			report.setTextColor(getResources().getColor(R.color.Blue));

		}

	}

	/**
	 * recv thread receive serialport data
	 * 
	 * @author Administrator
	 * 
	 */
	private class RecvThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				while (!isInterrupted()) {
					int size = 0;
					byte[] buffer = new byte[1024];
					if (is == null) {
						return;
					}
					size = is.read(buffer);

					if (size > 0) {

						onDataReceived(buffer, size);
					}
					Thread.sleep(10);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * add recv data on UI
	 * 
	 * @param buffer
	 * @param size
	 */
	private void onDataReceived(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (size > 2) {

					if (isHexRecv) {
						Message msg = mHandler.obtainMessage(0x01);
						Uid = Tools.Bytes2HexString(buffer, size);
						mHandler.sendMessage(msg);

					}

				}

			}
		});
	}

	/**
	 * open serial port
	 */
	private void open() {
		Log.e(TAG, "open-----------------");
		try {
			mSerialPort = new SerialPort(port, buad, 0);
			Log.e(TAG, "mSerialPort:----------" + mSerialPort.toString());
		} catch (Exception e) {

			Toast.makeText(this, "SerialPort init fail!!", 0).show();
			return;
		}
		is = mSerialPort.getInputStream();
		os = mSerialPort.getOutputStream();

		mSerialPort.rfid_poweron();

		recvThread = new RecvThread();
		recvThread.start();
		isOpen = true;
		Toast.makeText(this, "SerialPort open success", Toast.LENGTH_SHORT)
				.show();

	}

	/**
	 * close serial port
	 */
	private void close() {
		Log.e(TAG, "close------------>>>>");
		if (recvThread != null) {
			recvThread.interrupt();
		}
		if (mSerialPort != null) {

			mSerialPort.rfid_poweroff();

			try {
				is.close();
				os.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}
			mSerialPort.close(port);
			isOpen = false;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (isOpen) {
			this.close();
		}

	}

	@Override
	protected void onDestroy() {
		if (isOpen) {
			this.close();
		}
		super.onDestroy();
	}

}

package com.intelligent.load;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligent.greendao.DaoMaster;
import com.intelligent.greendao.DaoMaster.DevOpenHelper;
import com.intelligent.greendao.DaoSession;
import com.intelligent.greendao.biaoqian;
import com.intelligent.greendao.biaoqianDao;
import com.intelligent.greendao.dianli;
import com.intelligent.greendao.dianliDao;
import com.intelligent.greendao.gdlxDao;
import com.intelligent.greendao.guandianDao;
import com.intelligent.greendao.gxlbDao;
import com.intelligent.util.Global;
import com.intenlligent.R;

import de.greenrobot.dao.query.QueryBuilder;

public class DataLoadActivity extends Activity {

	private String TAG = "DataLoadActivity";
	private String Uid;
	private String TIME, road_info = null, remark = null, username = null,
			zhanming = null, tiaoxuhao = null, length = null,
			startpoint = null, endpoint = null, buildtime = null, type = null;
	private TextView UID, Label_time;
	private EditText Latitude, Longitude, ZhanMing, Road_info, TiaoXuHao,
			Length, StartPoint, EndPoint, Type, BuildTime, Remark;
	private Button load;
	// private Spinner spinner1, spinner2;
	// private String category1[];
	// private String category2[];
	// private int location1 = 0;
	// private int location2 = 0;
	private RadioGroup radiogroup;
	private MediaPlayer player;
	private LocationManager locationManager;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private gdlxDao mgdlxDao;
	private gxlbDao mgxlbDao;
	private guandianDao mguandianDao;
	private biaoqianDao mbiaoqianDao;
	private dianliDao mdianliDao;
	private Global global;
	private Location location;
	private MyThread myThread;
	private boolean Flag_Navigation = false;
	private Double Latitude_Intent = -1d, Longitude_Intent = -1d;
	private String from = null;
	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {

			if (msg.what == 0x123) {
				// 为什么没有获取到经纬度信息？？？？
				Toast.makeText(DataLoadActivity.this, "获取经纬度失败,请重新获取！！",
						Toast.LENGTH_SHORT).show();
			}
			if (msg.what == 0x223) {

				Longitude.setText(""
						+ ((int) (location.getLongitude() * 1E6) * 1.0) / 1E6);
				Latitude.setText(""
						+ ((int) (location.getLatitude() * 1E6) * 1.0) / 1E6);
				// player.prepare();
				// 报错prepareAsync called in state 8
				// 因为create方法已经帮你prepare了,所以你只需要start就可以了
				player.start();
			}

		};
	};

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.e(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dataload);

		DevOpenHelper myHelper = new DevOpenHelper(DataLoadActivity.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mgdlxDao = daoSession.getGdlxDao();
		mgxlbDao = daoSession.getGxlbDao();
		mguandianDao = daoSession.getGuandianDao();
		mbiaoqianDao = daoSession.getBiaoqianDao();
		mdianliDao = daoSession.getDianliDao();

		// QueryBuilder<gdlx> qb = mgdlxDao.queryBuilder();
		// qb.orderAsc(gdlxDao.Properties.Gdlxcode);
		// int count = (int) qb.buildCount().count();
		// category1 = new String[count];
		// for (int i = 0; i < count; i++) {
		// category1[i] = qb.list().get(i).getGdlxname();
		// }
		// Log.e(TAG, "gdlx----------------->>>" + qb.list());
		//
		// QueryBuilder<gxlb> qb1 = mgxlbDao.queryBuilder();
		// qb1.orderAsc(gxlbDao.Properties.Gxlbcode);
		// int count1 = (int) qb1.buildCount().count();
		// category2 = new String[count1];
		// for (int i = 0; i < count1; i++) {
		// category2[i] = qb1.list().get(i).getGxlbname();
		// }
		// Log.e(TAG, "gxlb----------------->>>" + qb1.list());

		global = (Global) getApplication();
		username = global.getUsername();
		Log.e(TAG, "global:" + username);

		// 创建LocationManager对象
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 当定位成功时，播放声音；
		player = MediaPlayer.create(DataLoadActivity.this, R.raw.thunder);
		// 判断GPS是否开启；
		openGPSSeting();

		UID = (TextView) findViewById(R.id.dataload_ID);
		Label_time = (TextView) findViewById(R.id.dataload_load_time);
		BuildTime = (EditText) findViewById(R.id.dataload_build_time);
		ZhanMing = (EditText) findViewById(R.id.dataload_pointname);
		Road_info = (EditText) findViewById(R.id.dataload_roadname);
		TiaoXuHao = (EditText) findViewById(R.id.dataload_tiaoxuhao);
		Length = (EditText) findViewById(R.id.dataload_changdu);
		StartPoint = (EditText) findViewById(R.id.dataload_startpoint);
		EndPoint = (EditText) findViewById(R.id.dataload_endpoint);
		Type = (EditText) findViewById(R.id.dataload_type);
		Remark = (EditText) findViewById(R.id.dataload_beizhu);
		Latitude = (EditText) findViewById(R.id.dataload_latitude);
		Longitude = (EditText) findViewById(R.id.dataload_longitude);
		load = (Button) findViewById(R.id.dataload_load);

		Uid = getIntent().getStringExtra("UID");
		Latitude_Intent = this.getIntent().getDoubleExtra("Latitude", -1d);
		Longitude_Intent = this.getIntent().getDoubleExtra("Longitude", -1d);
		from = this.getIntent().getStringExtra("from");
		Log.e(TAG, "onCreate:" + Uid);
		if (Uid != null) {
			UID.setText(Uid);
		}
		if (Latitude_Intent != -1d) {
			Latitude.setText(String.valueOf(Latitude_Intent));
		}
		if (Longitude_Intent != -1d) {
			Longitude.setText(String.valueOf(Longitude_Intent));
		}
		// 设置时间格式；
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TIME = df.format(new Date());
		Label_time.setText(TIME);
		// spinner1 = (Spinner) findViewById(R.id.dataload_spinner_industry);
		// spinner2 = (Spinner) findViewById(R.id.dataload_spinner_type);

		// ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
		// DataLoadActivity.this,
		// android.R.layout.simple_spinner_dropdown_item, category2);

		// spinner1.setAdapter(adapter1);
		// ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
		// DataLoadActivity.this,
		// android.R.layout.simple_spinner_dropdown_item, category1);
		// 设置spinner默认选项
		// spinner1.setSelection(1, true);
		// spinner2.setAdapter(adapter2);
		// spinner2.setSelection(1, true);
		// spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// location1 = position;
		//
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parent) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		// spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// location2 = position;
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parent) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		// 判断是否进行自动获取经纬度；
		radiogroup = (RadioGroup) findViewById(R.id.dataload_radioGroup);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.dataload_radio_yes) {

					Toast.makeText(DataLoadActivity.this, "正在定位...",
							Toast.LENGTH_SHORT).show();
					// 根据条件筛选LocationProvider；
					// Criteria criteria = new Criteria();
					// criteria.setAccuracy(Criteria.ACCURACY_FINE);// 精度高
					// criteria.setAltitudeRequired(false);// 不需要获取高度
					// criteria.setBearingRequired(false);// 不需要提供方向
					// criteria.setCostAllowed(true);// 必须是免费的
					// criteria.setPowerRequirement(Criteria.POWER_LOW);// 低功耗
					// String provider =
					// locationManager.getBestProvider(criteria,
					// true);
					// Log.e(TAG, "provider:" + provider);
					// 筛选出最好的地图信息提供者；
					// 从GPS获取最近的定位信息；
					// location = locationManager
					// .getLastKnownLocation(LocationManager.GPS_PROVIDER);
					// 对于获取到的Location值进行判断；

					myThread = new MyThread();
					myThread.start();
					Flag_Navigation = true;

				} else if (checkedId == R.id.dataload_radio_no) {
					Flag_Navigation = false;
				}

			}
		});

		// 是否数据录入
		load.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder builder = new Builder(DataLoadActivity.this);
				builder.setTitle("提示信息");
				builder.setMessage("是否保存标签信息?");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (!findUID(Uid)) {

									road_info = Road_info.getText().toString()
											.trim();
									remark = Remark.getText().toString().trim();

									zhanming = ZhanMing.getText().toString()
											.trim();
									tiaoxuhao = TiaoXuHao.getText().toString()
											.trim();
									length = Length.getText().toString().trim();
									startpoint = StartPoint.getText()
											.toString().trim();
									endpoint = EndPoint.getText().toString()
											.trim();
									buildtime = BuildTime.getText().toString()
											.trim();
									type = Type.getText().toString().trim();

									if (Flag_Navigation) {

										if (location == null
												&& (Longitude.getText()
														.toString().trim()
														.equals("") || Latitude
														.getText().toString()
														.trim().equals(""))) {

											Toast.makeText(
													DataLoadActivity.this,
													"经纬度获取失败,请重新获取！",
													Toast.LENGTH_SHORT).show();
										} else {

											dianli mdianli = new dianli();
											mdianli.setBiaoqiancode(Uid);
											mdianli.setZhanming(zhanming);
											mdianli.setLuming(road_info);
											mdianli.setTiaoxuhao(tiaoxuhao);
											mdianli.setChangdu(length);
											mdianli.setQidian(startpoint);
											mdianli.setZhongdian(endpoint);
											mdianli.setXinghao(type);
											mdianli.setBangdingshijian(TIME);
											mdianli.setFadianshijian(buildtime);
											mdianli.setBeizhu(remark);
											mdianli.setJingdu(Longitude
													.getText().toString()
													.trim());
											mdianli.setWeidu(Latitude.getText()
													.toString().trim());

											mdianliDao.insert(mdianli);

											biaoqian mbiaoqian = new biaoqian();
											mbiaoqian.setBiaoqiancode(Uid);
											mbiaoqian.setJingdu(Longitude
													.getText().toString()
													.trim());
											mbiaoqian.setWeidu(Latitude
													.getText().toString()
													.trim());
											mbiaoqian.setBiaoqianleibie("标牌");
											mbiaoqianDao.insert(mbiaoqian);
											Toast.makeText(
													DataLoadActivity.this,
													"数据加载成功 !",
													Toast.LENGTH_SHORT).show();
											finish();

										}
									} else {
										dianli mdianli = new dianli();
										mdianli.setBiaoqiancode(Uid);
										mdianli.setZhanming(zhanming);
										mdianli.setLuming(road_info);
										mdianli.setTiaoxuhao(tiaoxuhao);
										mdianli.setChangdu(length);
										mdianli.setQidian(startpoint);
										mdianli.setZhongdian(endpoint);
										mdianli.setXinghao(type);
										mdianli.setBangdingshijian(TIME);
										mdianli.setFadianshijian(buildtime);
										mdianli.setBeizhu(remark);
										mdianli.setJingdu(Longitude.getText()
												.toString().trim());
										mdianli.setWeidu(Latitude.getText()
												.toString().trim());

										mdianliDao.insert(mdianli);

										biaoqian mbiaoqian = new biaoqian();
										mbiaoqian.setBiaoqiancode(Uid);
										mbiaoqian.setJingdu(Longitude.getText()
												.toString().trim());
										mbiaoqian.setWeidu(Latitude.getText()
												.toString().trim());
										mbiaoqian.setBiaoqianleibie("标牌");
										mbiaoqianDao.insert(mbiaoqian);
										Toast.makeText(DataLoadActivity.this,
												"数据加载成功 !", Toast.LENGTH_SHORT)
												.show();
										finish();

									}

								} else {
									Toast.makeText(DataLoadActivity.this,
											"标签：" + Uid + "已经存在！",
											Toast.LENGTH_SHORT).show();
								}

							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				builder.create().show();
			}
		});
	}

	// 新建线程类；
	public class MyThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Message message = new Message();

			for (int i = 0; i < 1000; i++) {
				location = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				Log.e(TAG, "GPS_PROVIDER == " + location);
				if (location != null) {
					message.what = 0x223;
					break;
				}
			}

			if (location == null) {
				message.what = 0x123;
			}
			handler.sendMessage(message);
			Log.e(TAG, "sendMessage:" + message.what);

		}
	}

	// 判断标签ID是否存在于数据库；
	public boolean findUID(String Uid) {
		Log.e(TAG, " boolean findUID(String Uid)----->" + Uid);
		QueryBuilder<dianli> qb = mdianliDao.queryBuilder();
		qb.where(dianliDao.Properties.Biaoqiancode.eq(Uid));
		if (qb.buildCount().count() > 0)
			return true;
		return false;
	}

	public void openGPSSeting() {
		if (!locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(DataLoadActivity.this, "请开启GPS模块",
					Toast.LENGTH_SHORT).show();
			Builder builder = new Builder(DataLoadActivity.this);
			builder.setTitle("显示“我的位置”");
			builder.setMessage("是否开启GPS定位设置");
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			builder.setPositiveButton("设置",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(
									ACTION_LOCATION_SOURCE_SETTINGS);
							startActivityForResult(intent, 0);
						}
					});
			builder.create().show();

		} else {
			System.out.println("GPS模块已经开启！");
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (player != null)
			player.release();
		if (db != null) {
			db.close();
			db = null;
		}
	}

}

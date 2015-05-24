package com.intelligent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.intelligent.greendao.DaoMaster;
import com.intelligent.greendao.DaoMaster.DevOpenHelper;
import com.intelligent.greendao.DaoSession;
import com.intelligent.greendao.biaoqian;
import com.intelligent.greendao.biaoqianDao;
import com.intelligent.greendao.dianli;
import com.intelligent.greendao.dianliDao;
import com.intelligent.greendao.guzhangshenbao;
import com.intelligent.greendao.guzhangshenbaoDao;
import com.intelligent.greendao.user;
import com.intelligent.greendao.userDao;
import com.intelligent.greendao.xuncha;
import com.intelligent.greendao.xunchaDao;
import com.intelligent.search.InspectionRecord;
import com.intelligent.search.ReportRecord;
import com.intelligent.util.AppInfo;
import com.intenlligent.R;

import de.greenrobot.dao.query.QueryBuilder;

public class Fragment04 extends Fragment {

	private TextView Username;
	private String username;
	private Button inspection, fault, information, save_info;
	private String TAG = "Fragment04";
	private View parentView;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private userDao muserDao;
	private biaoqianDao mbiaoqianDao;
	private dianliDao mdianliDao;
	private xunchaDao mxunchaDao;
	private guzhangshenbaoDao mguzhangshenbaoDao;
	private File file;
	private List<AppInfo> myApps;
	private String Flag1 = "gpstest";

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		DevOpenHelper myHelper = new DevOpenHelper(activity,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mdianliDao = daoSession.getDianliDao();
		muserDao = daoSession.getUserDao();
		mbiaoqianDao = daoSession.getBiaoqianDao();
		mxunchaDao = daoSession.getXunchaDao();
		mguzhangshenbaoDao = daoSession.getGuzhangshenbaoDao();
		myApps = new ArrayList<AppInfo>();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e(TAG, "onCreate");
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.e(TAG, "onCreateView");
		parentView = inflater.inflate(R.layout.fragment04, container, false);
		Username = (TextView) parentView.findViewById(R.id.fragment04_username);

		inspection = (Button) parentView
				.findViewById(R.id.fragment04_inspection);
		fault = (Button) parentView.findViewById(R.id.fragment04_fault);
		information = (Button) parentView
				.findViewById(R.id.fragment04_information);
		save_info = (Button) parentView
				.findViewById(R.id.fragment04_saveinformation);
		inspection.setOnClickListener(new MyClick());
		fault.setOnClickListener(new MyClick());
		information.setOnClickListener(new MyClick());
		save_info.setOnClickListener(new MyClick());
		username = getActivity().getIntent().getStringExtra("username");
		Username.setText(username);

		return parentView;
	}

	public class MyClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.fragment04_inspection:
				Intent intent = new Intent(getActivity(),
						InspectionRecord.class);
				startActivity(intent);
				break;
			case R.id.fragment04_fault:
				Intent intent1 = new Intent(getActivity(), ReportRecord.class);
				startActivity(intent1);
				break;
			case R.id.fragment04_information:
				queryAppInfo();
				Log.e(TAG, "myApps--->>" + myApps.size());
				if (myApps.size() > 0) {
					Intent intent2 = myApps.get(0).getIntent();
					startActivity(intent2);
				} else {
					Toast.makeText(getActivity(), "系统不存在GPS检测软件！",
							Toast.LENGTH_SHORT).show();
				}

				break;
			case R.id.fragment04_saveinformation:
				String sdState = Environment.getExternalStorageState();
				file = new File(Environment.getExternalStorageDirectory()
						+ "/DCIM/record/");
				if (file != null) {
					file.mkdirs();
				}
				Log.e(TAG, "fragment04_saveinformation----->>>>");
				if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
					Toast.makeText(getActivity(), "sdcard尚未加载！",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					LoadData_user();
					LoadData_biaoqian();
					LoadData_dianli();
					LoadData_xuncha();
					LoadData_guzhangshenbao();
					Toast.makeText(getActivity(), "保存数据完毕！", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			default:
				break;
			}
		}

	}

	public void LoadData_user() {
		Log.e(TAG, "LoadData_user------>>>>>");
		QueryBuilder<user> qb = muserDao.queryBuilder();
		if (qb.buildCount().count() > 0) {
			// 注意路径——绝对路径和相对路径；以及不要忘记"/"
			String filename = file.getAbsolutePath() + "/" + "user.txt";
			Log.e(TAG, "filename---->>>" + filename);
			String jsonstring = JSON.toJSONString(qb.list());
			byte[] mbyte = jsonstring.getBytes();
			try {
				FileOutputStream mfile = new FileOutputStream(filename);
				try {
					mfile.write(mbyte);
					Log.e(TAG, "mbyte---->>>" + mbyte.length);
					mfile.flush();
					mfile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void LoadData_biaoqian() {
		Log.e(TAG, "LoadData_biaoqian------>>>>>");
		QueryBuilder<biaoqian> qb = mbiaoqianDao.queryBuilder();
		if (qb.buildCount().count() > 0) {
			// 注意路径——绝对路径和相对路径；以及不要忘记"/"
			String filename = file.getAbsolutePath() + "/" + "biaoqian.txt";
			Log.e(TAG, "filename---->>>" + filename);
			String jsonstring = JSON.toJSONString(qb.list());
			byte[] mbyte = jsonstring.getBytes();
			try {
				FileOutputStream mfile = new FileOutputStream(filename);
				try {
					mfile.write(mbyte);
					Log.e(TAG, "mbyte---->>>" + mbyte.length);
					mfile.flush();
					mfile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void LoadData_dianli() {
		Log.e(TAG, "LoadData_dianli------>>>>>");
		QueryBuilder<dianli> qb = mdianliDao.queryBuilder();
		if (qb.buildCount().count() > 0) {
			// 注意路径——绝对路径和相对路径；以及不要忘记"/"
			String filename = file.getAbsolutePath() + "/" + "danli.txt";
			Log.e(TAG, "filename---->>>" + filename);
			String jsonstring = JSON.toJSONString(qb.list());
			byte[] mbyte = jsonstring.getBytes();
			try {
				FileOutputStream mfile = new FileOutputStream(filename);
				try {
					mfile.write(mbyte);
					Log.e(TAG, "mbyte---->>>" + mbyte.length);
					mfile.flush();
					mfile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void LoadData_xuncha() {
		Log.e(TAG, "LoadData_xuncha------>>>>>");
		QueryBuilder<xuncha> qb = mxunchaDao.queryBuilder();
		if (qb.buildCount().count() > 0) {
			// 注意路径——绝对路径和相对路径；以及不要忘记"/"
			String filename = file.getAbsolutePath() + "/" + "xuncha.txt";
			Log.e(TAG, "filename---->>>" + filename);
			String jsonstring = JSON.toJSONString(qb.list());
			byte[] mbyte = jsonstring.getBytes();
			try {
				FileOutputStream mfile = new FileOutputStream(filename);
				try {
					mfile.write(mbyte);
					Log.e(TAG, "mbyte---->>>" + mbyte.length);
					mfile.flush();
					mfile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void LoadData_guzhangshenbao() {
		Log.e(TAG, "LoadData_guzhangshenbao------>>>>>");
		QueryBuilder<guzhangshenbao> qb = mguzhangshenbaoDao.queryBuilder();
		if (qb.buildCount().count() > 0) {
			// 注意路径——绝对路径和相对路径；以及不要忘记"/"
			String filename = file.getAbsolutePath() + "/"
					+ "guzhangshenbao.txt";
			Log.e(TAG, "filename---->>>" + filename);
			String jsonstring = JSON.toJSONString(qb.list());
			byte[] mbyte = jsonstring.getBytes();
			try {
				FileOutputStream mfile = new FileOutputStream(filename);
				try {
					mfile.write(mbyte);
					Log.e(TAG, "mbyte---->>>" + mbyte.length);
					mfile.flush();
					mfile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void queryAppInfo() {
		Log.e(TAG, "queryAppInfo() ----->>>>");
		// 获得PackageManager对象；用于管理软件；
		PackageManager mPackageManager = getActivity().getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// 通过查询，获得所有ResolveInfo对象；
		List<ResolveInfo> resolveInfos = mPackageManager.queryIntentActivities(
				mainIntent, 0);
		// 调用系统排序，根据name排序
		// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序；
		Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(
				mPackageManager));
		Log.e(TAG, "Collections.sort");

		if (myApps != null) {
			myApps.clear();
			for (ResolveInfo reInfo : resolveInfos) {
				// 获得该应用程序的启动Activity的Name；
				String activityName = reInfo.activityInfo.name;
				// 获得应用程序的包名；
				String pkgName = reInfo.activityInfo.packageName;
				// 获得应用程序的Label
				String appLabel = (String) reInfo.loadLabel(mPackageManager);
				// 获得应用程序的程序图标；
				Drawable icon = reInfo.loadIcon(mPackageManager);
				if (pkgName.contains(Flag1)) {
					// 为应用程序启动Activity做准备；
					Intent lanchIntent = new Intent();
					lanchIntent.setComponent(new ComponentName(pkgName,
							activityName));
					// 创建一个AppInfo对象并赋值；
					AppInfo appInfo = new AppInfo();
					appInfo.setAppLabel(appLabel);
					appInfo.setPkgName(pkgName);
					appInfo.setAppIcon(icon);
					appInfo.setIntent(lanchIntent);
					myApps.add(appInfo);
				}

			}
		}
	}
}

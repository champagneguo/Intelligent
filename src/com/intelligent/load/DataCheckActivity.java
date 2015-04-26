package com.intelligent.load;

import com.intelligent.greendao.DaoMaster;
import com.intelligent.greendao.DaoSession;
import com.intelligent.greendao.dianli;
import com.intelligent.greendao.dianliDao;
import com.intelligent.greendao.DaoMaster.DevOpenHelper;
import com.intenlligent.R;
import de.greenrobot.dao.query.QueryBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DataCheckActivity extends Activity {

	private String TAG = "DataCheckActivity";
	private TextView Label_id, Longitude, Latitude, Zhanming, Road_info,
			Tiaoxuhao, Length, StartPoint, EndPoint, Type, BuildTime,
			Label_time, Remark;
	private String label_id;
	private Button Close;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private dianliDao mdianliDao;
	private dianli mdianli;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_datacheck);
		Log.e(TAG, "onCreate");

		Label_id = (TextView) findViewById(R.id.datacheck_ID);
		Longitude = (TextView) findViewById(R.id.datacheck_longitude);
		Latitude = (TextView) findViewById(R.id.datacheck_latitude);
		Zhanming = (TextView) findViewById(R.id.datacheck_pointname);
		Road_info = (TextView) findViewById(R.id.datacheck_roadname);
		Tiaoxuhao = (TextView) findViewById(R.id.datacheck_tiaoxuhao);
		Length = (TextView) findViewById(R.id.datacheck_changdu);
		Log.e(TAG, "Length______-->>>");
		StartPoint = (TextView) findViewById(R.id.datacheck_startpoint);
		EndPoint = (TextView) findViewById(R.id.datacheck_endpoint);
		Type = (TextView) findViewById(R.id.datacheck_type);
		BuildTime = (TextView) findViewById(R.id.datacheck_build_time);
		Label_time = (TextView) findViewById(R.id.datacheck_load_time);
		Remark = (TextView) findViewById(R.id.datacheck_remark);
		Close = (Button) findViewById(R.id.datacheck_close);

		Log.e(TAG, "Close----->>>>");

		DevOpenHelper myHelper = new DevOpenHelper(DataCheckActivity.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mdianliDao = daoSession.getDianliDao();
		mdianli = new dianli();

		label_id = getIntent().getStringExtra("UID");
		Log.e(TAG, "onCreate:Uid:" + label_id);
		if (label_id != null) {
			Label_id.setText(label_id);
		}

		if (findUID(label_id)) {
			Log.e(TAG, "findUID(rfid)------------->>>>" + label_id);

			Longitude.setText(mdianli.getJingdu());
			Latitude.setText(mdianli.getWeidu());
			Zhanming.setText(mdianli.getZhanming());
			Road_info.setText(mdianli.getLuming());
			Tiaoxuhao.setText(mdianli.getTiaoxuhao());
			Length.setText(mdianli.getChangdu());
			StartPoint.setText(mdianli.getQidian());
			EndPoint.setText(mdianli.getZhongdian());
			Type.setText(mdianli.getXinghao());
			BuildTime.setText(mdianli.getFadianshijian());
			Label_time.setText(mdianli.getBangdingshijian());
			Remark.setText(mdianli.getBeizhu());

		} else {
			Toast.makeText(DataCheckActivity.this, "未查询到相关记录！",
					Toast.LENGTH_SHORT).show();
		}
		Close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});
	}

	// 判断标签ID是否存在于数据库；
	public boolean findUID(String Uid) {
		Log.e(TAG, " boolean findUID(String Uid)----->" + Uid);
		QueryBuilder<dianli> qb = mdianliDao.queryBuilder();
		qb.where(dianliDao.Properties.Biaoqiancode.eq(Uid));
		Log.e(TAG, "qb------->>>>>" + qb.buildCount().count());
		if (qb.buildCount().count() > 0) {
			mdianli = qb.list().get(0);
			return true;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (db != null) {
			db.close();
			db = null;
		}
	}
}

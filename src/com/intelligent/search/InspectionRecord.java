package com.intelligent.search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.intelligent.greendao.DaoMaster;
import com.intelligent.greendao.DaoMaster.DevOpenHelper;
import com.intelligent.greendao.DaoSession;
import com.intelligent.greendao.xuncha;
import com.intelligent.greendao.xunchaDao;
import com.intenlligent.R;

import de.greenrobot.dao.query.QueryBuilder;

public class InspectionRecord extends Activity {

	private String TAG = "InspectionRecord";
	private ListView lv;
	private InspectionRecordAdapter adapter;
	private List<ArrayList<String>> lists;
	private Button ArrowBack;
	private TextView Back;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private xunchaDao mxunchaDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 隐去标题栏（应用程序的名字）
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.inspection_record);
		Log.e(TAG, "oncreate");

		DevOpenHelper myHelper = new DevOpenHelper(InspectionRecord.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mxunchaDao = daoSession.getXunchaDao();

		ArrowBack = (Button) findViewById(R.id.inspection_record_arrowback);
		Back = (TextView) findViewById(R.id.inspection_record_back);
		lv = (ListView) findViewById(R.id.inspection_record_lv);
		lists = new ArrayList<ArrayList<String>>();

		lists = findAllElements();
		adapter = new InspectionRecordAdapter(InspectionRecord.this, lists);
		lv.setAdapter(adapter);

		ArrowBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		lv.setOnItemClickListener(adapter);

	}

	public List<ArrayList<String>> findAllElements() {
		Log.e(TAG, "findAllElements");

		QueryBuilder<xuncha> qb = mxunchaDao.queryBuilder();
		qb.orderAsc(xunchaDao.Properties.Xunchaid);
		for (int i = 0; i < qb.buildCount().count(); i++) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(String.valueOf(qb.list().get(i).getXunchaid()));
			list.add(qb.list().get(i).getXuncharenyuan());
			list.add(qb.list().get(i).getXunchaqingkuang());
			list.add(qb.list().get(i).getXunchariqi());
			lists.add(list);
		}

		return lists;
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

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
import com.intelligent.greendao.guzhangshenbao;
import com.intelligent.greendao.guzhangshenbaoDao;
import com.intenlligent.R;

import de.greenrobot.dao.query.QueryBuilder;

public class ReportRecord extends Activity {

	private String TAG = "ReportRecord";
	private ListView lv;
	private ReportRecordAdapter adapter;
	private List<ArrayList<String>> lists;
	private Button ArrowBack;
	private TextView Back;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private guzhangshenbaoDao mguzhangshebaoDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.report_record);
		Log.e(TAG, "oncreate");
		DevOpenHelper myHelper = new DevOpenHelper(ReportRecord.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mguzhangshebaoDao = daoSession.getGuzhangshenbaoDao();

		ArrowBack = (Button) findViewById(R.id.report_record_arrowback);
		Back = (TextView) findViewById(R.id.report_record_back);
		lv = (ListView) findViewById(R.id.report_record_lv);
		lists = new ArrayList<ArrayList<String>>();
		lists = findAllElements();
		adapter = new ReportRecordAdapter(ReportRecord.this, lists);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(adapter);
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

	}

	public List<ArrayList<String>> findAllElements() {

		QueryBuilder<guzhangshenbao> qb = mguzhangshebaoDao.queryBuilder();
		qb.orderAsc(guzhangshenbaoDao.Properties.Shenbaoid);
		for (int i = 0; i < qb.buildCount().count(); i++) {
			ArrayList<String> list = new ArrayList<String>();
			// list.add(qb.list().get(i).getShenbaoid() + "");
			list.add(qb.list().get(i).getGuzhangleixing());
			list.add(qb.list().get(i).getShenbaorenyuan());
			list.add(qb.list().get(i).getShenbaoriqi());
			list.add(String.valueOf(qb.list().get(i).getShenbaoid()));
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

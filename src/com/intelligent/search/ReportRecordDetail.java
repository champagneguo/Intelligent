package com.intelligent.search;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligent.greendao.DaoMaster;
import com.intelligent.greendao.DaoMaster.DevOpenHelper;
import com.intelligent.greendao.DaoSession;
import com.intelligent.greendao.guzhangshenbao;
import com.intelligent.greendao.guzhangshenbaoDao;
import com.intenlligent.R;

import de.greenrobot.dao.query.QueryBuilder;

public class ReportRecordDetail extends Activity {

	private String TAG = "InspectionRecordDetail";
	private TextView mTextView_ID, mTextView_Time, mTextView_Name,
			mTextView_Add, mTextView_Result, mTextView_IsPicture;
	private ImageView mImageView;
	private Button mButton_Close;
	private String mImageView_path;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private guzhangshenbaoDao mguzhanshenbaoDao;
	private String mShenBaoId;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reportrecord_detail);

		mTextView_ID = (TextView) findViewById(R.id.reportdetail_detail_ID);
		mTextView_Time = (TextView) findViewById(R.id.reportdetail_detail_time);
		mTextView_Name = (TextView) findViewById(R.id.reportdetail_detail_name);
		mTextView_Add = (TextView) findViewById(R.id.reportdetail_detail_add);
		mTextView_Result = (TextView) findViewById(R.id.reportdetail_detail_result);
		mTextView_IsPicture = (TextView) findViewById(R.id.reportdetail_is_picture);
		mImageView = (ImageView) findViewById(R.id.reportdetail_imageView);
		mButton_Close = (Button) findViewById(R.id.reportdetail_stop);
		mButton_Close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		mShenBaoId = this.getIntent().getStringExtra("ShenBaoId");

		DevOpenHelper myHelper = new DevOpenHelper(ReportRecordDetail.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mguzhanshenbaoDao = daoSession.getGuzhangshenbaoDao();
		Log.e(TAG, "mXunChaID------>>>>" + mShenBaoId);
		QueryBuilder<guzhangshenbao> qb = mguzhanshenbaoDao.queryBuilder()
				.where(guzhangshenbaoDao.Properties.Shenbaoid.eq(Long
						.valueOf(mShenBaoId)));
		if (qb.buildCount().count() == 0) {

			Toast.makeText(ReportRecordDetail.this, "", Toast.LENGTH_SHORT)
					.show();

		} else {

			mTextView_ID.setText(qb.list().get(0).getShenbaobiaoqianid());
			mTextView_Time.setText(qb.list().get(0).getShenbaoriqi());
			mTextView_Name.setText(qb.list().get(0).getShenbaorenyuan());
			mTextView_Add.setText(qb.list().get(0).getGuzhangshuoming());
			mTextView_Result.setText(qb.list().get(0).getGuzhangleixing());

			mImageView_path = qb.list().get(0).getZhaopianurl();
			if (mImageView_path == null || mImageView_path.equals("")) {

				mTextView_IsPicture.setText("否");

				Toast.makeText(ReportRecordDetail.this, "图片资源不存在",
						Toast.LENGTH_SHORT).show();

			} else {
				mTextView_IsPicture.setText("是");

				try {

					BitmapFactory.Options options = new Options();
					options.inSampleSize = 4;
					bitmap = BitmapFactory.decodeFile(mImageView_path, options);
					mImageView.setImageBitmap(bitmap);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (bitmap != null) {
			bitmap.recycle();
			bitmap = null;
		}
	}

}

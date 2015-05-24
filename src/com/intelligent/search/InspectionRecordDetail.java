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
import com.intelligent.greendao.xuncha;
import com.intelligent.greendao.xunchaDao;
import com.intenlligent.R;

import de.greenrobot.dao.query.QueryBuilder;

public class InspectionRecordDetail extends Activity {

	private String TAG = "InspectionRecordDetail";
	private TextView mTextView_ID, mTextView_Time, mTextView_Name,
			mTextView_Add, mTextView_Result, mTextView_IsPicture;
	private ImageView mImageView;
	private Button mButton_Close;
	private String mImageView_path;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private xunchaDao mxunchaDao;
	private String mXunChaID;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_inspection_detail);

		mTextView_ID = (TextView) findViewById(R.id.inspection_detail_ID);
		mTextView_Time = (TextView) findViewById(R.id.inspection_detail_time);
		mTextView_Name = (TextView) findViewById(R.id.inspection_detail_name);
		mTextView_Add = (TextView) findViewById(R.id.inspection_detail_add);
		mTextView_Result = (TextView) findViewById(R.id.inspection_detail_result);
		mTextView_IsPicture = (TextView) findViewById(R.id.inspection_detail_is_picture);
		mImageView = (ImageView) findViewById(R.id.inspection_detail_imageView);
		mButton_Close = (Button) findViewById(R.id.inspection_detail_stop);
		mButton_Close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});
		DevOpenHelper myHelper = new DevOpenHelper(InspectionRecordDetail.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mxunchaDao = daoSession.getXunchaDao();
		mXunChaID = this.getIntent().getStringExtra("XunChaId");

		Log.e(TAG, "mXunChaID------>>>>" + mXunChaID);
		QueryBuilder<xuncha> qb = mxunchaDao.queryBuilder().where(
				xunchaDao.Properties.Xunchaid.eq(Long.valueOf(mXunChaID)));
		if (qb.buildCount().count() == 0) {

			Toast.makeText(InspectionRecordDetail.this, "", Toast.LENGTH_SHORT)
					.show();

		} else {

			mTextView_ID.setText(qb.list().get(0).getXunchabiaoqianid());
			mTextView_Time.setText(qb.list().get(0).getXunchariqi());
			mTextView_Name.setText(qb.list().get(0).getXuncharenyuan());
			mTextView_Add.setText(qb.list().get(0).getChuliqingkuang());
			mTextView_Result.setText(qb.list().get(0).getXunchaqingkuang());

			mImageView_path = qb.list().get(0).getZhaopianurl();
			if (mImageView_path == null || mImageView_path.equals("")) {

				mTextView_IsPicture.setText("否");

				Toast.makeText(InspectionRecordDetail.this, "图片资源不存在",
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

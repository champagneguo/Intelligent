package com.intelligent.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.intelligent.MenuActivity;
import com.intelligent.greendao.DaoMaster;
import com.intelligent.greendao.DaoSession;
import com.intelligent.greendao.dianli;
import com.intelligent.greendao.dianliDao;
import com.intelligent.greendao.guandian;
import com.intelligent.greendao.guandianDao;
import com.intelligent.greendao.guzhangshenbao;
import com.intelligent.greendao.guzhangshenbaoDao;
import com.intelligent.greendao.DaoMaster.DevOpenHelper;
import com.intelligent.util.Global;
import com.intenlligent.R;
import de.greenrobot.dao.query.QueryBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Report extends Activity {

	private String TAG = "Report";
	private TextView Uid;
	private TextView Time;
	private TextView Name;
	private EditText Remark;
	private Button again, end;
	private String UID;
	private String TIME;
	private String NAME;
	private String REMARK;
	private String ImagePath;
	private RadioGroup type;
	private RadioGroup photo;
	private ImageView image;
	String filename = null;
	private String Type[] = { "损坏", "维护", "缺失" };
	private String Photo[] = { "是", "否" };
	private int Type_index = 0, Photo_index = 1;
	private Global global;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;

	private guzhangshenbaoDao mguzhangshebaoDao;
	private dianli mdianli;
	private dianliDao mdianliDao;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.report);
		Log.e(TAG, "onCreate");
		DevOpenHelper myHelper = new DevOpenHelper(Report.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mguzhangshebaoDao = daoSession.getGuzhangshenbaoDao();
		mdianliDao = daoSession.getDianliDao();
		mdianli = new dianli();
		global = (Global) getApplication();
		NAME = global.getUsername();

		Uid = (TextView) findViewById(R.id.report_ID);
		Time = (TextView) findViewById(R.id.report_time);
		Name = (TextView) findViewById(R.id.report_name);
		type = (RadioGroup) findViewById(R.id.report_radioGroup_type);
		photo = (RadioGroup) findViewById(R.id.report_radioGroup_photo);
		image = (ImageView) findViewById(R.id.report_imageView);
		again = (Button) findViewById(R.id.report_continue);
		end = (Button) findViewById(R.id.report_stop);
		Remark = (EditText) findViewById(R.id.report_remark);

		UID = getIntent().getStringExtra("UID");
		Log.e(TAG, "UID---->>" + UID);
		if (UID != null) {
			Uid.setText(UID);
		}
		// 设置时间格式；
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TIME = df.format(new Date());
		Time.setText(TIME);
		Name.setText(NAME);

		again.setOnClickListener(new MyClick());
		end.setOnClickListener(new MyClick());

		type.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.report_radio0) {
					Type_index = 0;
				} else if (checkedId == R.id.report_radio1) {
					Type_index = 1;
				} else if (checkedId == R.id.report_radio11)
					Type_index = 2;
			}
		});

		photo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.report_radio2) {
					Photo_index = 0;
					// 调用系统的照相机；
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, 100);
				} else if (checkedId == R.id.report_radio3) {
					Photo_index = 1;
				}

			}
		});

	}

	public class MyClick implements OnClickListener {

		Builder builder = new Builder(Report.this);

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.report_continue:
				builder.setTitle("提示信息：");
				builder.setMessage("是否保存信息？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (UID == null) {
									insertReport();
									Toast.makeText(Report.this, "数据保存成功",
											Toast.LENGTH_SHORT).show();
									finish();
								} else {
									if (findUID(UID)) {
										insertReport();
										Toast.makeText(Report.this, "数据保存成功",
												Toast.LENGTH_SHORT).show();
										finish();

									} else {
										Toast.makeText(Report.this,
												"该标签不再数据库中！",
												Toast.LENGTH_SHORT).show();
									}

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

				break;
			case R.id.report_stop:
				builder.setTitle("提示信息：");
				builder.setMessage("是否保存信息？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								if (UID == null) {
									insertReport();
									Toast.makeText(Report.this, "数据保存成功",
											Toast.LENGTH_SHORT).show();
									// 跳转至主界面；
									Intent intent = new Intent(Report.this,
											MenuActivity.class);
									startActivity(intent);
									finish();
								} else {
									if (findUID(UID)) {
										insertReport();
										Toast.makeText(Report.this, "数据保存成功",
												Toast.LENGTH_SHORT).show();
										// 跳转至主界面；
										Intent intent = new Intent(Report.this,
												MenuActivity.class);
										startActivity(intent);
										finish();
									} else {
										Toast.makeText(Report.this,
												"该标签不再数据库中！",
												Toast.LENGTH_SHORT).show();
									}
								}
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				builder.create().show();
				break;
			default:
				break;
			}

		}
	}

	// 判断标签ID是否存在于数据库；
	public boolean findUID(String Uid) {
		Log.e(TAG, " boolean findUID(String Uid)----->" + Uid);
		QueryBuilder<dianli> qb = mdianliDao.queryBuilder();
		qb.where(dianliDao.Properties.Biaoqiancode.eq(Uid));
		if (qb.buildCount().count() > 0) {
			mdianli = qb.list().get(0);
			return true;
		}
		return false;
	}

	// 插入一条申报记录
	public void insertReport() {
		REMARK = Remark.getText().toString().trim();
		guzhangshenbao mguzhangshenbao = new guzhangshenbao();
		mguzhangshenbao.setGuzhangleixing(Type[Type_index]);
		mguzhangshenbao.setGuzhangshuoming(REMARK);
		mguzhangshenbao.setShenbaobiaoqianid(UID);
		if (UID != null) {
			mguzhangshenbao.setJingdu(mdianli.getJingdu());
			mguzhangshenbao.setWeidu(mdianli.getWeidu());
		}
		mguzhangshenbao.setShenbaorenyuan(NAME);
		mguzhangshenbao.setShenbaoriqi(TIME);
		mguzhangshenbao.setZhaopianurl(ImagePath);
		mguzhangshebaoDao.insert(mguzhangshenbao);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@SuppressLint("SdCardPath")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String sdState = Environment.getExternalStorageState();
		if (requestCode == 100 && resultCode == Activity.RESULT_OK
				&& data != null) {
			if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
				Log.e("SDState", "sdcard尚未加载！");
				return;
			}
			// 相片的保存格式
			new DateFormat();
			String title = DateFormat.format("yyyyMMdd_hhmmss",
					Calendar.getInstance(Locale.CHINA))
					+ ".jpg";
			Bundle bundle = data.getExtras();
			// 获取相机返回的数据，并转换成图片格式；
			Bitmap bitmap = (Bitmap) bundle.get("data");
			image.setImageBitmap(bitmap);
			FileOutputStream fout = null;
			System.out.println("sd卡的目录"
					+ Environment.getExternalStorageDirectory());
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/DCIM/record/");
			if (file != null) {
				file.mkdirs();
			}
			// 注意路径——绝对路径和相对路径；以及不要忘记"/"
			filename = file.getAbsolutePath() + "/" + title;
			ImagePath = filename;
			Log.e(TAG, "ImagePath:" + ImagePath);
			try {
				fout = new FileOutputStream(filename);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {
					fout.flush();
					fout.close();
				} catch (IOException e) {
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
		if (db != null) {
			db.close();
			db = null;
		}

	}

}

package com.intelligent;

import com.intelligent.greendao.DaoMaster;
import com.intelligent.greendao.DaoMaster.DevOpenHelper;
import com.intelligent.greendao.DaoSession;
import com.intelligent.greendao.user;
import com.intelligent.greendao.userDao;
import com.intelligent.util.Global;
import com.intenlligent.R;
import de.greenrobot.dao.query.QueryBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	private String Username;
	private String Password;
	private EditText username;
	private EditText password;
	private Button login;
	private TextView register;
	private TextView forget;
	private static String TAG = "Login";
	private Global global;
	private SharedPreferences sharePreferences;
	private Editor editor;
	private SQLiteDatabase db;
	// private SQLiteDatabase DB;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private userDao muserDao;

	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		Log.e(TAG, "onCreate");
		global = (Global) getApplication();
		username = (EditText) findViewById(R.id.login_username);
		password = (EditText) findViewById(R.id.login_password);
		login = (Button) findViewById(R.id.login_button);
		forget = (TextView) findViewById(R.id.login_forget);
		register = (TextView) findViewById(R.id.login_register);

		DevOpenHelper myHelper = new DevOpenHelper(Login.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		muserDao = daoSession.getUserDao();

		// 初始化数据库；
		muserDao.deleteAll();
		user muser = new user();
		muser.setUserid(0L);
		muser.setUsername("hehaoyuan");
		muser.setName("和昊元");
		muser.setBumen("系统管理");
		muser.setPassword("hehaoyuan");
		muser.setSex("");
		muser.setMobile("");
		muser.setGuanliyuan("1");
		muserDao.insertOrReplace(muser);
		Log.e(TAG, "muser--------------->>>" + muser.getUsername());

		muser.setUserid(1L);
		muser.setUsername("admin");
		muser.setName("张三");
		muser.setBumen("管理部");
		muser.setPassword("admin");
		muser.setSex("男");
		muser.setMobile("18327");
		muser.setGuanliyuan("1");
		muserDao.insertOrReplace(muser);
		Log.e(TAG, "muser--------------->>>" + muser.getUsername());

		sharePreferences = getSharedPreferences("login", MODE_PRIVATE);
		editor = sharePreferences.edit();
		String e = sharePreferences.getString("username", null);
		String l = sharePreferences.getString("password", null);
		if (e != null && l != null) {
			username.setText(e);
			password.setText(l);
		}

		login.setOnClickListener(this);
		forget.setOnClickListener(this);
		register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_button:
			Username = username.getText().toString().trim();
			Password = password.getText().toString().trim();
			if (!Username.equals("") || !Password.equals("")) {
				if (search(Username, Password)) {

					editor.putString("username", Username);
					editor.putString("password", Password);
					editor.commit();

					Toast.makeText(Login.this, "用户：" + Username + "登录成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Login.this, MenuActivity.class);
					intent.putExtra("username", Username);
					startActivity(intent);
					global.setUsername(Username);
					Log.e(TAG, "global.getUsername:" + global.getUsername());
					finish();
				} else {
					Toast.makeText(Login.this, "对不起，您输入的用户名或密码不正确！",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(Login.this, "请将用户名或密码输入完整！", Toast.LENGTH_LONG)
						.show();
			}
			break;
		case R.id.login_register:
			Intent intent = new Intent(Login.this, Register.class);
			startActivity(intent);
			break;
		case R.id.login_forget:
			Intent intent1 = new Intent(Login.this, Forget.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}

	public boolean search(String Username, String Password) {

		QueryBuilder<user> qb = muserDao.queryBuilder();
		qb.where(qb.and(userDao.Properties.Username.eq(Username),
				userDao.Properties.Password.eq(Password)));
		if (qb.buildCount().count() > 0) {
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

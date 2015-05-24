package com.intelligent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.intelligent.db.MyDataBaseHelper;
import com.intelligent.util.Global;
import com.intelligent.util.User;
import com.intenlligent.R;

public class Register extends Activity {

	private String Username;
	private String Password;
	private String Confirm;
	private String Email;
	private String Phone;
	private EditText username;
	private EditText password;
	private EditText confirm;
	private EditText email;
	private EditText phone;
	private Spinner position;
	private Button cancle;
	private Button register;
	private MyDataBaseHelper DB;
	private boolean flag;
	private String Position[] = { "管理人员", "组长", "一般用户" };
	private int number = 1;
	private String TAG = "Register";
	private Global global;

	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		global = (Global) getApplication();
		username = (EditText) findViewById(R.id.register_username);
		password = (EditText) findViewById(R.id.register_password);
		confirm = (EditText) findViewById(R.id.register_confirm);
		email = (EditText) findViewById(R.id.register_email);
		phone = (EditText) findViewById(R.id.register_phone);
		position = (Spinner) findViewById(R.id.register_position);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this,
				android.R.layout.simple_spinner_dropdown_item, Position);
		position.setAdapter(adapter);
		cancle = (Button) findViewById(R.id.register_cancle);
		register = (Button) findViewById(R.id.register_button);
		DB = new MyDataBaseHelper(Register.this, "intelligent.db", null, 1);

		position.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				number = position + 1;
				Log.e(TAG, "onItemSelected:" + number);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		// 对于取消按钮结束当前Activity；
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 对于注册按钮进行搜索数据库匹配
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Username = username.getText().toString().trim();
				Password = password.getText().toString().trim();
				Confirm = confirm.getText().toString().trim();
				Email = email.getText().toString().trim();
				Phone = phone.getText().toString().trim();
				if (Username.equals("") || Password.equals("")
						|| Confirm.equals("") || Email.equals("")
						|| Phone.equals("")) {
					Toast.makeText(Register.this, "请您完善所有选项！",
							Toast.LENGTH_LONG).show();
				} else {
					if (Password.equals(Confirm)) {
						// 先查询数据库；有没有用户。没有则插入；
						flag = findElement(Username);
						if (!flag) {
							insert(new User(Username, Password, Email, Phone,
									number));
							global.setUsername(Username);
							Toast.makeText(Register.this,
									"恭喜用户：" + Username + "注册成功！",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(Register.this,
									MenuActivity.class);
							intent.putExtra("username", Username);
							startActivity(intent);
							finish();
						} else {
							Toast.makeText(Register.this, "对不起，该用户已经被注册！",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(Register.this, "密码输入不一致！",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	public boolean findElement(String Username) {
		SQLiteDatabase db = DB.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from register", null);
		if (cursor.moveToFirst()) {
			int index = cursor.getColumnIndex("username");
			Log.e(TAG, "findElement:" + index);
			if (Username.equals(cursor.getString(index)))
				return true;
			else
				return false;
		} else
			return false;
	}

	public void insert(User user) {

		Log.e(TAG, "insert start");

		SQLiteDatabase db = DB.getWritableDatabase();

		db.execSQL(
				"insert into register(username,password,email,telephone,position_id) values(?,?,?,?,?)",
				new Object[] { user.getUsername(), user.getPassword(),
						user.getEmail(), user.getPhone(), user.getPosition_id() });
		Log.e(TAG, "insert end");

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (DB != null) {
			DB.close();
		}
	}
}

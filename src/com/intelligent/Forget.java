package com.intelligent;

import java.util.ArrayList;

import com.intelligent.db.MyDataBaseHelper;
import com.intenlligent.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.content.DialogInterface.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forget extends Activity {

	private String Password;
	private String Username;
	private String Email;
	private String Phone;
	private EditText username;
	private EditText email;
	private EditText phone;
	private Button forget;
	private Cursor cursor;
	private MyDataBaseHelper DB;
	private Button cancle;
	public static ArrayList<ArrayList<Integer>> a;

	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forget);

		username = (EditText) findViewById(R.id.forget_username);
		email = (EditText) findViewById(R.id.forget_email);
		phone = (EditText) findViewById(R.id.forget_phone);
		forget = (Button) findViewById(R.id.forget_button);
		cancle = (Button) findViewById(R.id.forget_cancle);
		DB = new MyDataBaseHelper(Forget.this, "user.db", null, 1);

		cancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		// 实现查询数据提交按钮事件；
		forget.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Username = username.getText().toString().trim();
				Email = email.getText().toString().trim();
				Phone = phone.getText().toString().trim();
				Builder builder = new Builder(Forget.this);
				builder.setTitle("  找回密码");
				if (Username.equals("") || Email.equals("") || Phone.equals("")) {
					Toast.makeText(Forget.this, "请您完善以上信息！", Toast.LENGTH_SHORT)
							.show();
				} else {
					cursor = search(Username);
					// 遍历查询得到的游标需要移到first，先判断是否未找到数据，然后再从中筛选数据。
					if (cursor.moveToFirst()) {
						if (cursor.getString(1).equals(Username)
								&& cursor.getString(3).equals(Email)
								&& cursor.getString(4).equals(Phone)) {
							Password = cursor.getString(2);
							builder.setMessage("用户名：" + Username + "\n密码："
									+ Password + "\n请您保存好密码！");
							builder.setPositiveButton("确定",
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													Forget.this, Login.class);
											startActivity(intent);
											finish();
										}
									});
							builder.setNegativeButton("取消",
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
										}
									});
							builder.create().show();
						} else {
							builder.setMessage("用户：" + Username
									+ "\n验证未通过，密码未能获取到");
							builder.setPositiveButton("确定", null);
							builder.create().show();
						}
					} else {
						Toast.makeText(Forget.this, "系统该用户没有注册！",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	public Cursor search(String Username) {
		SQLiteDatabase db = DB.getReadableDatabase();
		String sql = "select * from user where username like " + Username;
		System.out.println(sql);
		Cursor cursor1 = db.rawQuery(sql, null);
		return cursor1;
	}
}

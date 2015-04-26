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

		// ʵ�ֲ�ѯ�����ύ��ť�¼���
		forget.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Username = username.getText().toString().trim();
				Email = email.getText().toString().trim();
				Phone = phone.getText().toString().trim();
				Builder builder = new Builder(Forget.this);
				builder.setTitle("  �һ�����");
				if (Username.equals("") || Email.equals("") || Phone.equals("")) {
					Toast.makeText(Forget.this, "��������������Ϣ��", Toast.LENGTH_SHORT)
							.show();
				} else {
					cursor = search(Username);
					// ������ѯ�õ����α���Ҫ�Ƶ�first�����ж��Ƿ�δ�ҵ����ݣ�Ȼ���ٴ���ɸѡ���ݡ�
					if (cursor.moveToFirst()) {
						if (cursor.getString(1).equals(Username)
								&& cursor.getString(3).equals(Email)
								&& cursor.getString(4).equals(Phone)) {
							Password = cursor.getString(2);
							builder.setMessage("�û�����" + Username + "\n���룺"
									+ Password + "\n������������룡");
							builder.setPositiveButton("ȷ��",
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
							builder.setNegativeButton("ȡ��",
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
							builder.setMessage("�û���" + Username
									+ "\n��֤δͨ��������δ�ܻ�ȡ��");
							builder.setPositiveButton("ȷ��", null);
							builder.create().show();
						}
					} else {
						Toast.makeText(Forget.this, "ϵͳ���û�û��ע�ᣡ",
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

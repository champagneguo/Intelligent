package com.intelligent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {

	// ְλ��
	final String create_table_position = "create table position(position_id integer primary key autoincrement, desc varchar);";
	// ע���
	final String creata_table_register = "create table register(register_id integer primary key autoincrement, username varchar, password varchar, telephone varchar, email varchar, position_id integer, foreign key(position_id) references position(position_id));";
	// �ܵ���ҵ��
	final String create_table_pipe_industry = "create table pipe_industry(pipe_industry_id integer primary key autoincrement, desc varchar);";
	// �ܵ����ͱ�
	final String create_table_pipe_type = "create table pipe_type(pipe_type_id integer primary key autoincrement, desc varchar);";
	// ��ǩ��
	final String create_table_label = "create table label(label_id integer primary key autoincrement, rfid varchar NOT NULL UNIQUE, label_time datetime default CURRENT_TIMESTAMP, register_id integer, longitude double, latitude double, road_info varchar, pipe_industry_id integer, pipe_type_id integer, remark varchar, foreign key(register_id) references register(register_id), foreign key(pipe_industry_id) references pipe_industry(pipe_industry_id),foreign key(pipe_type_id) references pipe_type(pipe_type_id) );";
	// Ѳ���
	final String create_table_inspection = "create table inspection(inspection_id integer primary key autoincrement, label_id integer, inspection_time datetime default CURRENT_TIMESTAMP,register_id integer, inspection_result varchar, image_result varchar, image_path varchar, remark varchar,foreign key(label_id) references label(label_id),foreign key(register_id) references register(register_id));";
	// ���ϱ�
	final String create_table_fault_report = "create table fault_report(fault_report_id integer primary key autoincrement, label_id integer, fault_report_time datetime default CURRENT_TIMESTAMP, register_id integer, fault_report_remark varchar, fault_report_type varchar, image_result varchar, iamge_path varchar,foreign key(label_id) references label(label_id),foreign key(register_id) references register(register_id));";
	// �����Լ��
	final String Sqlite_setting = "PRAGMA foreign_keys = ON;";

	public MyDataBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Sqlite_setting);
		// ��һ���������ݿ�ʱ���½���
		db.execSQL(create_table_position);
		db.execSQL("insert into position(position_id, desc) values (?,?);",
				new Object[] { 1, "������" });
		db.execSQL("insert into position(position_id, desc) values (?,?);",
				new Object[] { 2, "�鳤" });
		db.execSQL("insert into position(position_id, desc) values (?,?);",
				new Object[] { 3, "һ���û�" });

		db.execSQL(creata_table_register);

		db.execSQL(create_table_pipe_industry);
		db.execSQL(
				"insert into pipe_industry(pipe_industry_id,desc) values(?,?) ",
				new Object[] { 1, "������ҵ" });
		db.execSQL(
				"insert into pipe_industry(pipe_industry_id,desc) values(?,?) ",
				new Object[] { 2, "��������ҵ" });
		db.execSQL(
				"insert into pipe_industry(pipe_industry_id,desc) values(?,?) ",
				new Object[] { 3, "ͨ����ҵ" });
		db.execSQL(
				"insert into pipe_industry(pipe_industry_id,desc) values(?,?) ",
				new Object[] { 4, "�ź���ҵ" });

		db.execSQL(create_table_pipe_type);
		db.execSQL("insert into pipe_type(pipe_type_id,desc) values(?,?);",
				new Object[] { 1, "����ֱ��" });
		db.execSQL("insert into pipe_type(pipe_type_id,desc) values(?,?);",
				new Object[] { 2, "���½�����" });
		db.execSQL("insert into pipe_type(pipe_type_id,desc) values(?,?);",
				new Object[] { 3, "���½�ͷ" });
		db.execSQL("insert into pipe_type(pipe_type_id,desc) values(?,?);",
				new Object[] { 4, "���¹���" });

		db.execSQL(create_table_label);
		db.execSQL(create_table_inspection);
		db.execSQL(create_table_fault_report);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}

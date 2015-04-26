package com.intelligent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {

	// 职位表
	final String create_table_position = "create table position(position_id integer primary key autoincrement, desc varchar);";
	// 注册表
	final String creata_table_register = "create table register(register_id integer primary key autoincrement, username varchar, password varchar, telephone varchar, email varchar, position_id integer, foreign key(position_id) references position(position_id));";
	// 管道行业表
	final String create_table_pipe_industry = "create table pipe_industry(pipe_industry_id integer primary key autoincrement, desc varchar);";
	// 管道类型表
	final String create_table_pipe_type = "create table pipe_type(pipe_type_id integer primary key autoincrement, desc varchar);";
	// 标签表
	final String create_table_label = "create table label(label_id integer primary key autoincrement, rfid varchar NOT NULL UNIQUE, label_time datetime default CURRENT_TIMESTAMP, register_id integer, longitude double, latitude double, road_info varchar, pipe_industry_id integer, pipe_type_id integer, remark varchar, foreign key(register_id) references register(register_id), foreign key(pipe_industry_id) references pipe_industry(pipe_industry_id),foreign key(pipe_type_id) references pipe_type(pipe_type_id) );";
	// 巡检表
	final String create_table_inspection = "create table inspection(inspection_id integer primary key autoincrement, label_id integer, inspection_time datetime default CURRENT_TIMESTAMP,register_id integer, inspection_result varchar, image_result varchar, image_path varchar, remark varchar,foreign key(label_id) references label(label_id),foreign key(register_id) references register(register_id));";
	// 故障表
	final String create_table_fault_report = "create table fault_report(fault_report_id integer primary key autoincrement, label_id integer, fault_report_time datetime default CURRENT_TIMESTAMP, register_id integer, fault_report_remark varchar, fault_report_type varchar, image_result varchar, iamge_path varchar,foreign key(label_id) references label(label_id),foreign key(register_id) references register(register_id));";
	// 打开外键约束
	final String Sqlite_setting = "PRAGMA foreign_keys = ON;";

	public MyDataBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Sqlite_setting);
		// 第一次启动数据库时，新建表；
		db.execSQL(create_table_position);
		db.execSQL("insert into position(position_id, desc) values (?,?);",
				new Object[] { 1, "管理者" });
		db.execSQL("insert into position(position_id, desc) values (?,?);",
				new Object[] { 2, "组长" });
		db.execSQL("insert into position(position_id, desc) values (?,?);",
				new Object[] { 3, "一般用户" });

		db.execSQL(creata_table_register);

		db.execSQL(create_table_pipe_industry);
		db.execSQL(
				"insert into pipe_industry(pipe_industry_id,desc) values(?,?) ",
				new Object[] { 1, "电力行业" });
		db.execSQL(
				"insert into pipe_industry(pipe_industry_id,desc) values(?,?) ",
				new Object[] { 2, "电气化行业" });
		db.execSQL(
				"insert into pipe_industry(pipe_industry_id,desc) values(?,?) ",
				new Object[] { 3, "通信行业" });
		db.execSQL(
				"insert into pipe_industry(pipe_industry_id,desc) values(?,?) ",
				new Object[] { 4, "信号行业" });

		db.execSQL(create_table_pipe_type);
		db.execSQL("insert into pipe_type(pipe_type_id,desc) values(?,?);",
				new Object[] { 1, "电缆直线" });
		db.execSQL("insert into pipe_type(pipe_type_id,desc) values(?,?);",
				new Object[] { 2, "电缆交叉线" });
		db.execSQL("insert into pipe_type(pipe_type_id,desc) values(?,?);",
				new Object[] { 3, "电缆接头" });
		db.execSQL("insert into pipe_type(pipe_type_id,desc) values(?,?);",
				new Object[] { 4, "电缆拐弯" });

		db.execSQL(create_table_label);
		db.execSQL(create_table_inspection);
		db.execSQL(create_table_fault_report);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}

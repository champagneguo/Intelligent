package com.intelligent.util;

import java.util.Date;

//´æ´¢Ã¿Ìõ¼ÇÂ¼£»
public class DataLoadRecord {

	private String Uid;

	private int register_id;
	private double longitude;
	private double latitude;
	private String road_info;
	private int pipe_industry_id;
	private int pipe_type_id;
	private String remark;

	public DataLoadRecord(String Uid, Date label_time, int register_id,
			double longitude, double latitude, String road_info,
			int pipe_industry_id, int pipe_type_id, String remark) {
		this.Uid = Uid;

		this.register_id = register_id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.road_info = road_info;
		this.pipe_industry_id = pipe_industry_id;
		this.pipe_type_id = pipe_type_id;
		this.remark = remark;
	}

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}

	public int getRegister_id() {
		return register_id;
	}

	public void setRegister_id(int register_id) {
		this.register_id = register_id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getRoad_info() {
		return road_info;
	}

	public void setRoad_info(String road_info) {
		this.road_info = road_info;
	}

	public int getPipe_industry_id() {
		return pipe_industry_id;
	}

	public void setPipe_industry_id(int pipe_industry_id) {
		this.pipe_industry_id = pipe_industry_id;
	}

	public int getPipe_type_id() {
		return pipe_type_id;
	}

	public void setPipe_type_id(int pipe_type_id) {
		this.pipe_type_id = pipe_type_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

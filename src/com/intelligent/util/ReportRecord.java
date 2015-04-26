package com.intelligent.util;

//¥ÌŒÛ±®∏Ê¿‡
public class ReportRecord {

	private int label_id;
	private int register_id;
	private String fault_report_remark;
	private String fault_report_type;
	private String image_result;
	private String image_path;

	public ReportRecord(int label_id, int register_id,
			String fault_report_remark, String fault_report_type,
			String image_result, String image_path) {
		this.label_id = label_id;
		this.register_id = register_id;
		this.fault_report_remark = fault_report_remark;
		this.fault_report_type = fault_report_type;
		this.image_result = image_result;
		this.image_path = image_path;

	}

	public int getLabel_id() {
		return label_id;
	}

	public void setLabel_id(int label_id) {
		this.label_id = label_id;
	}

	public int getRegister_id() {
		return register_id;
	}

	public void setRegister_id(int register_id) {
		this.register_id = register_id;
	}

	public String getImage_result() {
		return image_result;
	}

	public void setImage_result(String image_result) {
		this.image_result = image_result;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getFault_report_remark() {
		return fault_report_remark;
	}

	public void setFault_report_remark(String fault_report_remark) {
		this.fault_report_remark = fault_report_remark;
	}

	public String getFault_report_type() {
		return fault_report_type;
	}

	public void setFault_report_type(String fault_report_type) {
		this.fault_report_type = fault_report_type;
	}

}

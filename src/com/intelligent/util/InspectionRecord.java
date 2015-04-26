package com.intelligent.util;

//Ñ²¼ì¼ÇÂ¼Àà
public class InspectionRecord {
	private int label_id;
	private int register_id;
	private String inspection_result;
	private String image_result;
	private String image_path;
	private String remark;

	public InspectionRecord(int label_id, int register_id, String result,
			String photo, String imagePath, String aDD) {
		this.label_id = label_id;
		this.register_id = register_id;
		this.inspection_result = result;
		this.image_result = photo;
		this.image_path = imagePath;
		this.remark = aDD;
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

	public String getInspection_result() {
		return inspection_result;
	}

	public void setInspection_result(String inspection_result) {
		this.inspection_result = inspection_result;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

package com.intelligent.util;

import android.content.Intent;
import android.graphics.drawable.Drawable;

//Model�������洢Ӧ�ó�����Ϣ��
public class AppInfo {
	private String appLabel;// Ӧ�ó���ı�ǩ��
	private Drawable appIcon;// Ӧ�ó����ͼ��
	private Intent intent;// ����Ӧ�ó����Intent��һ����ActionΪMain��CategoryΪLancher��Activity��
	private String pkgName;// Ӧ�ó���İ�����
	private String activityName; //Ӧ�ó������activity��ڣ�

	public AppInfo() {
	};
	
	public String getAppLabel() {
		return appLabel;
	}
	
	public void setAppLabel(String appName) {
		this.appLabel = appName;
	}
	
	public Drawable getAppIcon() {
		return appIcon;
	}
	
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	
	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	
	public String getPkgName() {
		return pkgName;
	}
	
	public void setPkgName(String PkgName) {
		this.pkgName = PkgName;
	}
	
	public void setActivityName(String ActivityName) {
		this.activityName = ActivityName;
	}
	
	public String getActivityName() {
		return activityName;
	}
}

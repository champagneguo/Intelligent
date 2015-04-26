package com.intelligent.util;

import android.content.Intent;
import android.graphics.drawable.Drawable;

//Model类用来存储应用程序信息；
public class AppInfo {
	private String appLabel;// 应用程序的标签；
	private Drawable appIcon;// 应用程序的图像；
	private Intent intent;// 启动应用程序的Intent，一般是Action为Main和Category为Lancher的Activity；
	private String pkgName;// 应用程序的包名；
	private String activityName; //应用程序的主activity入口；

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

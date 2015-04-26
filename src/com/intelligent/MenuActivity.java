package com.intelligent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import com.intenlligent.R;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MenuActivity extends FragmentActivity {

	private long waitTime = 2000;
	private long touchTime = 0;
	private String TAG = "MenuActivity";
	private FragmentTabHost mTabHost;
	private RadioGroup mTabGg;
	private final Class[] fragments = { Fragment01.class, Fragment02.class,
			Fragment03.class, Fragment04.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 隐去标题栏（应用程序的名字）
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_menu);
		// 初始化元素
		initView();
	}

	public void initView() {
		Log.e(TAG, "执行initView");
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

		Log.e(TAG, "新建mtabHost对象");
		mTabHost.setup(this, getSupportFragmentManager(),
				R.id.activitymenu_tabcontent);
		Log.e(TAG, "mTabHost.setup");
		// Fragment的个数；
		int count = fragments.length;
		for (int i = 0; i < count; i++) {
			// 为每个Tab设置图标和内容；
			TabSpec tabSpect = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			// 将Tab按钮添加到Tab选项卡中；
			mTabHost.addTab(tabSpect, fragments[i], null);
			Log.e(TAG, "fragments" + i);
		}

		//
		mTabGg = (RadioGroup) findViewById(R.id.activitymenu_radio_rg);
		mTabGg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.tab_rb_1:
					mTabHost.setCurrentTab(0);
					break;
				case R.id.tab_rb_2:
					mTabHost.setCurrentTab(1);
					break;
				case R.id.tab_rb_3:
					mTabHost.setCurrentTab(2);
					break;
				case R.id.tab_rb_4:
					mTabHost.setCurrentTab(3);
					break;
				default:
					break;
				}

			}
		});
		//初次打开软件时，默认在第一个Fragment；
		mTabHost.setCurrentTab(0);
	}

	// 实现再按一次退出系统。
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}

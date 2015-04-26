package com.intelligent;

import com.intelligent.load.DataCollectActivity;
import com.intelligent.search.InspectionRecord;
import com.intelligent.search.LabelSearch;
import com.intelligent.search.PipeRecognition;
import com.intelligent.search.Report;
import com.intenlligent.R;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

@SuppressWarnings("deprecation")
public class Fragment01 extends Fragment {

	private String TAG = "Fragment01";
	private View parentView;
	private Fragment01_GvAdapter adapter;
	private GridView gv;
	// private int[] imgItemRes = { R.drawable.load_data,
	// R.drawable.data_detect,
	// R.drawable.manager, R.drawable.map_vavigation };

	// private String[] imgItemDesc = { "数据加载", "数据识别", "巡检管理", "地图导航" };
	private int[] imgItemRes = { R.drawable.label, R.drawable.underground,
			R.drawable.load_data, R.drawable.map_vavigation,
			R.drawable.map_vavigation, R.drawable.manager,
			R.drawable.data_detect, R.drawable.phone, R.drawable.conversations,
			R.drawable.contact, R.drawable.camera, R.drawable.setting };
	private String[] imgItemDesc = { "标牌识别", "地下标识", "数据采集", "地图检索", "在线导航",
			"巡检管理", "故障申报", "电话", "短信", "通讯录", "相机", "设置" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.e(TAG, "onCreateView");
		parentView = inflater.inflate(R.layout.fragment01, container, false);
		adapter = new Fragment01_GvAdapter(this.getActivity(), imgItemRes,
				imgItemDesc);
		Log.e(TAG, "adapter:" + adapter);
		if (parentView != null) {
			gv = (GridView) parentView.findViewById(R.id.fragment01_gv);

			gv.setAdapter(adapter);

			gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (position == 0) {
						// 标牌识别
						Intent intent = new Intent(getActivity(),
								LabelSearch.class);
						startActivity(intent);
					} else if (position == 1) {
						// 地下标识
						Intent intent = new Intent(getActivity(),
								PipeRecognition.class);
						startActivity(intent);
					} else if (position == 2) {
						// 数据采集
						Intent intent = new Intent(getActivity(),
								DataCollectActivity.class);
						startActivity(intent);
					} else if (position == 3) {
						// 地图检索
						Intent intent = new Intent(getActivity(),
								Navigation.class);
						startActivity(intent);
					} else if (position == 4) {
						// 在线导航
						Intent intent = new Intent(getActivity(),
								Navigation.class);
						startActivity(intent);
					} else if (position == 5) {
						// 巡检管理
						Intent intent = new Intent(getActivity(),
								InspectionRecord.class);
						startActivity(intent);
					} else if (position == 6) {
						// 故障申报
						Intent intent = new Intent(getActivity(), Report.class);
						startActivity(intent);
					} else if (position == 7) {
						// 电话
						Intent intent = new Intent();
						intent.setAction("android.intent.action.DIAL");
						startActivity(intent);
					} else if (position == 8) {
						// 短信
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setType("vnd.android-dir/mms-sms");
						// intent.setData(Uri.parse("content://mms-sms/conversations/"));//此为号码
						startActivity(intent);
					} else if (position == 9) {
						// 通讯录
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.setData(Contacts.People.CONTENT_URI);
						startActivity(intent);
					} else if (position == 10) {
						// 相机
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						startActivity(intent);
					} else if (position == 11) {
						// 设置
						Intent intent = new Intent();
						intent.setComponent(new ComponentName(
								"com.android.settings",
								"com.android.settings.Settings"));
						intent.setAction(Intent.ACTION_VIEW);
						startActivity(intent);
					}
				}

			});
		}
		return parentView;
	}
}

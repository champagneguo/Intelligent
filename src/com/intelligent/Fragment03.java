package com.intelligent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.intenlligent.R;

public class Fragment03 extends Fragment {
	
	private String TAG = "Fragment03";
	private View parentView;
	private Fragment03_GvAdapter adapter;
	private GridView gv;
	// private int[] imgItemRes = { R.drawable.load_data,
	// R.drawable.data_detect,
	// R.drawable.manager, R.drawable.map_vavigation };

	// private String[] imgItemDesc = { "数据加载", "数据识别", "巡检管理", "地图导航" };
	private int[] imgItemRes = { R.drawable.map_vavigation, R.drawable.map_vavigation};
	private String[] imgItemDesc = { "地图检索", "在线导航" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.e(TAG, "onCreateView");
		parentView = inflater.inflate(R.layout.fragment03, container, false);
		adapter = new Fragment03_GvAdapter(this.getActivity(), imgItemRes,
				imgItemDesc);
		Log.e(TAG, "adapter:" + adapter);
		if (parentView != null) {
			gv = (GridView) parentView.findViewById(R.id.fragment03_gv);

			gv.setAdapter(adapter);

			gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (position == 0) {
						// 地图检索
						Intent intent = new Intent(getActivity(),
								Navigation.class);
						startActivity(intent);
					} else if (position == 1) {
						// 在线导航
						Intent intent = new Intent(getActivity(),
								Navigation.class);
						startActivity(intent);
					}
				}
			});
		}
		return parentView;
	}

}

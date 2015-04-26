package com.intelligent.search;

import java.util.ArrayList;
import java.util.List;
import com.intenlligent.R;
import android.app.Activity;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InspectionRecordAdapter extends BaseAdapter implements
		OnItemClickListener {

	private String TAG = "InspectionRecordAdapter";
	private Activity activity;
	private List<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
	private int i = 0;

	public InspectionRecordAdapter(Activity activity,
			List<ArrayList<String>> lists) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.lists = lists;
		Log.e(TAG, "InspectionRecordAdapter");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// 设置布局文件属性，getView函数不必多次调用，holder是为了加快数据加载速度；
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(activity, R.layout.inspection_record_lv,
					null);
			Log.e(TAG, "convertView == null:convertView" + convertView);
			holder.Number = (TextView) convertView
					.findViewById(R.id.inspection_record_lv_number);
			holder.Name = (TextView) convertView
					.findViewById(R.id.inspection_record_lv_name);
			holder.Result = (TextView) convertView
					.findViewById(R.id.inspection_record_lv_result);
			holder.Time = (TextView) convertView
					.findViewById(R.id.inspection_record_lv_time);
			convertView.setTag(holder);
			Log.e(TAG, "convertView == null:" + holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
			Log.e(TAG, "convertView != null:" + holder);
		}

		// 注意setText时，不要将整形直接写入；
		holder.Number.setText("" + (position + 1));
		Log.e(TAG, "getView:position" + (position + 1));

		holder.Name.setText(lists.get(position).get(1));
		Log.e(TAG, "getView:Name" + lists.get(position).get(1));

		String temp = lists.get(position).get(2);
		holder.Result.setText(temp);
		Log.e(TAG, "getView:Result" + temp);

		holder.Time.setText(lists.get(position).get(3));
		Log.e(TAG, "getView:Time" + lists.get(position).get(3));

		return convertView;
	}

	class ViewHolder {

		private TextView Number, Name, Result, Time;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(activity, InspectionRecordDetail.class);
		intent.putExtra("XunChaId", lists.get(position).get(0));
		activity.startActivity(intent);

	}
}

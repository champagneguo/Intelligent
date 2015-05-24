package com.intelligent.search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.intenlligent.R;

public class ReportRecordAdapter extends BaseAdapter implements
		OnItemClickListener {

	private String TAG = "ReportRecordAdapter";
	private Activity activity;
	private List<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();

	public ReportRecordAdapter(ReportRecord reportRecord,
			List<ArrayList<String>> lists) {
		// TODO Auto-generated constructor stub
		this.activity = reportRecord;
		this.lists = lists;
		Log.e(TAG, "ReportRecordAdapter");
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
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(activity, R.layout.report_record_lv,
					null);
			holder = new ViewHolder();
			holder.Number = (TextView) convertView
					.findViewById(R.id.report_record_lv_number);
			holder.Reason = (TextView) convertView
					.findViewById(R.id.report_record_lv_result);
			holder.Name = (TextView) convertView
					.findViewById(R.id.report_record_lv_name);
			holder.Time = (TextView) convertView
					.findViewById(R.id.report_record_lv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.Number.setText("" + (position + 1));
		holder.Reason.setText(lists.get(position).get(0));
		holder.Name.setText(lists.get(position).get(1));
		holder.Time.setText(lists.get(position).get(2));
		return convertView;
	}

	class ViewHolder {

		private TextView Number, Reason, Name, Time;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(activity, ReportRecordDetail.class);
		intent.putExtra("ShenBaoId", lists.get(position).get(3));
		activity.startActivity(intent);

	}
}

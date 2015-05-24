package com.intelligent.load;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intenlligent.R;

public class DataCollect_GvAdapter extends BaseAdapter {

	private Activity activity;
	private int[] imgItemRes;
	private String[] imgItemDesc;
	private String TAG = "DataCollect_GvAdapter";
	private ImageView image;
	private TextView desc;

	public DataCollect_GvAdapter(DataCollectActivity dataCollectActivity,
			int[] imgItemRes, String[] imgItemDesc) {
		// TODO Auto-generated constructor stub
		this.activity = dataCollectActivity;
		this.imgItemDesc = imgItemDesc;
		this.imgItemRes = imgItemRes;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgItemDesc.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = View.inflate(activity,
					R.layout.activity_datacollect_gv, null);
		}
		image = (ImageView) convertView.findViewById(R.id.datacollect_gv_image);
		image.setImageResource(imgItemRes[position]);
		desc = (TextView) convertView.findViewById(R.id.datacollect_gv_desc);
		desc.setText(imgItemDesc[position]);
		Log.e(TAG, "getView:" + position);
		return convertView;
	}

}

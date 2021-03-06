package com.intelligent;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intenlligent.R;

public class Fragment01_GvAdapter extends BaseAdapter {

	private int[] imgItemRes;
	private String[] imgItemDesc;
	private FragmentActivity activity;
	private String TAG = "Fragment01_GvAdapter";
	private ImageView image;
	private TextView desc;

	public Fragment01_GvAdapter(FragmentActivity activity, int[] imgItemRes,
			String[] imgItemDesc) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.imgItemDesc = imgItemDesc;
		this.imgItemRes = imgItemRes;
		Log.e(TAG, "���캯��");
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
			convertView = View.inflate(activity, R.layout.fragment01_gv, null);
		}
		image = (ImageView) convertView.findViewById(R.id.fragment01_gv_image);
		image.setImageResource(imgItemRes[position]);
		desc = (TextView) convertView.findViewById(R.id.fragment01_gv_desc);
		desc.setText(imgItemDesc[position]);
		Log.e(TAG, "getView:" + position);

		return convertView;
	}

}

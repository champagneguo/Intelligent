package com.intelligent.load;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.intelligent.search.LabelSearch;
import com.intelligent.search.PipeRecognition;
import com.intenlligent.R;

public class DataCollectActivity extends Activity {

	private String TAG = "DataCollectActivity";
	private GridView gv;
	private DataCollect_GvAdapter adapter;
	private int[] imgItemRes = { R.drawable.label, R.drawable.underground };
	private String[] imgItemDesc = { "标牌识别", "地标识别" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datacollect);
		Log.e(TAG, "onCreate");
		gv = (GridView) findViewById(R.id.datacollect_gv);
		adapter = new DataCollect_GvAdapter(this, imgItemRes, imgItemDesc);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					// 标牌识别
					Intent intent = new Intent(DataCollectActivity.this,
							LabelSearch.class);
					startActivity(intent);
				} else if (position == 1) {
					// 地下标识
					Intent intent = new Intent(DataCollectActivity.this,
							PipeRecognition.class);
					startActivity(intent);
				}

			}
		});
	}

}

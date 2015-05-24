package com.intelligent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.intelligent.load.DataLoadActivity;
import com.intelligent.search.LabelSearch;
import com.intenlligent.R;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Navigation extends Activity {

	private static final String TAG = "Navigation";
	private 

	// ���ڽػ�������
	MKMapTouchListener mapTouchListener = null;
	private BMapManager mBMapMan = null;
	private MapView mMapView = null;
	public View popView;

	private Toast mToast;
	boolean flag = true;
	/**
	 * ��λSDK�ĺ�����
	 */
	private LocationClient mLocClient;
	/**
	 * �û�λ����Ϣ
	 */
	private LocationData mLocData;
	/**
	 * �ҵ�λ��ͼ��
	 */
	private LocationOverlay myLocationOverlay = null;
	/**
	 * ��������ͼ��
	 */
	private PopupOverlay mPopupOverlay = null;

	// �Ƿ��ֶ���������λ
	private boolean isRequest = false;
	// �Ƿ��״ζ�λ
	private boolean isFirstLoc = true;

	/**
	 * ��������ͼ��View
	 */
	private View viewCache;
	private BDLocation location;

	private MapController mMapController = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager�����������setContentView()�ȳ�ʼ��
		mBMapMan = new BMapManager(this);

		// ��һ��������API key,
		// �ڶ��������ǳ����¼���������������ͨ�������������Ȩ��֤����ȣ���Ҳ���Բ��������ص��ӿ�
		mBMapMan.init("kBbsnvi1NqjHOM4CqPPWtYtg", new MKGeneralListenerImpl());
		setContentView(R.layout.navigation);

		// �����ť�ֶ�����λ
		((Button) findViewById(R.id.navigation_request))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						requestLocation();
					}
				});

		mMapView = (MapView) findViewById(R.id.navigation_bmapView); // ��ȡ�ٶȵ�ͼ�ؼ�ʵ��
		mMapController = mMapView.getController(); // ��ȡ��ͼ������
		mMapController.enableClick(true); // ���õ�ͼ�Ƿ���Ӧ����¼�
		mMapController.setZoom(14); // ���õ�ͼ���ż���
		mMapView.setBuiltInZoomControls(true); // ��ʾ�������ſؼ�

		viewCache = LayoutInflater.from(this).inflate(
				R.layout.navigation_popup, null);
		mPopupOverlay = new PopupOverlay(mMapView, new PopupClickListener() {

			@Override
			public void onClickedPopup(int arg0) {
				mPopupOverlay.hidePop();
			}
		});

		mLocData = new LocationData();

		// ʵ������λ����LocationClient����������߳�������
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(new BDLocationListenerImpl());// ע�ᶨλ�����ӿ�

		/**
		 * ���ö�λ����
		 */
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��GPRS
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000); // ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.disableCache(false);// ��ֹ���û��涨λ
		// option.setPoiNumber(5); //��෵��POI����
		// option.setPoiDistance(1000); //poi��ѯ����
		// option.setPoiExtraInfo(true); //�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ

		mLocClient.setLocOption(option);
		mLocClient.start(); // ���ô˷�����ʼ��λ

		// ��λͼ���ʼ��
		myLocationOverlay = new LocationOverlay(mMapView);
		// ���ö�λ����
		myLocationOverlay.setData(mLocData);

		// ��Ӷ�λͼ��
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();

		// �޸Ķ�λ���ݺ�ˢ��ͼ����Ч
		mMapView.refresh();
		mapTouchListener = new MKMapTouchListener() {

			@Override
			public void onMapLongClick(final GeoPoint point) {

				// TODO Auto-generated method stub
				Builder builder = new Builder(Navigation.this);
				builder.setTitle("��ǩ���");
				builder.setMessage("�Ƿ���ӵ�ͼ��ǰλ�õ�ϵͳ��");
				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						});
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(Navigation.this,
										LabelSearch.class);
								Log.e(TAG,
										"onClick:" + "Latitude:"
												+ point.getLatitudeE6()
												+ "Longitude:"
												+ point.getLongitudeE6());
								intent.putExtra("Latitude",
										(point.getLatitudeE6() * 1.0) / 1e6);
								intent.putExtra("Longitude",
										(point.getLongitudeE6() * 1.0) / 1e6);
								startActivity(intent);
							}
						});
				builder.create().show();
			}

			@Override
			public void onMapDoubleClick(GeoPoint arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMapClick(GeoPoint arg0) {
				// TODO Auto-generated method stub

			}
		};

	}

	/**
	 * ��λ�ӿڣ���Ҫʵ����������
	 * 
	 */
	public class BDLocationListenerImpl implements BDLocationListener {

		/**
		 * �����첽���صĶ�λ�����������BDLocation���Ͳ���
		 */
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}

			Navigation.this.location = location;

			mLocData.latitude = location.getLatitude();
			mLocData.longitude = location.getLongitude();
			// �������ʾ��λ����Ȧ����accuracy��ֵΪ0����
			mLocData.accuracy = location.getRadius();
			mLocData.direction = location.getDerect();

			// ����λ�������õ���λͼ����
			myLocationOverlay.setData(mLocData);
			// ����ͼ������ִ��ˢ�º���Ч
			mMapView.refresh();

			if (isFirstLoc || isRequest) {
				mMapController.animateTo(new GeoPoint((int) (location
						.getLatitude() * 1e6),
						(int) (location.getLongitude() * 1e6)));

				showPopupOverlay(location);

				isRequest = false;
			}

			isFirstLoc = false;
		}

		/**
		 * �����첽���ص�POI��ѯ�����������BDLocation���Ͳ���
		 */
		@Override
		public void onReceivePoi(BDLocation poiLocation) {

		}

	}

	/**
	 * �����¼���������������ͨ�������������Ȩ��֤�����
	 * 
	 */
	public class MKGeneralListenerImpl implements MKGeneralListener {

		/**
		 * һЩ����״̬�Ĵ�����ص�����
		 */
		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				showToast("���������������");
			}
		}

		/**
		 * ��Ȩ�����ʱ����õĻص�����
		 */
		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				showToast("API KEY����, ���飡");
			}
		}

	}

	//
	private class LocationOverlay extends MyLocationOverlay {

		public LocationOverlay(MapView arg0) {
			super(arg0);
		}

		@Override
		protected boolean dispatchTap() {
			showPopupOverlay(location);
			return super.dispatchTap();
		}

		@Override
		public void setMarker(Drawable arg0) {
			super.setMarker(arg0);
		}

	}

	private void showPopupOverlay(BDLocation location) {
		TextView popText = ((TextView) viewCache
				.findViewById(R.id.navigation_popup_tv));
		popText.setText(location.getAddrStr());
		mPopupOverlay.showPopup(getBitmapFromView(popText),
				new GeoPoint((int) (location.getLatitude() * 1e6),
						(int) (location.getLongitude() * 1e6)), 10);
	}

	/**
	 * �ֶ�����λ�ķ���
	 */
	public void requestLocation() {
		isRequest = true;

		if (mLocClient != null && mLocClient.isStarted()) {
			showToast("���ڶ�λ......");
			mLocClient.requestLocation();
		} else {
			Log.d("LocSDK3", "locClient is null or not started");
		}
	}

	/**
	 * ��ʾToast��Ϣ
	 * 
	 * @param msg
	 */
	private void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	/**
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {

		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}

		// �˳�ʱ���ٶ�λ
		if (mLocClient != null) {
			mLocClient.stop();
		}

		super.onResume();
	}

}

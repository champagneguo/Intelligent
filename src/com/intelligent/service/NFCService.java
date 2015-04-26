package com.intelligent.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import com.example.nfc.util.AS3911;
import com.example.nfc.util.Consts;
import com.example.nfc.util.Tools;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class NFCService extends Service {
	private AS3911 as3911;
	private OutputStream mOutputStream;
	private InputStream mInputStream;

	private String activity = "";
	private MyReceiver myReceive; // �㲥������
	private ReadThread mReadThread; // �������߳�
	private boolean run = true;

	private int cmdCode = 0;
	private boolean cmdFlag = false;
	private static final String TAG = "NFCService";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			as3911 = new AS3911(Consts.PORT, 115200, 0); // �豸���ں�Ϊ0��������Ϊ115200
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (as3911 == null) {
			return;
		}
		AS3911.powerOn();// �򿪵�Դ
		mOutputStream = as3911.getOutputStream();
		mInputStream = as3911.getInputStream();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte [] power = new byte[16];
		try {
			int size = mInputStream.read(power);
			Log.i(TAG, "power " );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myReceive = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.intelligent.service.NFCService");
		registerReceiver(myReceive, filter);
		// ע��Broadcast Receiver�����ڹر�Service

		mReadThread = new ReadThread();
		mReadThread.start(); // �������߳�
		Log.i(TAG, "start thread");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		cmdCode = intent.getIntExtra("cmd", 0);
		if(cmdCode == 0){
			cmdFlag = true;
		}else{
			cmdFlag = false;
		}
		Log.i(TAG, "onStartCommand CMD CODE :  " + cmdCode + "");
		return 0;
	}

	/**
	 * @return void
	 * @Method Description:ִ��ָ��
	 * @Autor Jimmy
	 * @Date 2013-11-21
	 */
	private void exeCmd(int cmdcode) {
		byte[] cmdArr = null;
		switch (cmdcode) {
		case Consts.Get_version:
			cmdArr = as3911.getHardwareVersion();//��ȡ�豸�汾
			break;
		case Consts.Init_14443a:
			cmdArr = as3911.card14443aInit(); // 14443A��ʼ��
			break;
		case Consts.GetUID_14443a:
			cmdArr = as3911.card14443aSelect(); // 14443AѰ��
			break;
		case Consts.DeInit_14443a:
			cmdArr = as3911.card14443aDeInit(); // 14443Aȡ����ʼ��
			break;
		case Consts.Mifare_14443aInit:
			cmdArr = as3911.mifare14443aInit();// mifare��ʼ��
			break;
		case Consts.Mifare_14443aAuth:
			cmdArr = as3911.mifare14443aAuth(Consts.AUTH_INFO_14443A); // mifare��֤
			break;
		case Consts.Mifare_14443aRead:
			cmdArr = as3911.mifare14443aRead(Consts.READ_INFO_14443A);// mifare����
			break;
		case Consts.Mifare_14443aWrite:
			cmdArr = as3911.mifare14443aWrite(Consts.WRITE_INFO_14443A);// mifareд����
			break;
		case Consts.CmdCode_Init_15693://15693��ʼ��
			cmdArr = as3911.card15693Init();
			break;
		case Consts.CmdCode_DeInit_15693://15693ȡ����ʼ��
			cmdArr = as3911.card15693DeInit();
			break;
		case Consts.CmdCode_1_SlotInventory_15693://1 slot inventory
			cmdArr = as3911.card15693OneSlotInventory();
			break;
		case Consts.CmdCode_GetSysInfo_15693://��ȡ15693��ϵͳ��Ϣ
			cmdArr = as3911.card15693GetSysInfo(Consts.Card15693_UID);
			break;
		case Consts.CmdCode_ReadSingleBlock_15693://��ȡ����һ��
			cmdArr = as3911.card15693Read(Consts.Card15693_UID_Block);
			break;
		case Consts.CmdCode_WriteSingleBlock_15693://д����һ��
			cmdArr = as3911.card15693Write(Consts.Card15693_UID_Block_Data);
			break;
		default:
			break;
		}

		if (cmdArr != null) {
			try {
				mOutputStream.write(cmdArr); // ��������
				Log.i(TAG, "SEN CMD SUCCESS" + cmdcode);
				Log.i(TAG, "CMD" + Tools.Bytes2HexString(cmdArr, cmdArr.length));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDestroy() {
		if (mReadThread != null)
			run = false; // �ر��߳�
		AS3911.powerOff();
		as3911.close(Consts.PORT); // �رմ���
		unregisterReceiver(myReceive); // ж��ע��
		Log.i(TAG, "service onDestroy");
		super.onDestroy();

	}

	/**
	 * ���߳� ,��ȡ�豸���ص���Ϣ������ش������������activity
	 * 
	 * @author Jimmy Pang
	 * 
	 */
	private class ReadThread extends Thread {
		byte[] recv_data = null;
		String cardUID = null;//��ƬUID
		Intent recvIntent = new Intent();
		Intent recvIntent1 = new Intent();
		@Override
		public void run() {
			super.run();
			while (run) {
				if(!cmdFlag){
					recv_data = null;
					recvIntent.setAction("com.intelligent.search.LabelSearch");
					recvIntent1.setAction("com.intelligent.search.PipeRecognition");
					//��ȡ�汾��Ϣ
					if(cmdCode == Consts.Get_version){
						Log.i(TAG, "get version");
						exeCmd(Consts.Get_version);
						recv_data = getRecv_data();
						if(recv_data != null){
							String version;
							try {
								version = new String(recv_data,"UTF-8");
								Log.i(TAG, "version " + version);
								recvIntent.putExtra("result", version);
								sendBroadcast(recvIntent);
								recvIntent1.putExtra("result", version);
								sendBroadcast(recvIntent1);
								recv_data = null;
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						cmdCode = 0;
					}
					//��ʼ��14443A
					if(cmdCode == Consts.Init_14443a){
						Log.i(TAG, "14443A init*******");
						exeCmd(Consts.Init_14443a);//��ʼ��
						recv_data = getRecv_data();
						if(recv_data != null){
							cmdCode = Consts.GetUID_14443a;
							recv_data = null;
						}else{
//							cmdCode = Consts.CmdCode_DeInit_15693;
							cmdCode = Consts.CmdCode_Init_15693;
						}
					}
					//��ȡUID
					if(cmdCode == Consts.GetUID_14443a){
						Log.i(TAG, "14443A GET UID*******");
						exeCmd(Consts.GetUID_14443a);//Ѱ��
						recv_data = getRecv_data();
						if(recv_data != null){
							cardUID = Tools.resolveUID(Tools.Bytes2HexString(recv_data, recv_data.length));
//							
							if(cardUID != null && !cardUID.equals("")){
								Log.i(TAG, "14443A  " + cardUID);
								recvIntent.putExtra("result", "ISO14443A " + cardUID);
								sendBroadcast(recvIntent);
								recvIntent1.putExtra("result", "ISO14443A " + cardUID);
								sendBroadcast(recvIntent1);
							}
//							cmdCode = Consts.CmdCode_DeInit_15693;
							cmdCode = Consts.CmdCode_Init_15693;
//							cmdCode = Consts.Init_14443a;
							recv_data = null;
						}else{
//							cmdCode = Consts.CmdCode_DeInit_15693;
							cmdCode = Consts.CmdCode_Init_15693;
//							cmdCode = Consts.Init_14443a;
						}
					}
					//14443Aȡ����ʼ��
					
					//15693ȡ����ʼ��
					if(cmdCode == Consts.CmdCode_DeInit_15693){
						Log.i(TAG, "15963 DEINIT *******");
						exeCmd(Consts.CmdCode_DeInit_15693);
						recv_data = getRecv_data();
						if(recv_data != null){
//							cmdCode = Consts.CmdCode_1_SlotInventory_15693;
							cmdCode = Consts.CmdCode_Init_15693;
							recv_data = null;
						}else{
							cmdCode = Consts.Init_14443a;
//							cmdCode = 0;
						}
					}
					//15693��ʼ��
					if(cmdCode == Consts.CmdCode_Init_15693){
						Log.i(TAG, "15963 INIT *******");
						exeCmd(Consts.CmdCode_Init_15693);
						recv_data = getRecv_data();
						if(recv_data != null){
							cmdCode = Consts.CmdCode_1_SlotInventory_15693;
						}else{
							cmdCode = Consts.Init_14443a;
//							cmdCode = 0;
						}
					}
					//15693��UID
					if(cmdCode == Consts.CmdCode_1_SlotInventory_15693){
						Log.i(TAG, "15693 inventory***");
						exeCmd(Consts.CmdCode_1_SlotInventory_15693);
						recv_data = getRecv_data();
						if(recv_data != null){
							String card15693UID = Tools.resolve15693UID(Tools.Bytes2HexString(recv_data, recv_data.length));
							Log.i(TAG, "15693 uid : " + card15693UID);
							recvIntent.putExtra("result", "ISO15693 " + card15693UID);
							sendBroadcast(recvIntent);
							recvIntent1.putExtra("result", "ISO15693 " + card15693UID);
							sendBroadcast(recvIntent1);
							cmdCode = Consts.Init_14443a;
						}else{
							
							cmdCode = Consts.Init_14443a;
						}
					}
				}
			}
		}
	}
	/**
	 *@return byte[]
	 *@Method Description:�Ӵ����л�ȡ����
	 *@Autor Jimmy
	 *@Date 2013-12-3
	 */
	private byte[] getRecv_data(){
		int count = 0;
		int index = 0;
		byte[] recvDataBuffer = null;
		byte[] recvData = null;
		try {
		while(count < 1){
			count = mInputStream.available();
			//��ȡ���ݳ�ʱ
			if(index > 50){
				return null;
			}else{
				index++;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		count = mInputStream.available();
		recvDataBuffer = new byte[count];
		mInputStream.read(recvDataBuffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*=============��������==============*/
		if(recvDataBuffer != null){
			recvData = as3911.resolveResp(recvDataBuffer);
			Log.e("recvData", Tools.Bytes2HexString(recvDataBuffer, recvDataBuffer.length));
		}
		return recvData;
	}

	/**
	 * �㲥������
	 * 
	 * @author Jimmy Pang
	 * 
	 */
	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String ac = intent.getStringExtra("activity");
			if (ac != null)
				Log.i("receive activity", ac);
			activity = ac; // ��ȡactivity
			if (intent.getBooleanExtra("stopflag", false))
				stopSelf(); // �յ�ֹͣ�����ź�
			Log.i("stop service", intent.getBooleanExtra("stopflag", false)
					+ "");

		}

	}
}


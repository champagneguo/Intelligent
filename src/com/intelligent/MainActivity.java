package com.intelligent;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.intelligent.greendao.DaoMaster;
import com.intelligent.greendao.DaoMaster.DevOpenHelper;
import com.intelligent.greendao.DaoSession;
import com.intelligent.greendao.gdlx;
import com.intelligent.greendao.gdlxDao;
import com.intelligent.greendao.guandian;
import com.intelligent.greendao.guandianDao;
import com.intelligent.greendao.guanxian;
import com.intelligent.greendao.guanxianDao;
import com.intelligent.greendao.gxlb;
import com.intelligent.greendao.gxlbDao;
import com.intelligent.greendao.jlx;
import com.intelligent.greendao.jlxDao;
import com.intelligent.greendao.shuxing;
import com.intelligent.greendao.shuxingDao;
import com.intenlligent.R;

public class MainActivity extends Activity {

	private String TAG = "MainActivity";
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private SQLiteDatabase db;
	private gdlxDao mgdlxDao;
	private guandianDao mguandianDao;
	private guanxianDao mguanxianDao;
	private gxlbDao mgxlbDao;
	private jlxDao mjlxDao;
	private shuxingDao mshuxingDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 隐去标题栏（应用程序的名字）
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐去状态栏部分(电池等图标和一切修饰部分)
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		Log.e(TAG, "onCreate()");
		DevOpenHelper myHelper = new DevOpenHelper(MainActivity.this,
				"intelligentDao.db", null);
		db = myHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		mgdlxDao = daoSession.getGdlxDao();
		mguandianDao = daoSession.getGuandianDao();
		mguanxianDao = daoSession.getGuanxianDao();
		mgxlbDao = daoSession.getGxlbDao();
		mjlxDao = daoSession.getJlxDao();
		mshuxingDao = daoSession.getShuxingDao();

		// 让一个Activity过一段时间自动跳转到下一个Activity；
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(MainActivity.this, Login.class);
				MainActivity.this.startActivity(mainIntent);
				MainActivity.this.finish();
				// overridePendingTransition(android.R.anim.fade_in,android.R.anim.slide_in_left);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		}, 3000);

		initGdlx();
		// initmguandian();
		// initmguanxian();
		initmgxlb();
		initmjlx();
		initmshuxing();
		Log.e(TAG, "END!!!!!!!!!!!!!!");

	}

	private void initGdlx() {
		Log.e(TAG, "initGdlx()");
		mgdlxDao.deleteAll();
		gdlx mgdlx = new gdlx();
		mgdlx.setGdlxcode(1L);
		mgdlx.setGdlxname("检修井");
		mgdlxDao.insertOrReplace(mgdlx);
		Log.e(TAG, "initGdlx()------------>>" + mgdlx.getGdlxname());
		mgdlx.setGdlxcode(2L);
		mgdlx.setGdlxname("上杆");
		mgdlxDao.insertOrReplace(mgdlx);
		Log.e(TAG, "initGdlx()------------>>" + mgdlx.getGdlxname());
		mgdlx.setGdlxcode(3L);
		mgdlx.setGdlxname("接线箱");
		mgdlxDao.insertOrReplace(mgdlx);
		mgdlx.setGdlxcode(4L);
		mgdlx.setGdlxname("偏心井");
		mgdlxDao.insertOrReplace(mgdlx);
		mgdlx.setGdlxcode(5L);
		mgdlx.setGdlxname("分支");
		mgdlxDao.insertOrReplace(mgdlx);
		mgdlx.setGdlxcode(6L);
		mgdlx.setGdlxname("非普区");
		mgdlxDao.insertOrReplace(mgdlx);
		mgdlx.setGdlxcode(7L);
		mgdlx.setGdlxname("入户");
		mgdlxDao.insertOrReplace(mgdlx);
		mgdlx.setGdlxcode(8L);
		mgdlx.setGdlxname("预留口");
		mgdlxDao.insertOrReplace(mgdlx);
		mgdlx.setGdlxcode(9L);
		mgdlx.setGdlxname("直线点");
		mgdlxDao.insertOrReplace(mgdlx);
		mgdlx.setGdlxcode(10L);
		mgdlx.setGdlxname("转折");
		mgdlxDao.insertOrReplace(mgdlx);
		Log.e(TAG, "initGdlx()------------>>" + mgdlx.getGdlxname());
	}

	private void initmguandian() {

		mguandianDao.deleteAll();
		guandian mguandian = new guandian();
		mguandian.setGuandiancode(1L);
		mguandian.setGuanlei("供电");
		mguandian.setGuanxianqidianhao("DL1264");
		mguandian.setTezheng("");
		mguandian.setFushuwu("检修井");
		mguandian.setDianleixing("检修井");
		mguandian.setJingleixing("检修井");
		mguandian.setDaolumingcheng("上地七街");
		mguandian.setJingdu("309045.136");
		mguandian.setWeidu("497010.925");
		mguandian.setDimiangaocheng("51.961");
		mguandian.setGuandiangaocheng("0");
		mguandian.setBiaoqiancode("");
		mguandianDao.insert(mguandian);
		mguandian.setGuandiancode(2L);
		mguandian.setGuanlei("供电");
		mguandian.setGuanxianqidianhao("DL114");
		mguandian.setTezheng("");
		mguandian.setFushuwu("检修井");
		mguandian.setDianleixing("检修井");
		mguandian.setJingleixing("检修井");
		mguandian.setDaolumingcheng("上地七街");
		mguandian.setJingdu("309463.579");
		mguandian.setWeidu("496891.52");
		mguandian.setDimiangaocheng("52.689");
		mguandian.setGuandiangaocheng("0");
		mguandian.setBiaoqiancode("");
		mguandianDao.insert(mguandian);
		mguandian.setGuandiancode(3L);
		mguandian.setGuanlei("供电");
		mguandian.setGuanxianqidianhao("DL293");
		mguandian.setTezheng(" ");
		mguandian.setFushuwu("检修井");
		mguandian.setDianleixing(" ");
		mguandian.setJingleixing("检修井");
		mguandian.setDaolumingcheng("上地七街");
		mguandian.setJingdu("309261.648");
		mguandian.setWeidu("497038.229");
		mguandian.setDimiangaocheng("52.674");
		mguandian.setGuandiangaocheng("0");
		mguandian.setBiaoqiancode("");
		mguandianDao.insert(mguandian);
		mguandian.setGuandiancode(4L);
		mguandian.setGuanlei("供电");
		mguandian.setGuanxianqidianhao("1DL460");
		mguandian.setTezheng(" ");
		mguandian.setFushuwu("上杆");
		mguandian.setDianleixing(" ");
		mguandian.setJingleixing(" ");
		mguandian.setDaolumingcheng("上地七街");
		mguandian.setJingdu("309140.357");
		mguandian.setWeidu("496787.265");
		mguandian.setDimiangaocheng("52.223");
		mguandian.setGuandiangaocheng("0");
		mguandian.setBiaoqiancode("");
		mguandianDao.insert(mguandian);
		mguandian.setGuandiancode(5L);
		mguandian.setGuanlei("供电");
		mguandian.setGuanxianqidianhao("1DL505");
		mguandian.setTezheng(" ");
		mguandian.setFushuwu("检修井");
		mguandian.setDianleixing(" ");
		mguandian.setJingleixing("检修井");
		mguandian.setDaolumingcheng("上地七街");
		mguandian.setJingdu("309131.841");
		mguandian.setWeidu("496657.032");
		mguandian.setDimiangaocheng("51.885");
		mguandian.setGuandiangaocheng("0");
		mguandian.setBiaoqiancode("");
		mguandianDao.insert(mguandian);

	}

	private void initmguanxian() {

		mguanxianDao.deleteAll();
		guanxian mguanxian = new guanxian();
		mguanxian.setGuanxiancode(1L);
		mguanxian.setGuanxianleixing("供电");
		mguanxian.setGuanxianqidianhao("1DL12");
		mguanxian.setGuanxianzhongdianhao("1DL10");
		mguanxian.setGuanxianqidianmaishen(".6");
		mguanxian.setGuanxianzhongdianmaishen(".82");
		mguanxian.setDuanmianzhicun("400X400");
		mguanxian.setJiemiandaxiao(" ");
		mguanxian.setCaizhi("铜");
		mguanxian.setMaishefangshi("管埋");
		mguanxian.setZongkongshu("4");
		mguanxian.setYiyongkongshu("2");
		mguanxian.setDianlantiaoshu("2");
		mguanxian.setDianyazhi("10KV");
		mguanxian.setYalileixing(" ");
		mguanxian.setPaishuiliuxiang(" ");
		mguanxian.setBaohucailiao("塑料");
		mguanxian.setPuchaniandai(" ");
		mguanxian.setQuanshudanwei("北京路灯处");
		mguanxian.setDimiangaocheng("");
		mguanxian.setGuanxiandiangaocheng("");
		mguanxian.setDianbeizhu("");
		mguanxian.setShifoujiebian("");
		mguanxian.setDaolumingcheng("");
		mguanxianDao.insert(mguanxian);
		mguanxian.setGuanxiancode(2L);
		mguanxian.setGuanxianleixing("供电");
		mguanxian.setGuanxianqidianhao("1DL10");
		mguanxian.setGuanxianzhongdianhao("1DL9");
		mguanxian.setGuanxianqidianmaishen(".82");
		mguanxian.setGuanxianzhongdianmaishen(".82");
		mguanxian.setDuanmianzhicun("400X400");
		mguanxian.setJiemiandaxiao(" ");
		mguanxian.setCaizhi("铜");
		mguanxian.setMaishefangshi("管埋");
		mguanxian.setZongkongshu("4");
		mguanxian.setYiyongkongshu("2");
		mguanxian.setDianlantiaoshu("2");
		mguanxian.setDianyazhi("10KV");
		mguanxian.setYalileixing(" ");
		mguanxian.setPaishuiliuxiang(" ");
		mguanxian.setBaohucailiao("塑料");
		mguanxian.setPuchaniandai(" ");
		mguanxian.setQuanshudanwei("");
		mguanxian.setDimiangaocheng("");
		mguanxian.setGuanxiandiangaocheng("");
		mguanxian.setDianbeizhu("");
		mguanxian.setShifoujiebian("");
		mguanxian.setDaolumingcheng("");
		mguanxianDao.insert(mguanxian);
		mguanxian.setGuanxiancode(3L);
		mguanxian.setGuanxianleixing("供电");
		mguanxian.setGuanxianqidianhao("1DL9");
		mguanxian.setGuanxianzhongdianhao("1DL11");
		mguanxian.setGuanxianqidianmaishen(".96");
		mguanxian.setGuanxianzhongdianmaishen(".2");
		mguanxian.setDuanmianzhicun("400X400");
		mguanxian.setJiemiandaxiao(" ");
		mguanxian.setCaizhi("铜");
		mguanxian.setMaishefangshi("管埋");
		mguanxian.setZongkongshu("4");
		mguanxian.setYiyongkongshu("2");
		mguanxian.setDianlantiaoshu("2");
		mguanxian.setDianyazhi("10KV");
		mguanxian.setYalileixing(" ");
		mguanxian.setPaishuiliuxiang(" ");
		mguanxian.setBaohucailiao("塑料");
		mguanxian.setPuchaniandai(" ");
		mguanxian.setQuanshudanwei("北京路灯处");
		mguanxian.setDimiangaocheng("");
		mguanxian.setGuanxiandiangaocheng("");
		mguanxian.setDianbeizhu("");
		mguanxian.setShifoujiebian("");
		mguanxian.setDaolumingcheng("");
		mguanxianDao.insert(mguanxian);
		mguanxian.setGuanxiancode(4L);
		mguanxian.setGuanxianleixing("供电");
		mguanxian.setGuanxianqidianhao("1DL1");
		mguanxian.setGuanxianzhongdianhao("1DL2");
		mguanxian.setGuanxianqidianmaishen("1.3");
		mguanxian.setGuanxianzhongdianmaishen("1.62");
		mguanxian.setDuanmianzhicun("600X150");
		mguanxian.setJiemiandaxiao(" ");
		mguanxian.setCaizhi("铜");
		mguanxian.setMaishefangshi("管埋");
		mguanxian.setZongkongshu("4");
		mguanxian.setYiyongkongshu("2");
		mguanxian.setDianlantiaoshu("2");
		mguanxian.setDianyazhi("10KV");
		mguanxian.setYalileixing(" ");
		mguanxian.setPaishuiliuxiang(" ");
		mguanxian.setBaohucailiao("塑料");
		mguanxian.setPuchaniandai(" ");
		mguanxian.setQuanshudanwei("北京路灯处");
		mguanxian.setDimiangaocheng(" ");
		mguanxian.setGuanxiandiangaocheng(" ");
		mguanxian.setDianbeizhu(" ");
		mguanxian.setShifoujiebian(" ");
		mguanxian.setDaolumingcheng(" ");
		mguanxianDao.insert(mguanxian);
		mguanxian.setGuanxiancode(5L);
		mguanxian.setGuanxianleixing("供电");
		mguanxian.setGuanxianqidianhao("1DL2");
		mguanxian.setGuanxianzhongdianhao("1DL4");
		mguanxian.setGuanxianqidianmaishen("1.62");
		mguanxian.setGuanxianzhongdianmaishen("1.7");
		mguanxian.setDuanmianzhicun("600X150");
		mguanxian.setJiemiandaxiao(" ");
		mguanxian.setCaizhi("铜");
		mguanxian.setMaishefangshi("管埋");
		mguanxian.setZongkongshu("4");
		mguanxian.setYiyongkongshu("2");
		mguanxian.setDianlantiaoshu("2");
		mguanxian.setDianyazhi("10KV");
		mguanxian.setYalileixing(" ");
		mguanxian.setPaishuiliuxiang(" ");
		mguanxian.setBaohucailiao("塑料");
		mguanxian.setPuchaniandai(" ");
		mguanxian.setQuanshudanwei("北京路灯处");
		mguanxian.setDimiangaocheng(" ");
		mguanxian.setGuanxiandiangaocheng(" ");
		mguanxian.setDianbeizhu(" ");
		mguanxian.setShifoujiebian(" ");
		mguanxian.setDaolumingcheng(" ");
		mguanxianDao.insert(mguanxian);

	}

	private void initmgxlb() {

		mgxlbDao.deleteAll();
		gxlb mgxlb = new gxlb();
		mgxlb.setGxlbcode(1L);
		mgxlb.setGxlbname("电信");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(2L);
		mgxlb.setGxlbname("中水");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(3L);
		mgxlb.setGxlbname("污水");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(4L);
		mgxlb.setGxlbname("电力");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(5L);
		mgxlb.setGxlbname("燃气");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(6L);
		mgxlb.setGxlbname("热力");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(7L);
		mgxlb.setGxlbname("给水");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(8L);
		mgxlb.setGxlbname("路灯");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(9L);
		mgxlb.setGxlbname("雨水");
		mgxlbDao.insert(mgxlb);
		mgxlb.setGxlbcode(10L);
		mgxlb.setGxlbname("有视");
		mgxlbDao.insert(mgxlb);

	}

	private void initmjlx() {

		mjlxDao.deleteAll();
		jlx mjlx = new jlx();
		mjlx.setJlxcode(1L);
		mjlx.setJlxname("检修井");
		mjlxDao.insert(mjlx);
		mjlx.setJlxcode(2L);
		mjlx.setJlxname("偏心井");
		mjlxDao.insert(mjlx);

	}

	private void initmshuxing() {

		mshuxingDao.deleteAll();
		shuxing mshuxing = new shuxing();
		mshuxing.setShuxingcode(1L);
		mshuxing.setShuxingname("管线起点号");
		mshuxing.setShuxingziduan("guanxianqidianhao");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(2L);
		mshuxing.setShuxingname("特征");
		mshuxing.setShuxingziduan("tezheng");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(3L);
		mshuxing.setShuxingname("附属物");
		mshuxing.setShuxingziduan("fushuwu");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(4L);
		mshuxing.setShuxingname("道路名称");
		mshuxing.setShuxingziduan("daolumingcheng");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(5L);
		mshuxing.setShuxingname("地面高程");
		mshuxing.setShuxingziduan("dimiangaocheng");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(6L);
		mshuxing.setShuxingname("管线点高程");
		mshuxing.setShuxingziduan("guanxiandiangaocheng");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(7L);
		mshuxing.setShuxingname("点备注");
		mshuxing.setShuxingziduan("dianbeizhu");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(8L);
		mshuxing.setShuxingname("是否接头");
		mshuxing.setShuxingziduan("shifoujietou");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(9L);
		mshuxing.setShuxingname("管点终点号");
		mshuxing.setShuxingziduan("guandianzhongdianhao");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(10L);
		mshuxing.setShuxingname("管点起点埋深");
		mshuxing.setShuxingziduan("guanxianqidianmaishen");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(11L);
		mshuxing.setShuxingname("管线终点埋深");
		mshuxing.setShuxingziduan("guanxianzhongdianmaishen");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(12L);
		mshuxing.setShuxingname("断面尺寸");
		mshuxing.setShuxingziduan("duanmianchicun");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(13L);
		mshuxing.setShuxingname("材质");
		mshuxing.setShuxingziduan("caizhi");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(14L);
		mshuxing.setShuxingname("埋设方式");
		mshuxing.setShuxingziduan("maishefangshi");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(15L);
		mshuxing.setShuxingname("总孔数");
		mshuxing.setShuxingziduan("zongkongshu");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(16L);
		mshuxing.setShuxingname("电缆条数");
		mshuxing.setShuxingziduan("dianlantiaoshu");
		mshuxingDao.insert(mshuxing);
		mshuxing.setShuxingcode(17L);
		mshuxing.setShuxingname("电压");
		mshuxing.setShuxingziduan("dianya");
		mshuxingDao.insert(mshuxing);

	}

}

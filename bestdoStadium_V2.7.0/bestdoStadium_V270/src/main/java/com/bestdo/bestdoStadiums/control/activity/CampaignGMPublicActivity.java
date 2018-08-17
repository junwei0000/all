package com.bestdo.bestdoStadiums.control.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.AddWonderfulBusiness;
import com.bestdo.bestdoStadiums.business.ClubGetManageBusiness;
import com.bestdo.bestdoStadiums.business.AddWonderfulBusiness.AddWonderfulCallback;
import com.bestdo.bestdoStadiums.business.ClubGetManageBusiness.ClubGetManageCallback;
import com.bestdo.bestdoStadiums.business.GetQiNiuTokenBusiness;
import com.bestdo.bestdoStadiums.business.GetQiNiuTokenBusiness.GetQiNiuTokenCallback;
import com.bestdo.bestdoStadiums.control.adapter.ClubSelectAdapter;
import com.bestdo.bestdoStadiums.control.photo.activity.GalleryActivity;
import com.bestdo.bestdoStadiums.control.photo.adapter.SelectPhotoAdapter;
import com.bestdo.bestdoStadiums.control.photo.util.Bimp;
import com.bestdo.bestdoStadiums.control.photo.util.ImageItem;
import com.bestdo.bestdoStadiums.control.photo.vedio.AblumUtils;
import com.bestdo.bestdoStadiums.control.photo.vedio.FilePathResolver;
import com.bestdo.bestdoStadiums.control.photo.vedio.PlayerUtils;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.FileCache;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.MyGridView;
import com.bestdo.bestdoStadiums.utils.parser.AddWonderfulParser;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.Zone;
//import com.qiniu.android.http.ResponseInfo;
//import com.qiniu.android.storage.UpCompletionHandler;
//import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadManager;
import com.zhy.view.HorizontalProgressBarWithNumber;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2017-3-12 上午11:47:26
 * @Description 类描述：精彩时刻发布活动
 */
public class CampaignGMPublicActivity extends BaseActivity {

	private FileCache mFileCache;
	private PlayerUtils mPlayerUtils;
	private MyGridView edit_gv_addablum;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView pagetop_iv_you;
	private AblumUtils mAblumUtils;
	private EditText campaignpublic_et_title;
	private EditText campaignpublic_et_content;
	private RelativeLayout relay_club;
	private String clubid_Buffer = "";// "1,2"
	private TextView tv_name;

	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private boolean isCancelled = false;
	private String vedio_name = "";
	private String act_title = "";
	private String act_content = "";
	private String type = "";

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.pagetop_iv_you:
			/*
			 * 关闭键盘
			 */
			CommonUtils.getInstance().closeSoftInput(CampaignGMPublicActivity.this);
			act_title = campaignpublic_et_title.getText().toString();
			act_content = campaignpublic_et_content.getText().toString();
			if (isSelect()) {
				if (Bimp.tempSelectBitmap.size() > 1) {
					if (mAblumUtils.selectVedioStatus) {// 视频
						type = "video";
						getQiniuToken();

					} else {// 图片
						type = "image";
						addWonderful();
					}
				} else {// 文字
					type = "text";
					addWonderful();
				}
			}

			break;
		case R.id.relay_club:
			Intent intent = new Intent(this, ClubSelectActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("clubid_Buffer", clubid_Buffer);
			startActivityForResult(intent, Constans.getInstance().searchForResultBySearchKWPage);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		default:
			break;
		}
	}

	public Boolean isSelect() {
		if (TextUtils.isEmpty(clubid_Buffer)) {
			CommonUtils.getInstance().initToast(context, "至少选择一个俱乐部");
			return false;
		}
		if (TextUtils.isEmpty(act_content) && Bimp.tempSelectBitmap.size() == 1) {
			CommonUtils.getInstance().initToast(context, "请输入内容或上传图片、视频！");
			return false;
		}
		return true;
	}

	int before = 0;
	private TextView tv_name_select;

	/**
	 * 上传七牛
	 */
	private void qiNiuPut() {
		isCancelled = false;
		Configuration config = new Configuration.Builder().chunkSize(256 * 1024) // 分片上传时，每片的大小。
																					// 默认256K
				.putThreshhold(512 * 1024) // 启用分片上传阀值。默认512K
				.connectTimeout(10) // 链接超时。默认10秒
				.responseTimeout(60) // 服务器响应超时。默认60秒
				.recorder(null) // recorder分片上传时，已上传片记录器。默认null
				.zone(Zone.zone1) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
				.build();
		UploadManager uploadManager = new UploadManager(config);
		Map<String, String> params = new HashMap<String, String>();
		params.put("x:a", "test");
		params.put("x:b", "test2");
		// params Map<String, String> 自定义变量，key 必须以 x: 开始
		// mimeType String 指定文件的 mimeType
		// checkCrc boolean 是否验证上传文件
		// progressHandler UpProgressHandler 上传进度回调
		// cancellationSignal UpCancellationSignal 取消上传，当 isCancelled() 返回 true
		// 时，不再执行更多上传
		UploadOptions options = new UploadOptions(params, null, false, new UpProgressHandler() {
			@Override
			public void progress(String key, double percent) {
				// if (percent >= 0.8) {
				// isCancelled = true;
				// }
				double d = percent;
				int ii = (int) (d * 100);
				if (before != ii && ii > before) {
					Message msg = new Message();
					msg.what = 0x111;
					msg.arg1 = ii;
					mHandler.sendMessage(msg);
				}
				before = ii;
			}
		}, new UpCancellationSignal() {
			@Override
			public boolean isCancelled() {
				return isCancelled;
			}
		});
		// @param data 上传的数据
		// * @param key 上传数据保存的文件名
		// * @param token 上传凭证
		// * @param complete 上传完成后续处理动作
		// * @param options 上传数据的可选参数
		uploadManager.put(select_vedio_file, select_vedio_file.getName(), token, new UpCompletionHandler() {

			@Override
			public void complete(String key, ResponseInfo info, JSONObject res) {
				// res包含hash、key等信息，具体字段取决于上传策略的设置
				if (info.isOK()) {
					Log.i("qiniu", "Upload Success");
					if (res != null) {
						vedio_name = res.optString("key");
						vedio_name = domain + "/" + vedio_name;
						Log.e("vedio_name", vedio_name);
						addWonderful();
					}
				} else {
					Log.i("qiniu", "Upload Fail");
					// 如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
				}
				Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
			}
		}, options);
	}

	// 点击取消按钮，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
	private void cancell() {
		isCancelled = true;
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.campaigngmpublic);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		Bimp.tempSelectBitmap.clear();
	}

	@Override
	protected void findViewById() {
		LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		edit_gv_addablum = (MyGridView) findViewById(R.id.edit_gv_addablum);
		campaignpublic_et_title = (EditText) findViewById(R.id.campaignpublic_et_title);
		campaignpublic_et_content = (EditText) findViewById(R.id.campaignpublic_et_content);
		relay_club = (RelativeLayout) findViewById(R.id.relay_club);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_name_select = (TextView) findViewById(R.id.tv_name_select);
		setLoseFocusable();
	}

	/**
	 * 设置输入框失去焦点
	 */
	private void setLoseFocusable() {
		// ------阻止EditText得到焦点，以防输入法弄丑画面
		campaignpublic_et_content.setFocusable(true);
		campaignpublic_et_content.setFocusableInTouchMode(true);
		campaignpublic_et_content.requestFocus();
		// 初始不让EditText得焦点搜索,让焦点指到任一个textView1中
		campaignpublic_et_content.requestFocusFromTouch();
	}

	@Override
	protected void setListener() {
		relay_club.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		pagetop_iv_you.setOnClickListener(this);
		pagetop_tv_name.setText("精彩时刻");
		pagetop_iv_you.setBackgroundResource(R.drawable.campaign_gmimg_commit);
		pagetop_iv_you.setVisibility(View.VISIBLE);
		/**
		 * 完美解决EditText和ScrollView的滚动冲突
		 */
		campaignpublic_et_content.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				// 触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
				if ((view.getId() == R.id.campaignpublic_et_content && canVerticalScroll(campaignpublic_et_content))) {
					view.getParent().requestDisallowInterceptTouchEvent(true);
					if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
						view.getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
				return false;
			}
		});
	}

	/**
	 * EditText竖直方向是否可以滚动
	 * 
	 * @param editText
	 *            需要判断的EditText
	 * @return true：可以滚动 false：不可以滚动
	 */
	private boolean canVerticalScroll(EditText editText) {
		// 滚动的距离
		int scrollY = editText.getScrollY();
		// 控件内容的总高度
		int scrollRange = editText.getLayout().getHeight();
		// 控件实际显示的高度
		int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop()
				- editText.getCompoundPaddingBottom();
		// 控件内容总高度与实际显示高度的差值
		int scrollDifference = scrollRange - scrollExtent;

		if (scrollDifference == 0) {
			return false;
		}

		return (scrollY > 0) || (scrollY < scrollDifference - 1);
	}

	@Override
	protected void processLogic() {

		mFileCache = new FileCache(this);
		mPlayerUtils = new PlayerUtils();

		mAblumUtils = new AblumUtils(this, mHandler, UPDATEABLUM);
		mAblumUtils.addEditBimp(this, null);
		reShowSelectPhoto();
		getClubName();
	}

	/**
	 * 拍照和相册选择后---重新加载选择的图片
	 */
	ArrayList<File> mAddAblumFileList = new ArrayList<File>();

	private void reShowSelectPhoto() {
		ArrayList<ImageItem> selectbimp = Bimp.tempSelectBitmap;
		if (selectbimp != null && selectbimp.size() > 0) {
			mAddAblumFileList.clear();
			if (Bimp.tempSelectBitmap.size() == 1) {
				mAblumUtils.selectVedioStatus = false;
			}
			for (int i = 0; i < selectbimp.size() - 1; i++) {
				File ablumFile = new File(selectbimp.get(i).getImagePath());
				Bitmap bitmap = mAblumUtils.setFileToBitmap(ablumFile);
				ablumFile = mAblumUtils.saveBitmapFile(bitmap);
				System.err.println("ablumFile.getPath()====" + ablumFile.getPath());
				if (selectbimp.size() - 1 != 0)
					mAddAblumFileList.add(ablumFile);
			}
			SelectPhotoAdapter mSelectPhotoAdapter = new SelectPhotoAdapter(this, mAblumUtils, selectbimp,
					mphotoHandler);
			edit_gv_addablum.setAdapter(mSelectPhotoAdapter);
		}
	}

	private ProgressDialog mDialog;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog2(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected ArrayList<MyJoinClubmenuInfo> clubList;

	/**
	 * 得到俱乐部，一个俱乐部时默认显示
	 */
	public void getClubName() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		new ClubGetManageBusiness(this, mhashmap, new ClubGetManageCallback() {
			private String status;

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					status = (String) dataMap.get("code");
					if (status.equals("200")) {
						clubList = (ArrayList<MyJoinClubmenuInfo>) dataMap.get("clubList");
						if (clubList != null && clubList.size() > 0) {
							if (clubList.size() == 1) {
								tv_name_select.setText(clubList.get(0).getClub_name());
								clubid_Buffer = clubList.get(0).getClub_id();
								clubname_Buffer = clubList.get(0).getClub_name();
								relay_club.setOnClickListener(null);
								relay_club.setClickable(false);
							} else {
								relay_club.setClickable(true);
							}
						}
					}
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
			}
		});

	}

	/**
	 * 选择相册后返回刷新列表
	 */
	@Override
	protected void onRestart() {
		if (mAblumUtils != null && mAblumUtils.selectAblumStatus)
			reShowSelectPhoto();
		super.onRestart();
	}

	private HashMap<String, String> mhashmap;
	private String token = "";
	private String domain = "";

	/**
	 * 获取七牛token
	 */
	public void getQiniuToken() {
		// uid 用户ID string 是
		// token 验证token string 是 token生成规则：md5(用户id+加密key)
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", "" + uid);
		mhashmap.put("token", ConfigUtils.getInstance().MD5(uid + "zecepErPQ4UxtxK@GN6K48.fiC%R++"));

		new GetQiNiuTokenBusiness(context, mhashmap, new GetQiNiuTokenCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				// TODO Auto-generated method stub
				if (dataMap.get("code").equals("200")) {
					token = (String) dataMap.get("token");
					domain = (String) dataMap.get("domain");
					Log.e("token,domain", "token=" + token + "domain=" + domain);
					defineBackPressed(context);
					qiNiuPut();
				} else {

				}
			}
		});
	}

	/*
	 * 调用相册
	 */
	protected static final int UPDATEABLUM = 3;
	protected static final int SETRESULT = 4;

	//
	protected static final int UPLOADABLUM = 5;
	protected static final int PUBLISHCAMPAIGN = 6;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String selectday;
			switch (msg.what) {
			// ----
			case UPDATEABLUM:
				// File ablumFile = (File) msg.obj;
				// addAblumView(ablumFile);
				reShowSelectPhoto();
				break;
			case SETRESULT:
				int requestCode = msg.arg1;
				int resultCode = msg.arg2;
				Intent data = (Intent) msg.obj;
				mAblumUtils.setResult(requestCode, resultCode, data);
				break;
			case UPLOADABLUM:
				File tempFile = (File) msg.obj;
				// uploadAblumPhone(tempFile);
				break;
			case PUBLISHCAMPAIGN:
				// commit();
				break;
			case 0x111:
				int ii = msg.arg1;
				// 设置进度条当前的完成进度
				mProgressBar.setProgress(ii);
				if (ii >= 100) {
					selectDialog.dismiss();
				}
			}
		}
	};
	public static final int DEL = 32;
	public static final int LOOK = 33;
	public static final int ADDPHOTO = 34;
	Handler mphotoHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DEL:
				mAddAblumFileList.remove(msg.arg1);
				Bimp.tempSelectBitmap.remove(msg.arg1);
				reShowSelectPhoto();
				break;
			case LOOK:
				int position = (Integer) msg.obj;
				if (Bimp.tempSelectBitmap.size() > 1) {
					if (!mAblumUtils.selectVedioStatus) {
						Intent intent = new Intent();
						intent.putExtra("DelStatus", "Del");
						intent.putExtra("ID", position);
						intent.setClass(context, GalleryActivity.class);
						startActivity(intent);
					} else {
						// 查看视频
						mPlayerUtils.playNativeVideo(context, Bimp.tempSelectBitmap.get(position).getVedioPath());
					}
				}
				break;
			case ADDPHOTO:
				if (mAddAblumFileList != null) {
					if (!mAblumUtils.selectVedioStatus) {
						mAblumUtils.onAddAblumClick();
					} else {
						CommonUtils.getInstance().initToast(context, "不能同时上传图片和视频两种格式");
					}
				}
				break;
			}
		}
	};
	private String clubname_Buffer = "";

	private File select_vedio_file;
	private File imagePath;

	/**
	 * 上传精彩时刻 uid 用户ID string 是 act_title 标题 string 否 act_content 内容 string 否
	 * club_ids 俱乐部ID string 是 多个俱乐部用逗号（,）分隔 示例：1,2,3 bid 企业ID string 是 type
	 * 精彩时刻类型 string 是 text（文本）、image（图片）、video（视频） images 图片 string 否 json
	 * 示例：[{"img_name":"图片名称","img_type":"图片类型","data":"图片二进制流（需要base64）"},{"img_name":"1.jpg","img_type":"jpg","data":"123123123123"}]
	 * video_url 视频URL string 是 当上传类型为video时为必填项
	 * 
	 */
	public void addWonderful() {
		String ss = "";

		try {
			showDilag();
			if (!mAblumUtils.selectVedioStatus) {
				JSONArray array = new JSONArray();
				Log.e("mAddAblumFileList", mAddAblumFileList.size() + "");
				if (mAddAblumFileList != null && mAddAblumFileList.size() > 0) {
					for (int i = 0; i < mAddAblumFileList.size(); i++) {
						String name = mAddAblumFileList.get(i).getName();
						String getPath = mAddAblumFileList.get(i).getPath();

						byte[] b = CommonUtils.getInstance().File2Bytes(mAddAblumFileList.get(i));
						String img_type = CommonUtils.getInstance().getExtensionName(name);
						JSONObject stoneObject = new JSONObject();
						stoneObject.put("img_name", name);
						stoneObject.put("img_type", img_type);
						stoneObject.put("data", Base64.encode(b));
						array.put(stoneObject);
					}
				}
				ss = array.toString();
			} else if (mAblumUtils.selectVedioStatus) {
				JSONArray array = new JSONArray();
				if (imagePath != null) {
					String name = imagePath.getName();
					String getPath = imagePath.getPath();
					byte[] b = CommonUtils.getInstance().File2Bytes(imagePath);
					String img_type = CommonUtils.getInstance().getExtensionName(imagePath.getName());
					JSONObject stoneObject = new JSONObject();
					stoneObject.put("img_name", name);
					stoneObject.put("img_type", img_type);
					stoneObject.put("data", Base64.encode(b));
					array.put(stoneObject);
				}
				ss = array.toString();
				Log.e("mAddAblumFileList", ss + "");
			}
		} catch (Exception e) {
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", "" + uid);
		mhashmap.put("act_title", act_title);
		mhashmap.put("act_content", act_content);
		mhashmap.put("club_ids", clubid_Buffer);
		mhashmap.put("bid", bestDoInfoSharedPrefs.getString("corp_id", ""));
		mhashmap.put("type", type);
		mhashmap.put("video_url", vedio_name);
		mhashmap.put("images", ss);
		Log.e("mhashmap", "" + mhashmap.toString());
		new AddWonderfulBusiness(context, mhashmap, new AddWonderfulCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				// TODO Auto-generated method stub

				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						intentToShowUrl(dataMap);
					} else {
						CommonUtils.getInstance().initToast(context, (String) dataMap.get("msg"));
					}
				} else {
					CommonUtils.getInstance().initToast(context, getResources().getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}

			public void intentToShowUrl(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().initToast(context, "添加成功");
				String show_url = (String) dataMap.get("wondful_show_url");
				CommonUtils.getInstance().toH5(context,show_url, "精彩时刻", "",true);
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Constans.getInstance().searchForResultBySearchKWPage) {
			clubid_Buffer = data.getStringExtra("clubid_Buffer");
			clubname_Buffer = data.getStringExtra("clubname_Buffer");
			tv_name_select.setText(clubname_Buffer);
		} else if (requestCode == mAblumUtils.RESULTVEDIO && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			String v_path = FilePathResolver.getPath(this, uri);
			System.out.println("文件路径== " + v_path);
			select_vedio_file = new File(v_path);
			long size = select_vedio_file.length();
			System.out.println("视频大小== " + size);
			if (size <= 1024 * 1024 * 50) {
				// <=50M
				String v_size = select_vedio_file.getName(); // 图片名称
				System.err.println("v_size==" + v_size);
				if (!TextUtils.isEmpty(v_size) && v_size.contains(".")) {
					int lastDot = v_size.lastIndexOf(".");
					String typeString = v_size.substring(lastDot + 1).toUpperCase();
					mAblumUtils.selectVedioStatus = true;
					imagePath = mAblumUtils.saveBitmapFile(mPlayerUtils.getVideoThumbnail(v_path));
					String vedioduration = mPlayerUtils.getDuration(v_path);
					if (!typeString.equals("MP4")) {
						mPlayerUtils.convertMp4(this, v_path, mFileCache.cacheDir.getPath() + "/selectvedio.mp4");
						v_path = mFileCache.cacheDir.getPath() + "/selectvedio.mp4";

					}
					if (imagePath != null) {
						System.err.println("视频时长==" + vedioduration);
						System.err.println("视频缩略图路径==" + imagePath.getPath());
						ArrayList<ImageItem> selectedDataList = new ArrayList<ImageItem>();
						ImageItem mImageItem = new ImageItem();
						mImageItem.setImagePath(imagePath.getPath());
						mImageItem.setVedioduration(vedioduration);
						mImageItem.setVediosize(size);
						mImageItem.setVedioPath(v_path);
						selectedDataList.add(mImageItem);
						mAblumUtils.addMoreEditBimp(this, selectedDataList);
						reShowSelectPhoto();
					}
				}
			} else if (size == 0) {
				CommonUtils.getInstance().initToast(context, "文件已损坏无法上传");
			} else {
				CommonUtils.getInstance().initToast(context, "请上传50M以内的视频哦");
			}
		} else if (requestCode == mAblumUtils.RESULTCAMERA || requestCode == mAblumUtils.RESULTGALLERY
				|| requestCode == mAblumUtils.RESULTCROP) {
			Message msgMessage = new Message();
			msgMessage.arg1 = requestCode;
			msgMessage.arg2 = resultCode;
			msgMessage.obj = data;
			msgMessage.what = SETRESULT;
			mHandler.sendMessage(msgMessage);
			msgMessage = null;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 自定义退出应用提示
	 * 
	 * @param content
	 * @param mHandler
	 * @param activitypath
	 *            exit退出,cancel注销
	 */
	private HorizontalProgressBarWithNumber mProgressBar;
	private MyDialog selectDialog;

	public void defineBackPressed(final Activity content) {
		selectDialog = new MyDialog(content, R.style.dialog, R.layout.dialog_put_pengyouquan);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView text_off = (TextView) selectDialog.findViewById(R.id.put_pengyouquan_text_off);// 取消

		mProgressBar = (HorizontalProgressBarWithNumber) selectDialog.findViewById(R.id.id_progressbar01);
		mProgressBar.setMax(100);
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancell();
				selectDialog.dismiss();
			}
		});
		selectDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
					cancell();
				}
				return false;
			}
		});
	}

	private void doBack() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Bimp.tempSelectBitmap.clear();
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (selectDialog != null && selectDialog.isShowing()) {
			cancell();
		}
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}
}
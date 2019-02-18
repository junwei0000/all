package com.longcheng.lifecareplan.zxing;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.zxing.camera.CameraManager;
import com.longcheng.lifecareplan.zxing.decoding.CaptureActivityHandler;
import com.longcheng.lifecareplan.zxing.decoding.InactivityTimer;
import com.longcheng.lifecareplan.zxing.decoding.RGBLuminanceSource;
import com.longcheng.lifecareplan.zxing.decoding.Utils;
import com.longcheng.lifecareplan.zxing.view.ViewfinderView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Vector;

import butterknife.OnClick;

/**
 * 作者：MarkShuai
 * 时间：2017/11/27 16:04
 * 邮箱：MarkShuai@163.com
 * 意图： 扫描二维码的类
 */
public class CaptureActivity extends BaseActivity implements Callback {

    private static final int REQUEST_CODE = 234;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private String photo_path;
    private Bitmap scanBitmap;
    boolean flag = true;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 初始化 CameraManager
        CameraManager.init(getApplication());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.scanner_main;
    }

    @Override
    public void initView(View view) {
        initView();
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.iv_scanner_back)
    protected void ivBackClick() {
        finish();
    }

    @OnClick(R.id.iv_scanner_photo)
    protected void photoClick() {
        photo();
    }

    @OnClick(R.id.iv_scanner_light)
    protected void lightClick() {
        // 闪光灯
        light();
    }

    private void initView() {
        viewfinderView = (ViewfinderView) findViewById(R.id.vfv_scanner_viewfinder_view);
    }

    /**
     * @param
     * @name 闪光灯控制方法
     * @time 2017/11/27 16:52
     * @author MarkShuai
     */
    protected void light() {
        if (flag == true) {
            flag = false;
            // 开闪光灯
//            CameraManager.get().openLight();
        } else {
            flag = true;
            // 关闪光灯
//            CameraManager.get().offLight();
        }

    }

    /**
     * @param
     * @name 选择图片的跳转
     * @time 2017/11/27 16:50
     * @author MarkShuai
     */
    private void photo() {
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        // innerIntent.setAction(Intent.ACTION_GET_CONTENT);

        innerIntent.setType("image/*");

        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");

        CaptureActivity.this
                .startActivityForResult(wrapperIntent, REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:

                    String[] proj = {MediaStore.Images.Media.DATA};
                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(data.getData(), proj, null, null, null);

                    if (cursor.moveToFirst()) {

                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(column_index);
                        if (photo_path == null) {
                            photo_path = Utils.getPath(getApplicationContext(), data.getData());
                            LogUtils.i("123path  Utils", photo_path);
                        }
                        LogUtils.i("123path", photo_path);
                    }
                    cursor.close();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Result result = scanningImage(photo_path);
                            // String result = decode(photo_path);
                            if (result == null) {
                                LogUtils.i("123", "   -----------");
                                Looper.prepare();
                                ToastUtils.showToast("图片格式有误");
                                Looper.loop();
                            } else {
                                LogUtils.i("123result", result.toString());
                                // LogUtils.i("123result", result.getText());
                                // 数据返回
                                String recode = recode(result.toString());
                                Intent data = new Intent();
                                data.putExtra("result", recode);
                                setResult(300, data);
                                finish();
                            }
                        }
                    }).start();
                    break;

            }

        }

    }

    /**
     * @param
     * @name 解析部分图片
     * @time 2017/11/27 16:53
     * @author MarkShuai
     */
    protected Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        // DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小

        int sampleSize = (int) (options.outHeight / (float) 200);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);

        // --------------测试的解析方法---PlanarYUVLuminanceSource-这几行代码对project没作功----------
//
//        LuminanceSource source1 = new PlanarYUVLuminanceSource(
//                rgb2YUV(scanBitmap), scanBitmap.getWidth(),
//                scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(),
//                scanBitmap.getHeight(), false);
//        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
//                source1));
//        MultiFormatReader reader1 = new MultiFormatReader();
//        Result result1;
//        try {
//            result1 = reader1.decode(binaryBitmap);
//            String content = result1.getText();
//            LogUtils.i("123content", content);
//        } catch (NotFoundException e1) {
//            e1.printStackTrace();
//        }

        // ----------------------------

        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {

            return reader.decode(bitmap1, hints);

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.sfv_scanner_preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * @param
     * @name 初始化相机
     * @time 2017/11/27 16:54
     * @author MarkShuai
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
//            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(final Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String recode = recode(result.toString());
        ToastUtils.showToast(recode);
        // 数据返回
        Intent data = new Intent();
        data.putExtra("result", recode);
        setResult(300, data);
        finish();
    }

    /**
     * @param
     * @name 扫描完后声音和震动
     * @time 2017/11/27 16:54
     * @author MarkShuai
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.mo_scanner_beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    /**
     * @param
     * @name 播放声音和震动
     * @time 2017/11/27 16:55
     * @author MarkShuai
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * 中文乱码
     * <p>
     * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
     *
     * @return
     */
    private String recode(String str) {
        String formart = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder().canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
                LogUtils.i("1234      ISO8859-1", formart);
            } else {
                formart = str;
                LogUtils.i("1234      stringExtra", str);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return formart;
    }

    /**
     * //TODO: TAOTAO 将bitmap由RGB转换为YUV //TOOD: 研究中
     *
     * @param bitmap 转换的图形
     * @return YUV数据
     */
    public byte[] rgb2YUV(Bitmap bitmap) {
        // 该方法来自QQ空间
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int len = width * height;
        byte[] yuv = new byte[len * 3 / 2];
        int y, u, v;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = pixels[i * width + j] & 0x00FFFFFF;

                int r = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb >> 16) & 0xFF;

                y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
                u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
                v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

                y = y < 16 ? 16 : (y > 255 ? 255 : y);
                u = u < 0 ? 0 : (u > 255 ? 255 : u);
                v = v < 0 ? 0 : (v > 255 ? 255 : v);

                yuv[i * width + j] = (byte) y;
                // yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
                // yuv[len + (i >> 1) * width + (j & ~1) + 1] = (byte) v;
            }
        }
        return yuv;
    }


}
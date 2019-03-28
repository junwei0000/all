package com.longcheng.lifecareplan.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.longcheng.lifecareplan.R;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * 作者：jun on
 * 时间：2018/4/24 12:48
 * 意图：
 */

public class ConfigUtils {
    private static volatile ConfigUtils INSTANCE;


    public static ConfigUtils getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (ConfigUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigUtils();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 生成32位的随机数作为id
     *
     * @param digCount
     * @return
     */
    public String getRandomNumber(int digCount) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++)
            sb.append((char) ('0' + rnd.nextInt(10)));
        return sb.toString();
    }

    /**
     * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     * <p>
     * 渠道标志为： 1，andriod（a）
     * <p>
     * 识别符来源标志： 1， wifi mac地址（wifi）； 2， IMEI（imei）； 3， 序列号（sn）； 4，
     * id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * @return
     */
    public String getDeviceId(Context context) {
        String deviceId = "";
        try {
            // IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            String androidId = getAndroidId(context);
            // 序列号（sn）
            String sn = tm.getSimSerialNumber();
            long leastSigBits = 0;
            if (!TextUtils.isEmpty(imei)) {
                if (TextUtils.isEmpty(sn)) {
                    leastSigBits = ((long) imei.hashCode() << 32);
                } else {
                    leastSigBits = ((long) imei.hashCode() << 32) | sn.hashCode();
                }
            }
            UUID deviceUuid = new UUID(androidId.hashCode(), leastSigBits);
            deviceId = MD5(deviceUuid.toString());
            Log.e("getDeviceId : ", "imei:" + imei + "  ;androidId=" + androidId + " ;simSerialNumber= " + sn + "\n" + deviceId.toString());
        } catch (Exception e) {
            e.printStackTrace();
            deviceId = getAndroidId(context);
            Log.e("getDeviceId : ", deviceId);
            deviceId = MD5(deviceId);
        }

        return deviceId;
    }

    private String getUUID(Context context) {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    @SuppressLint("HardwareIds")
    private String getAndroidId(Context context) {

        return android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

    }

    /**
     * 功能说明：获取版本号
     */
    public String getVerCode(Context context) {
        String verName = null;
        try {
            verName = context.getPackageManager().getPackageInfo("com.longcheng.lifecareplan", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取测试 sha1
     *
     * @param context
     * @return
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备最小宽度代码，
     *
     * @param context
     * @return
     */
    public float getWindowPD(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int heightPixels = dm.heightPixels;
        int widthPixels = dm.widthPixels;
        float density = dm.density;
        float heightDP = heightPixels / density;
        float widthDP = widthPixels / density;
        float smallestWidthDP;
        if (widthDP < heightDP) {
            smallestWidthDP = widthDP;
        } else {
            smallestWidthDP = heightDP;
        }
        Log.e("getWindowPD", "smallestWidthDP=" + smallestWidthDP);
        return smallestWidthDP;
    }

    /**
     * 跟php对接的md5加密
     *
     * @param s
     * @return
     */
    public String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置随机数
     *
     * @param max
     * @return
     */
    public int setRandom(int max) {
        int min = 0;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 圆角头像
     *
     * @param mContext
     * @param bitmap
     * @return
     */
    public Bitmap toRoundCorner(Context mContext, Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        final float roundPx = 100;// 角度

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }

    /**
     * 关闭键盘
     *
     * @param context
     */
    public void closeSoftInput(Activity context) {
        if (context != null)
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * EditText防止输入空格、换行、限制输入字符长度
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText, int max) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || "\n".equals(source)
                        || "/".equals(source)
                        || "'".equals(source)
                        || "&".equals(source)
                        || ">".equals(source)
                        || "\"".equals(source)
                        || "<".equals(source)) {
                    //空格和换行都转换为""
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(max)});
    }

    /**
     * 设置选中布局高亮
     *
     * @param mTextView
     */
    public static void setSelectFouseBtn(View mTextView) {
        mTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mTextView.setBackgroundResource(R.drawable.corners_bg_btnselect);
                } else {
                    mTextView.setBackgroundResource(R.drawable.corners_bg_btn);
                }
            }
        });
    }

    /**
     * 设置选中布局高亮
     *
     * @param mTextView
     */
    public static void setSelectFouseText(View mTextView) {
        mTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.e("OnFocusChange", "OnFocusChange=" + hasFocus);
                    mTextView.setBackgroundResource(R.drawable.corners_bg_textselect);
                } else {
                    mTextView.setBackgroundResource(R.drawable.corners_bg_text);
                }
            }
        });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width:100%; width:100%; height:auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 显示showBridgeWebView cont
     */
//    public void showBridgeWebView(BridgeWebView mBridgeWebView, String H5String) {
//        mBridgeWebView.setFocusable(false);
//        mBridgeWebView.loadData(H5String, "text/html; charset=UTF-8", null);//这种写法可以正确解码
//        mBridgeWebView.loadDataWithBaseURL(null, getHtmlData(H5String),
//                "text/html", "utf-8", null);
//    }
}

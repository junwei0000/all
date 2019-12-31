package com.longcheng.lifecareplan.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 设置页面跳转效果 实现两个 Activity 切换时的动画。 在Activity中使用 有两个参数：进入动画和出去的动画。 注意 1、必须在
     * StartActivity() 或 finish() 之后立即调用。 2、而且在 2.1 以上版本有效 3、手机设置-显示-动画，要开启状态
     * 第二个参数为当前页面的效果
     */
    public void setPageIntentAnim(Intent startIntent, Activity mActivity) {
        // 设置切换动画，从右边进入，左边退出
        mActivity.overridePendingTransition(R.anim.page_intent_from_right, R.anim.page_now);
        startIntent = null;
    }

    /**
     * 设置页面返回效果
     *
     * @param mActivity
     */
    public void setPageBackAnim(Activity mActivity) {
        // 设置切换动画，从右边进入，左边退出
        mActivity.overridePendingTransition(R.anim.page_now, R.anim.page_back_to_left);
    }

    /**
     * 登录从下向上开启动画
     *
     * @param startIntent
     * @param mActivity
     */
    public void setPageLoginIntentAnim(Intent startIntent, Activity mActivity) {
        mActivity.overridePendingTransition(R.anim.push_bottom_in, R.anim.page_now);
        startIntent = null;
    }

    /**
     * 登录back动画
     *
     * @param mActivity
     */
    public void setPageLoginBackAnim(Activity mActivity) {
        mActivity.overridePendingTransition(R.anim.page_now, R.anim.push_bottom_out);
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
            long leastSigBits;
            if (TextUtils.isEmpty(sn)) {
                leastSigBits = ((long) imei.hashCode() << 32);
            } else {
                leastSigBits = ((long) imei.hashCode() << 32) | sn.hashCode();
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

    /**
     * Android动态获取当前手机IP地址
     *
     * @param context
     * @return
     */
    public String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    private String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
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
     * 功能说明：获取版本号'1.8.0'
     */
    public String getVersionName(Context context) {
        String verName = "";
        if (context == null) {
            return verName;
        }
        try {
            verName = context.getPackageManager().getPackageInfo("com.longcheng.lifecareplan", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 功能说明：获取版本号180
     */
    public int getVersionCode(Context context) {
        int vercode = 0;
        try {
            vercode = context.getPackageManager().getPackageInfo("com.longcheng.lifecareplan", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return vercode;
    }

    /**
     * 获取测试 sha1
     *
     * @param context
     * @return
     */
    public String sHA1(Context context) {
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
        if (context != null && context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null)
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 防止弹层键盘不隐藏
     *
     * @param et_content
     */
    public void closeSoftInput(EditText et_content) {
        if (et_content != null) {
            final InputMethodManager imm = (InputMethodManager) et_content.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
        }
    }


    /**
     * EditText防止输入空格、换行、限制输入字符长度
     *
     * @param editText
     */
    public void setEditTextInhibitInputSpace(EditText editText, int max) {
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

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width:100%; width:100%; height:auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 解决HTML5手机端字体小的问题。关键下下面的前2行内容
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlAdpterMober(String bodyHTML) {
        String bef =
                " <!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\" \"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">\n" +
                        "\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "\n" +
                        "<head>\n" +
                        "\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>";
        return bef + bodyHTML + " </body></html>";
    }

    /**
     * 显示showBridgeWebView cont
     */
    public void showBridgeWebView(BridgeWebView mBridgeWebView, String H5String) {
        if (mBridgeWebView != null) {
            mBridgeWebView.setFocusable(false);
//        mBridgeWebView.loadData(H5String, "text/html; charset=UTF-8", null);//这种写法可以正确解码
            mBridgeWebView.loadDataWithBaseURL(null, getHtmlData(H5String),
                    "text/html", "utf-8", null);
        }
    }

    /**
     * 显示showBridgeWebView cont
     */
    public void showBridgeWebViewColor(BridgeWebView mBridgeWebView, String H5String) {
        if (mBridgeWebView != null) {
            mBridgeWebView.setFocusable(false);
            String cont = "<font color= '#ecd890'>" + H5String + "</font>";
            mBridgeWebView.loadDataWithBaseURL(null, getHtmlAdpterMober(cont),
                    "text/html", "utf-8", null);
        }
    }

    /**
     * 初始化webview背景透明
     *
     * @param mBridgeWebView
     * @param context
     */
    public void setInitWebView(BridgeWebView mBridgeWebView, Context context) {
        int fontSize = (int) context.getResources().getDimension(R.dimen.text_zhu_size);
        if (mBridgeWebView != null) {
//            mBridgeWebView.getSettings().setDefaultFontSize(fontSize);
//            mBridgeWebView.getSettings().setTextSize(WebSettings.TextSize.LARGER);
//            mBridgeWebView.getSettings().setTextZoom(130);
            mBridgeWebView.setBackgroundColor(0); // 设置背景色
            if (mBridgeWebView.getBackground() != null) {
                mBridgeWebView.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
            }
        }
    }

    public void setHtmlText(TextView text, String cont) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(cont, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(cont);
        }
        text.setText(result);
    }

    /**
     * 将系统表情转化为字符串
     *
     * @param s
     * @return
     */
    public int getEmojiNum(String s) {
        int length = s.length();
        int num = 0;
        //循环遍历字符串，将字符串拆分为一个一个字符
        for (int i = 0; i < length; i++) {
            char codePoint = s.charAt(i);
            //判断字符是否是emoji表情的字符
            if (isEmojiCharacter(codePoint)) {
                //如果是将以大括号括起来
                String emoji = "{" + Integer.toHexString(codePoint) + "}";
                Log.e("isEmojiCharacter", "emoji=" + emoji);
                num++;
                continue;
            }
        }
        return num;
    }

    /**
     * 将系统表情转化为字符串
     *
     * @param s
     * @return
     */
    public String getString(String s) {
        int length = s.length();
        String context = "";
        //循环遍历字符串，将字符串拆分为一个一个字符
        for (int i = 0; i < length; i++) {
            char codePoint = s.charAt(i);
            //判断字符是否是emoji表情的字符
            if (isEmojiCharacter(codePoint)) {
                //如果是将以大括号括起来
                String emoji = "{" + Integer.toHexString(codePoint) + "}";
                Log.e("isEmojiCharacter", "emoji=" + emoji);
                context = context + emoji;
                continue;
            }
            context = context + codePoint;
        }
        if (TextUtils.isEmpty(context)) {
            context = s;
        }
        return context;
    }

    /**
     * 是否包含表情
     *
     * @param codePoint
     * @return 如果不包含 返回false,包含 则返回true
     */

    private boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 将表情描述转换成表情
     *
     * @param str
     * @return
     */
    public String getEmoji(Context context, String str) {
        String string = str;
        String rep = "\\{(.*?)\\}";
        Pattern p = Pattern.compile(rep);
        Matcher m = p.matcher(string);
        while (m.find()) {
            String s1 = m.group().toString();
            String s2 = s1.substring(1, s1.length() - 1);
            String s3;
            try {
                s3 = String.valueOf((char) Integer.parseInt(s2, 16));
                string = string.replace(s1, s3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(string)) {
            string = str;
        }
        return string;
    }

    /**
     * 字符串转换unicode
     */
    public String stringToUnicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    /**
     * 字符串换成UTF-8
     *
     * @param str
     * @return
     */
    public String stringToUtf8(String str) {
        String result = null;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * utf-8换成字符串
     *
     * @param str
     * @return
     */
    public String utf8ToString(String str) {
        String result = null;
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}

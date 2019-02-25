package com.longcheng.lifecareplan.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.ConfigUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 那是为什么，会导致oom呢：
 * <p>
 * 原来当使用像 imageView.setBackgroundResource，imageView.setImageResource,或者
 * BitmapFactory.decodeResource 这样的方法来设置一张大图片的时候，
 * <p>
 * 这些函数在完成decode后，最终都是通过java层的createBitmap来完成的，需要消耗更多内存。
 * <p>
 * 因此，改用先通过BitmapFactory.decodeStream方法，创建出一个bitmap，再将其设为ImageView的
 * source，decodeStream最大的秘密在于其直接调用JNI
 * >>nativeDecodeAsset()来完成decode，无需再使用java层的createBitmap
 * ，从而节省了java层的空间。如果在读取时加上图片的Config参数，可以跟有效减少加载的内存，从而跟有效阻止抛out of Memory异常。
 * <p>
 * 另外，需要特别注意：
 * <p>
 * decodeStream是直接读取图片资料的字节码了，
 * 不会根据机器的各种分辨率来自动适应，使用了decodeStream之后，需要在hdpi和mdpi，ldpi中配置相应的图片资源
 * ，否则在不同分辨率机器上都是同样大小（像素点数量），显示出来的大小就不对了。
 *
 * @author jun
 */
public class ImageLoader {
    FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    // 线程池
    ExecutorService executorService;
    public String imgStatus = "";
    // 当进入listview时默认的图片，可换成你自己的默认图片
    public int stub_id = R.mipmap.moren_new;
    Context context;
    public Bitmap bitmap_orve;

    private int showWidth = 0;
    private int showHeight = 0;

    public int getShowWidth() {
        return showWidth;
    }

    public void setShowWidth(int showWidth) {
        this.showWidth = showWidth;
    }

    public int getShowHeight() {
        return showHeight;
    }

    public void setShowHeight(int showHeight) {
        this.showHeight = showHeight;
    }

    public ImageLoader(Context context, String imgStatus) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
        this.context = context;
        this.imgStatus = imgStatus;
        initLoadImage();
    }

    private void initLoadImage() {
        if (imgStatus.equals("listitem")) {
            stub_id = R.mipmap.moren_new;
        } else if (imgStatus.equals("headImg")) {
            stub_id = R.mipmap.user_default_icon;
        }
        bitmap_orve = readBitMap(context, stub_id);
    }

    // 最主要的方法
    public void DisplayImage(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageBitmap(bitmap_orve);
            return;
        }
        imageViews.put(imageView, url);
        fileCache.setShowWidth(showWidth);
        /**
         * 1先从内存缓存中查找
         */
        File f = fileCache.getFile(url);
        Bitmap bitmap = null;
        if (f.exists()) {
            bitmap = fileCache.decodeFile(f);
        }

        if (bitmap != null) {
            if (imgStatus.equals("listdetail")) {
                imageView.setScaleType(ScaleType.FIT_XY);
            }
            setRoundCorner(imageView, bitmap);
        } else {
            setRoundCorner(imageView, bitmap_orve);
            // 若没有的话则开启新线程加载图片
            queuePhoto(url, imageView);
        }
        f = null;
    }

    private void setRoundCorner(ImageView imageView, Bitmap bitmap) {
        if (imgStatus.equals("headImg")) {
            Bitmap Roundbitmap = ConfigUtils.getINSTANCE().toRoundCorner(context, bitmap);
            imageView.setImageBitmap(Roundbitmap);
        } else {
            imageView.setImageBitmap(bitmap);
        }
        bitmap = null;
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

    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getUrlBitmap(String url) {
        // 从指定的url中下载图片
        try {
            /**
             * 2保存本地缓存文件
             */
            File f = fileCache.getFile(url);
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            CopyStream(is, os);
            os.close();
            bitmap = fileCache.decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            Bitmap bmp = getUrlBitmap(photoToLoad.url);
            if (imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            // 更新的操作放在UI线程中
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
            bd = null;
        }
    }

    /**
     * 防止图片错位
     *
     * @param photoToLoad
     * @return
     */
    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    // 用于在UI线程中更新界面
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null) {
                setRoundCorner(photoToLoad.imageView, bitmap);
                if (imgStatus.equals("listdetail")) {
                    photoToLoad.imageView.setScaleType(ScaleType.CENTER_CROP);
                }
            } else {
                setRoundCorner(photoToLoad.imageView, bitmap_orve);
            }
        }
    }

    public void clearCache() {
        imageViews.clear();
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
            bytes = null;
        } catch (Exception ex) {
        }
    }

}

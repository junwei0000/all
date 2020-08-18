package com.longcheng.lifecareplan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 作者：MarkShuai on
 * 时间：2017/11/21 16:15
 * 邮箱：mark_mingshuai@163.com
 * 意图：日志打印工具类
 */

public class FileCache {

    private File cacheDir;
    // 图片SDCard缓存路径
    public static String path = Environment.getExternalStorageDirectory()
            .toString() + "/longcheng/";
    private int showWidth;

    public int getShowWidth() {
        return showWidth;
    }

    public void setShowWidth(int showWidth) {
        this.showWidth = showWidth;
    }

    /**
     * @params
     * @name 获取异常缓存的内存卡地址
     * @time 2017/11/21 15:23
     * @author MarkShuai
     * <p>
     * 如果有SD卡则在SD卡中建一个LazyList的目录存放缓存的图片
     * 没有SD卡就放在系统的缓存目录中
     * </>
     */
    public FileCache(Context context) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(path);
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url) {
        // 将url的hashCode作为缓存的文件名
        String filename = ConfigUtils.getINSTANCE().MD5(url);
        // String filename = String.valueOf(url.hashCode());
        // String filename = url.substring(url.lastIndexOf("/") + 1)+".txt";
        File f = new File(cacheDir, filename);
        return f;

    }

    //获取缓存目录
    public File getCacheDir() {
        return cacheDir;
    }

    /**
     * decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
     *
     * @param f
     * @return
     */
    public Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inPreferredConfig = Bitmap.Config.RGB_565;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            int REQUIRED_SIZE = 210;
            if (showWidth != 0) {
                REQUIRED_SIZE = showWidth;
            } else {
                REQUIRED_SIZE = 210;
                int width_tmp = o.outWidth, height_tmp = o.outHeight;
                while (true) {
                    if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                        break;
                    }
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }
            }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            o2.inPurgeable = true;
            o2.inInputShareable = true;
            o2.inJustDecodeBounds = false;
            Bitmap mBitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            // if (mBitmap != null && showWidth != 0) {
            // mBitmap = zoomImg(mBitmap, showWidth);
            // }
            return mBitmap;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 处理图片
     *
     * @param bm 所要转换的bitmap
     * @return 指定宽高的bitmap
     */
    public Bitmap zoomImg(Bitmap bm, int newWidth) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            deleteFile(f);
    }

    //使用递归进行文件的删除
    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }

    public Bitmap getFitSampleBitmap(InputStream inputStream) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        byte[] bytes = readStream(inputStream);
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 从inputStream中获取字节流 数组大小
     **/
    public byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * @params
     * @name 获取sd卡路径文件
     * @time 2017/11/21 15:24
     * @author MarkShuai
     */
    public static File getFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    /**
     * @params
     * @name 保存BItMap
     * @time 2017/11/21 15:24
     * @author MarkShuai
     */
    public static void saveMyBitmap(String bitName, Bitmap mBitmap) throws Exception {

        if (CommonUtilImage.hasSDCard()) {

            File f = getFilePath(path, bitName);

            FileOutputStream fOut = null;

            fOut = new FileOutputStream(f);

            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            fOut.flush();

            fOut.close();

        }
    }

    /**
     * Bitmap对象保存味图片文件
     *
     * @param bitmap
     */
    public static File saveBitmapFile(Bitmap bitmap) {
        try {
            String file_name = ConfigUtils.getINSTANCE().getRandomNumber(16) + ".jpg";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File tempFile = new File(path + file_name);
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(tempFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

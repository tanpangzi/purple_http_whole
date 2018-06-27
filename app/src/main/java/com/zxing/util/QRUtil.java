package com.zxing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zimi.zimixing.BaseApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

/**
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class QRUtil {

    private int QR_WIDTH = 300;
    private int QR_HEIGHT = 300;

    /**
     * 创建二维码图片
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-9,下午12:01:26
     * <br> UpdateTime: 2016-4-9,下午12:01:26
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param url
     */
    public Bitmap createQRImage(String url) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            // BitMatrix bitMatrix = new MultiFormatWriter().encrypt(new
            // String(url.getBytes("GBK"),"ISO-8859-1"),BarcodeFormat.QR_CODE,
            // 300, 300);
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            new QRUtil().saveSD(bitmap);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存生成的二维码到本地
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-1-14,上午11:28:46
     * <br> UpdateTime: 2016-1-14,上午11:28:46
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param bitmap
     */
    private void saveSD(Bitmap bitmap) {
        BufferedOutputStream outStream = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 将图片转换成一个byte[]
            byte[] bitmaps = getbitmaptobytes(bitmap);
            // 将Byte[]转换成long类型
            long longbitmaps = bytes2long(bitmaps);
            // 判断SD卡是否有足够的空间供下载使用
            boolean iscapacity = isEnaleforDownload(longbitmaps);
            if (iscapacity) {
                try {
                    File sdCardDir = Environment.getExternalStorageDirectory();
                    // 防止出现重复名字
                    String fileName = System.currentTimeMillis() + ".png";
                    File dir;
                    dir = new File(sdCardDir.getCanonicalPath() + "/Diver/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File cacheFile = new File(dir, fileName);
                    FileOutputStream fstream = new FileOutputStream(cacheFile);
                    outStream = new BufferedOutputStream(fstream);
                    outStream.write(bitmaps);
                    Context context = BaseApplication.getInstance().getApplicationContext();
                    Toast.makeText(context, "图片成功存至myscan目录下", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    if (outStream != null) {
                        try {
                            outStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        } else {
            Context context = BaseApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "SDCard存储空间不足", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断SD卡是否有足够的空间供下载使用
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-9,下午12:00:57
     * <br> UpdateTime: 2016-4-9,下午12:00:57
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param longbitmaps
     *
     * @return false空间不足
     */
    @SuppressWarnings("deprecation")
    private boolean isEnaleforDownload(long longbitmaps) {
        /*
         * Statfs : 获取系统文件的类
		 * 
		 * @.getAbsolutePath()给一个抽象路径名的绝对路径字符串
		 */
        StatFs statfs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        // 获得你的手机共有几个存储，即获得块的总量
        // int blockCount = statfs.getBlockCount();
        // 该手机里可用的块的数量，即可用的存储。也可以说是剩余内存容量
        int availableBlocks = statfs.getAvailableBlocks();
        // 获得每一个块的大小， 返回值用long接受，int可能达到上限
        long blockSize = statfs.getBlockSize();
        // 获得可用的存储空间
        long asavespace = availableBlocks * blockSize;

        if (asavespace > longbitmaps) {
            return true;
        }
        return false;
    }

    /**
     * 将Byte[]转换成long类型
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-9,下午12:08:56
     * <br> UpdateTime: 2016-4-9,下午12:08:56
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param bitmaps
     */
    private long bytes2long(byte[] bitmaps) {
        int num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (bitmaps[ix] & 0xff);
        }
        return num;
    }

    /**
     * 将图片转换成Byte[]
     * <p>
     * <br> Version: 1.0.0
     * <br> CreateTime: 2016-4-9,下午12:09:09
     * <br> UpdateTime: 2016-4-9,下午12:09:09
     * <br> CreateAuthor: 叶青
     * <br> UpdateAuthor: 叶青
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param bitmap
     */
    private byte[] getbitmaptobytes(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }

}
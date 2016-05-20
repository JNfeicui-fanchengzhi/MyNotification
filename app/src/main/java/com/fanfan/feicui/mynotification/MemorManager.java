package com.fanfan.feicui.mynotification;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by Administrator on 2016/5/15.
 */
public class MemorManager {
    /**
     * 获取手机内置sdcard路径, 为null表示无
     */
    public static String getPhoneInSDCardPath() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath();
    }
    /**
     * 设备自身存储全部大小 单位B
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + cacheFileSize;
    }
    /**
     * 设备自身存储空闲大小 单位B
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfFreeSize() {

        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getAvailableBlocksLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getAvailableBlocksLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + cacheFileSize;
    }
    /**
     * 获取到的是手机自带的储存空间 单位是byte
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSDCardSize() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        return blockCount * blockSize;
    }
    /**
     * 获取到的是手机自带的储存空间的剩余空间  单位byte
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSDCardFreeSize() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availaBlock = stat.getAvailableBlocksLong();
        return availaBlock * blockSize;
    }
    /**
     * 获取手机总存储大小
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneAllSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long dataFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + dataFileSize + cacheFileSize;
    }
    /**
     * 获取手机总闲置存储大小
     */
    public static long getPhoneAllFreeSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long blockCount = stat.getAvailableBlocks();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        blockCount = stat.getAvailableBlocks();
        long dataFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        blockCount = stat.getAvailableBlocks();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + dataFileSize + cacheFileSize;
    }

}

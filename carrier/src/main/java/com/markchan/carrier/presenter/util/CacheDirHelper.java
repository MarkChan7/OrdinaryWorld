package com.markchan.carrier.presenter.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/6/28
 */
public class CacheDirHelper {

    private static final String TAG = "CacheDirHelper";

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题, 切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空, 为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录, 否则优先返回SD卡缓存目录
     */
    public static File getCacheDir(Context context, String type) {
        File cacheDir = getExternalCacheDirectory(context, type);
        if (cacheDir == null) {
            cacheDir = getInternalCacheDirectory(context, type);
        }

        if (cacheDir == null) {
            Log.e(TAG, "getCacheDir fail, the reason is mobile phone unknown exception!");
        } else {
            if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                Log.e(TAG, "getCacheDir fail, the reason is make directory fail!");
            }
        }
        return cacheDir;
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                {@link Environment#DIRECTORY_MUSIC},
     *                {@link Environment#DIRECTORY_PODCASTS},
     *                {@link Environment#DIRECTORY_RINGTONES},
     *                {@link Environment#DIRECTORY_ALARMS},
     *                {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link Environment#DIRECTORY_PICTURES},
     *                {@link Environment#DIRECTORY_MOVIES}, or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalCacheDirectory(Context context, String type) {
        File externalCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)) {
                externalCacheDir = context.getExternalCacheDir();
            } else {
                externalCacheDir = context.getExternalFilesDir(type);
            }

            if (externalCacheDir == null) { // 有些手机需要通过自定义目录
                externalCacheDir = new File(Environment.getExternalStorageDirectory(),
                        "Android/data/" + context.getPackageName() + "/cache/" + type);
            }

            if (!externalCacheDir.exists() && !externalCacheDir.mkdirs()) {
                Log.e(TAG, "getExternalDirectory fail, the reason is make directory fail!");
            }
        } else {
            Log.e(TAG, "getExternalDirectory fail, the reason is SDCard nonexistence or SDCard mount fail!");
        }
        return externalCacheDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param type 子目录, 可以为空, 为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败） 注：该方法获取的目录是能供当前应用自己使用, 外部应用没有读写权限, 如: 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context, String type) {
        File internalCacheDir;
        if (TextUtils.isEmpty(type)) {
            internalCacheDir = context.getCacheDir(); // /data/data/app_package_name/cache
        } else {
            internalCacheDir = new File(context.getFilesDir(), type); // /data/data/app_package_name/files/type
        }

        if (!internalCacheDir.exists() && !internalCacheDir.mkdirs()) {
            Log.e(TAG, "getInternalDirectory fail, the reason is make directory fail!");
        }
        return internalCacheDir;
    }
}

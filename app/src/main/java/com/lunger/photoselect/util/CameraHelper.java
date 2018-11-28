package com.lunger.photoselect.util;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.Calendar;

/**
 * Created by Allen on 2018/10/31 0031.
 */

public class CameraHelper {

    private String mImagePath;
    private Activity mActivity;

    public CameraHelper(Activity activity) {
        mActivity = activity;
    }

    public void goCamera() {
        String dir = getTakePhotoDir();
        File photoDir = new File(dir);
        if (!photoDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            photoDir.mkdirs();
        }
        File mCurrentFile = new File(dir, getPhotoName());
        mImagePath = mCurrentFile.toString();
        Intent intent = getCameraIntent(mCurrentFile);
        mActivity.startActivityForResult(intent, 10001);
        mActivity.overridePendingTransition(0, 0);
    }

    private Intent getCameraIntent(File f) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri imageUri = FileProvider.getUriForFile(mActivity, "bk.androidreader1.fileprovider", f);//通过FileProvider创建一个content类型的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        }
        return intent;
    }

    private String getTakePhotoDir() {
        return getRootDir() + "camera";
    }

    private String getPhotoName() {
        return Calendar.getInstance().getTimeInMillis() + ".JPEG";
    }

    public static String getRootDir() {
        String rootDir;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            } catch (NullPointerException e) {
                rootDir = Environment.getRootDirectory().getAbsolutePath();
            }
        } else {
            rootDir = Environment.getRootDirectory().getAbsolutePath();
        }
        return rootDir + "/selector/";
    }

    public String getPath() {
        return mImagePath;
    }

    public void scanFile() {
        MediaScannerConnection.scanFile(mActivity, new String[]{mImagePath},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }
}

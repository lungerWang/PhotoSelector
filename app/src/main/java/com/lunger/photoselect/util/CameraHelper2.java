package com.lunger.photoselect.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Allen on 2018/11/12 0012.
 */

public class CameraHelper2 {
    public final static int REQUEST_CAMERA = 909;

    private String cameraPath;

    /**
     * start to camera、preview、crop
     */
    public void startOpenCamera(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            File cameraFile = PictureFileUtils.createCameraFile(activity, 1,"", ".JPEG");
            cameraPath = cameraFile.getAbsolutePath();
            Uri imageUri = parUri(activity, cameraFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activity.startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }

    /**
     * 生成uri
     *
     *
     * @param activity
     * @param cameraFile
     * @return
     */
    private Uri parUri(Activity activity, File cameraFile) {
        Uri imageUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //通过FileProvider创建一个content类型的Uri
            String authority = activity.getPackageName() + ".provider";
            imageUri = FileProvider.getUriForFile(activity, authority, cameraFile);
        } else {
            imageUri = Uri.fromFile(cameraFile);
        }
        return imageUri;
    }

}

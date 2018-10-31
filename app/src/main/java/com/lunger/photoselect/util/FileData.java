package com.lunger.photoselect.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lunger.photoselect.bean.AlbumBean;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by arvinljw on 17/12/25 16:58
 * Function：获取本地的图片视频数据
 * Desc：通过content provider获取
 */
public class FileData {

    public static List<AlbumBean> getImageFolderData(final Activity context) {


        final ArrayList<AlbumBean> folders = new ArrayList<>();
        Cursor cursor = getImageCursor(context);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //获取当前游标所指的图片信息
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                AlbumBean entity = new AlbumBean();
                entity.setPath(path);
                entity.setItemType(1);
                folders.add(entity);
                // foldersMap.get(allKey).add(entity);

                //将当前图片加入到相应的文件图集中
//                        File parentFile = new File(path).getParentFile();
//                        if (foldersMap.containsKey(parentFile.getName())) {
//                            foldersMap.get(parentFile.getName()).add(entity);
//                        } else {
//                            ArrayList<FileEntity> images = new ArrayList<>();
//                            images.add(entity);
//                            foldersMap.put(parentFile.getName(), images);
//                        }
            }
            cursor.close();
        }
        return folders;
    }

    private static Cursor getImageCursor(Context context) {
        String[] supportMineTypes = getSupportMineTypes();
        return context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                getSelection(supportMineTypes), supportMineTypes, MediaStore.Images.Media.DATE_MODIFIED + " desc");
    }

    private static String getSelection(String[] supportMineTypes) {
        StringBuilder selection = new StringBuilder();
        for (int i = 0; i < supportMineTypes.length; i++) {
            selection.append(MediaStore.Images.Media.MIME_TYPE + "=?");
            if (i != supportMineTypes.length - 1) {
                selection.append(" or ");
            }
        }
        return selection.toString();
    }

    private static String[] getSupportMineTypes() {
        return new String[]{"image/jpeg", "image/png"};
    }


    public static List<AlbumBean> getSystemPhotoList(Context context) {
        List<AlbumBean> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0) return null; // 没有图片
        AlbumBean entity;
        while (cursor.moveToNext()) {
            int index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(index); // 文件地址
            File file = new File(path);
            if (file.exists()) {
                entity = new AlbumBean();
                entity.setPath(path);
                entity.setItemType(1);
                result.add(entity);
            }
        }

        return result;
    }

}

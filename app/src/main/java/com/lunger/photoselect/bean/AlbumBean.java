package com.lunger.photoselect.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Allen on 2018/10/31 0031.
 */

public class AlbumBean implements MultiItemEntity {

    private String path;
    private int itemType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}

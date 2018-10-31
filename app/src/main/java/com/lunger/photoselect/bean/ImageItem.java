package com.lunger.photoselect.bean;

import java.io.Serializable;

/**
 * 一个图片对象
 *
 * @author Administrator
 */
@SuppressWarnings("serial")
public class ImageItem implements Serializable
{
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    public boolean isSelected = false;
}

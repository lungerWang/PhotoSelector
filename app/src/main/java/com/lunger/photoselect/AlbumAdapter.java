package com.lunger.photoselect;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lunger.photoselect.bean.AlbumBean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Allen on 2018/10/30 0030.
 */

public class AlbumAdapter extends BaseMultiItemQuickAdapter<AlbumBean, BaseViewHolder> {

    // 照相按钮
    public static final int TYPE_CAMERA = 0;
    // 相册
    public static final int TYPE_PIC = 1;

    private final LinkedList<String> mSelectList;

    public AlbumAdapter(@Nullable List<AlbumBean> data, LinkedList<String> selectList) {
        super(data);
        addItemType(TYPE_CAMERA, R.layout.item_go_camera);
        addItemType(TYPE_PIC, R.layout.item_album_pic);
        mSelectList = selectList;
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumBean bean) {
        ImageView iv_pic = (ImageView) helper.getView(R.id.iv_pic);
        //第0个位置显示拍照按钮
        if (bean.getItemType() == TYPE_CAMERA) {
            iv_pic.setImageResource(R.mipmap.def_reply_camera);
        } else {
            String path = bean.getPath();
            if (!TextUtils.isEmpty(path)) {
                Glide.with(mContext).load(path).asBitmap().into(iv_pic);
            } else {
                iv_pic.setImageBitmap(null);
            }
            //标号数字
            TextView tv_number = (TextView) helper.getView(R.id.tv_number);
            if(mSelectList == null || mSelectList.size() == 0 || !mSelectList.contains(path)){
                tv_number.setText("");
                tv_number.setBackgroundResource(R.mipmap.def_reply_default);
            }else{
                int number = mSelectList.indexOf(path);
                tv_number.setText(String.valueOf(++number));
                tv_number.setBackgroundResource(R.mipmap.def_reply_select);
            }
        }
    }
}

package com.lunger.photoselect.util;

import java.util.LinkedList;

/**
 * Created by Allen on 2018/10/31 0031.
 */

public class AlbumSelector {

    //添加成功
    public static final int STATUS_ADD_SUCCESS = 0;
    //添加失败
    public static final int STATUS_ADD_FAILED = 1;
    //删除成功
    public static final int STATUS_REMOVE_SUCCESS = 2;
    private int maxLength;

    //选中的图片
    private LinkedList<String> selectList = new LinkedList<>();

    public AlbumSelector(int maxLength){
        this.maxLength = maxLength;
    }

    /**
     *
     * @param path
     * @return
     */
    public int addOrRemove(String path){
        //如果已经包含，则为删除
        if(selectList.contains(path)){
            selectList.remove(path);
            return STATUS_REMOVE_SUCCESS;
        }else{
            if(selectList.size() >= maxLength){
                return STATUS_ADD_FAILED;
            }else{
                selectList.addLast(path);
                return STATUS_ADD_SUCCESS;
            }
        }
    }

    public LinkedList<String> getSelectList(){
        return selectList;
    }

}

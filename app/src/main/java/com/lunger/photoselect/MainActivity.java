package com.lunger.photoselect;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lunger.photoselect.bean.AlbumBean;
import com.lunger.photoselect.permission.PermissionListener;
import com.lunger.photoselect.permission.PermissionManage;
import com.lunger.photoselect.util.AlbumSelector;
import com.lunger.photoselect.util.CameraHelper;
import com.lunger.photoselect.util.CameraHelper2;
import com.lunger.photoselect.util.DividerGridItemDecoration;
import com.lunger.photoselect.util.FileData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rlv_album)
    RecyclerView rlv_album;

    //相册列表
    public List<AlbumBean> dataList;
    private AlbumAdapter mAlbumAdapter;
    private CameraHelper mCameraHelper = new CameraHelper(this);

    private AlbumSelector mAlbumSelector = new AlbumSelector(9);
    private CameraHelper2 mAlbumSelector2 = new CameraHelper2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getPermission();

        initData();
        initWidget();
    }

    private void getPermission() {
        // 初始化权限 android.Manifest.permission.READ_PHONE_STATE
        if (!PermissionManage.hasPermission(this
                , android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                , android.Manifest.permission.CAMERA)) {
            PermissionManage.with(this)
                    .requestCode(0)
                    .permission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
                    .send();
        }
    }

    private void initData() {
        //初始化相冊幫助類
        /*AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(this);

        dataList = helper.getAllPicPath();*/
        dataList = FileData.getSystemPhotoList(this);
        //第一个位置加占位
        dataList.add(0, new AlbumBean());
    }

    private void initWidget() {
        mAlbumAdapter = new AlbumAdapter(dataList, mAlbumSelector.getSelectList());
        mAlbumAdapter.setHasStableIds(true);
        rlv_album.setLayoutManager(new GridLayoutManager(this, 3));
        rlv_album.setAdapter(mAlbumAdapter);
        rlv_album.addItemDecoration(new DividerGridItemDecoration(this, 6, Color.parseColor("#ffffff")));
        mAlbumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("wbl", "position = " + position);
                Log.d("wbl", "path = " + dataList.get(position).getPath());
                if (position != 0) {
                    //点击图片
                    int result = mAlbumSelector.addOrRemove(dataList.get(position).getPath());
                    if(result == AlbumSelector.STATUS_ADD_SUCCESS || result == AlbumSelector.STATUS_REMOVE_SUCCESS){
                        mAlbumAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(MainActivity.this, "图片不能超过9张", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //点击拍照
                    if(mAlbumSelector.getSelectList().size() >= 9){
                        Toast.makeText(MainActivity.this, "图片不能超过9张", Toast.LENGTH_SHORT).show();
                    }else{
                        //跳转拍照界面
                        //mCameraHelper.goCamera();
                        mAlbumSelector2.startOpenCamera(MainActivity.this);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case 10001:
//                    mCameraHelper.scanFile();
//                    AlbumBean albumBean = new AlbumBean();
//                    albumBean.setItemType(1);
//                    albumBean.setPath(mCameraHelper.getPath());
//                    dataList.add(1, albumBean);
//                    mAlbumSelector.addOrRemove(mCameraHelper.getPath());
//                    mAlbumAdapter.notifyDataSetChanged();
//                    break;
//            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManage.onRequestPermissionsResult(requestCode, permissions, grantResults, null);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {

        }
    };

}

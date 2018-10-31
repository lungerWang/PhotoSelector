package com.lunger.photoselect;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lunger.photoselect.bean.AlbumBean;
import com.lunger.photoselect.bean.ImageBucket;
import com.lunger.photoselect.permission.PermissionListener;
import com.lunger.photoselect.permission.PermissionManage;
import com.lunger.photoselect.util.AlbumHelper;
import com.lunger.photoselect.util.AlbumSelector;
import com.lunger.photoselect.util.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rlv_album)
    RecyclerView rlv_album;

    //相册列表
    public List<AlbumBean> dataList;
    private AlbumAdapter mAlbumAdapter;
    //选中的图片
    private List<String> selectList = new ArrayList<>();
    private AlbumSelector mAlbumSelector = new AlbumSelector();

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
        AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(this);

        dataList = helper.getAllPicPath();
        //第一个位置加占位
        dataList.add(0, new AlbumBean());
    }

    private void initWidget() {
        mAlbumAdapter = new AlbumAdapter(dataList, mAlbumSelector.getSelectList());
        rlv_album.setLayoutManager(new GridLayoutManager(this, 3));
        rlv_album.setAdapter(mAlbumAdapter);
        rlv_album.addItemDecoration(new DividerGridItemDecoration(this, 6, Color.parseColor("#ffffff")));
        mAlbumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("wbl", "position = " + position);
                Log.d("wbl", "path = " + dataList.get(position).getPath());
                if (position != 0) {
                    int result = mAlbumSelector.addOrRemove(dataList.get(position).getPath());
                    if(result == AlbumSelector.STATUS_ADD_SUCCESS || result == AlbumSelector.STATUS_REMOVE_SUCCESS){
                        mAlbumAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
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

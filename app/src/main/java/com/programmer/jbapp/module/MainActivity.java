package com.programmer.jbapp.module;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.bean.Moudle;
import com.programmer.jbapp.common.utils.permission.PermissionsManager;
import com.programmer.jbapp.common.utils.permission.PermissionsResultAction;
import com.programmer.jbapp.common.widget.CircleImageView;
import com.programmer.jbapp.common.widget.dialog.MenuDialog;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.module.adapter.MainGridAdapter;
import com.programmer.jbapp.module.technique.TechniqueListActivity;
import com.programmer.jbapp.module.view.ViewListActivity;

import java.util.ArrayList;
import java.util.List;

/***
 * 主页
 */
public class MainActivity extends AbsBaseActivity implements OnItemClickListener,View.OnClickListener{
    private String TAG = "MainActivity";
    private android.widget.GridView mModule_Gv;
    private com.programmer.jbapp.common.widget.CircleImageView mHeaderView;

    private MainGridAdapter mAdapter;
    List<Moudle> mList = new ArrayList<Moudle>();

//    dialog
    MenuDialog menuDialog ;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setEnterTransition(new Explode().setDuration(300));
//        getWindow().setExitTransition(new Explode().setDuration(300));
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {
        mModule_Gv.setOnItemClickListener(this);
        mHeaderView.setOnClickListener(this);
    }

    private void initData() {
        menuDialog = new MenuDialog(this);
        setBarLeftImg(0);
        setBarTitle("主页");
        mAdapter = new MainGridAdapter(this,mList);
        mModule_Gv.setAdapter(mAdapter);
        refreshData();
    }

    private void initUI() {
        initBar(this);
        mModule_Gv = (GridView) findViewById(R.id.main_module_gv);
        mHeaderView = (CircleImageView) findViewById(R.id.imageView);
    }

    /**
     *  刷新数据
     */
    private void refreshData() {
        Moudle moudle = new Moudle(R.drawable.main_item_technique_img,"第三方工具",TechniqueListActivity.class);
        Moudle moudle2 = new Moudle(R.drawable.main_item_technique_img,"控件",ViewListActivity.class);
        mList.add(moudle);
        mList.add(moudle2);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Moudle moudle = mList.get(i);
        startActivity(new Intent(this,moudle.getActivity()));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageView:    //头像
                menuDialog.show();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, new String[]{
                       Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this,"申请权限成功",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onDenied(String permission) {
                        Log.e(TAG, "Unable to get location without permission");
                        Toast.makeText(MainActivity.this,"申请权限失败---"+permission,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Activity-onRequestPermissionsResult() PermissionsManager.notifyPermissionsChange()");
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}

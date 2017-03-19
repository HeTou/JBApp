package com.programmer.jbapp.module.view.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.widget.flingswipe.SwipeFlingAdapterView;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */

public class Widget_FlingSwipeActivity extends AbsBaseActivity implements ItemInfo, SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener{


    SwipeFlingAdapterView swipeFlingAdapterView;
    private InnerAdapter innerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flingswipe_activity);
        initUI();
        initData();
        initEvents();

    }

    private void initEvents() {

    }

    private void initData() {

    }

    private void initUI() {
        initBar(this);
        innerAdapter = new InnerAdapter();
        swipeFlingAdapterView = (SwipeFlingAdapterView) findViewById(R.id.swipefling);
        swipeFlingAdapterView.setAdapter(innerAdapter);
        swipeFlingAdapterView.setFlingListener(this);
        swipeFlingAdapterView.setOnItemClickListener(this);

    }

    @Override
    public void removeFirstObjectInAdapter() {
        innerAdapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        if(dataObject!=null) {
            innerAdapter.add((Integer) dataObject);
        }
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        if(dataObject!=null) {
            innerAdapter.add((Integer) dataObject);
        }else{

        }
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {

    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }

    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {
        swipeFlingAdapterView.getTopCardListener().selectRight();
    }


    private class InnerAdapter extends BaseAdapter {

        private List<Integer> resList = new ArrayList<Integer>();

        public InnerAdapter() {
            resList.add(R.mipmap.first);
            resList.add(R.mipmap.second);
            resList.add(R.mipmap.third);
            resList.add(R.mipmap.fourth);
            resList.add(R.mipmap.fifth);
        }

        @Override
        public int getCount() {
            return resList.size();
        }

        @Override
        public Object getItem(int position) {
            return resList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder ;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(Widget_FlingSwipeActivity.this).inflate(R.layout.item_flingswipe, parent, false);
                holder.img = (ImageView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            }
            holder  = (ViewHolder) convertView.getTag();
            Integer integer = resList.get(position);
            holder.img.setImageResource(integer);

            return convertView;
        }


        public void remove(int index) {
            resList.remove(0);
            notifyDataSetChanged();
        }

        public void add(Integer i){
            resList.add(i);
        }
        public class ViewHolder {
            ImageView img;
        }

    }


    @Override
    public String getItemName() {
        return "卡片层叠控件";
    }

    @Override
    public String getItemDec() {
        return "通过自定义AdapterView实现";
    }
}

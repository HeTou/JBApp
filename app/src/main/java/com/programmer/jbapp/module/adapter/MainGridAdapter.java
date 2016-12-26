package com.programmer.jbapp.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.bean.Moudle;

import java.util.ArrayList;
import java.util.List;

/**
 * zft
 * 2016/11/22.
 */
public class MainGridAdapter extends BaseAdapter {

    Context mContext;
    List<Moudle> mList = new ArrayList<Moudle>();

    public MainGridAdapter(Context mContext, List<Moudle> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView  = LayoutInflater.from(mContext).inflate(R.layout.main_item,null,false);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }
        holder  = (ViewHolder) convertView.getTag();

        Moudle moudle = mList.get(i);
        holder.name.setText(moudle.getName());
        holder.img.setImageResource(moudle.getImg());
        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView name;
    }
}

package com.programmer.jbapp.module.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.bean.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * zft
 * 2016/11/22.
 */
public class ViewListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Technique> mList = new ArrayList<Technique>();

    public ViewListAdapter(Context mContext, List<Technique> mList) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.technique_item,null,false);
            holder.url_img = (ImageView) convertView.findViewById(R.id.img);
            holder.title_tv = (TextView) convertView.findViewById(R.id.title);
            holder.dec_tv = (TextView) convertView.findViewById(R.id.dec);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Technique technique = mList.get(position);
        holder.title_tv.setText(technique.getTitle());
        holder.dec_tv.setText(technique.getDec());
        return convertView;
    }

    class ViewHolder{
        ImageView url_img;
        TextView title_tv;
        TextView dec_tv;
    }
}

package com.example.nhvn.esfileexplorerclone.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhvn.esfileexplorerclone.R;
import com.example.nhvn.esfileexplorerclone.controler.MyFileControler;

import java.io.File;

/**
 * Created by nguye on 2/3/2018.
 */

public class StorageAdapter extends BaseAdapter {
    Context context;
    File[] storages;

    public StorageAdapter(Context context, File[] storages) {
        this.context = context;
        this.storages = storages;
    }

    @Override
    public int getCount() {
        return storages.length;
    }

    @Override
    public Object getItem(int i) {
        return storages[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = null;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        if(view == null){
            rowView = inflater.inflate(R.layout.storage_item, viewGroup, false);
            ViewHolder holder = new ViewHolder();
            holder.storageImageView = rowView.findViewById(R.id.storage_imageview);
            holder.storageNameTextView = rowView.findViewById(R.id.storage_name_textview);
            holder.spaceStateTextView = rowView.findViewById(R.id.space_state_textview);
            rowView.setTag(holder);
        } else {
            rowView = view;
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        if(storages[i].getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())){
            holder.storageNameTextView.setText(context.getResources().getString(R.string.internal_storage));
        } else {
            holder.storageNameTextView.setText(context.getResources().getString(R.string.sd_card));
        }
        holder.spaceStateTextView.setText((MyFileControler.formatFileLength(storages[i].getTotalSpace() - storages[i].getFreeSpace()))
                + " / "  + MyFileControler.formatFileLength(storages[i].getTotalSpace()));
        return rowView;
    }
    class ViewHolder{
        ImageView storageImageView;
        TextView storageNameTextView;
        TextView spaceStateTextView;
    }
}

package com.example.nhvn.esfileexplorerclone.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.nhvn.esfileexplorerclone.R;
import com.example.nhvn.esfileexplorerclone.model.MyFile;

import java.util.ArrayList;

/**
 * Created by nguye on 2/3/2018.
 */

public class FileAdapter extends BaseAdapter implements Filterable {
    ArrayList<MyFile> myFiles;
    ArrayList<MyFile> myFilesFiltered;
    
    Context context;

    public FileAdapter(ArrayList<MyFile> myFiles, Context context) {
        this.myFiles = myFiles;
        this.context = context;
        this.myFilesFiltered = myFiles;
    }

    @Override
    public int getCount() {
        return myFilesFiltered.size();
    }

    @Override
    public Object getItem(int i) {
        return myFilesFiltered.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = null;
        if(view == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.file_item, viewGroup, false);
            ViewHolder holder = new ViewHolder();
            holder.itemIconImageView = rowView.findViewById(R.id.item_icon_imageview);
            holder.itemNameTextView = rowView.findViewById(R.id.item_name_textview);
            holder.itemContainTextView = rowView.findViewById(R.id.item_contain_textview);
            holder.itemPermissionTextView = rowView.findViewById(R.id.item_permission_textview);
            holder.itemLastModifiedTextView = rowView.findViewById(R.id.item_last_modified_textview);
            holder.itemCheckRadioButton = rowView.findViewById(R.id.item_check_radiobutton);
            rowView.setTag(holder);
        } else {
            rowView = view;
        }
        final ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.itemNameTextView.setText(myFilesFiltered.get(i).getName());
        holder.itemContainTextView.setText(myFilesFiltered.get(i).getContain());
        holder.itemPermissionTextView.setText(myFilesFiltered.get(i).getPermission());
        holder.itemLastModifiedTextView.setText(myFilesFiltered.get(i).getLastModifiedDate());
        if(myFilesFiltered.get(i).isDirectory()){
            holder.itemIconImageView.setImageResource(R.drawable.folder_icon);
        } else {
            holder.itemIconImageView.setImageResource(R.drawable.file_icon);
        }
        return rowView;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                myFilesFiltered=(ArrayList<MyFile>)results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<MyFile> FilteredList= new ArrayList<MyFile>();
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = myFiles;
                    results.count = myFiles.size();
                }
                else {
                    for (int i = 0; i < myFiles.size(); i++) {
                        MyFile data = myFiles.get(i);
                        if (data.getName().replaceAll(" ", "").toLowerCase().contains(constraint.toString().toLowerCase()))  {
                            FilteredList.add(data);
                        }
                    }
                    results.values = FilteredList;
                    results.count = FilteredList.size();
                }
                return results;
            }
        };
        return filter;
    }


    class ViewHolder{
        ImageView itemIconImageView;
        TextView itemNameTextView;
        TextView itemContainTextView;
        TextView itemPermissionTextView;
        TextView itemLastModifiedTextView;
        RadioButton itemCheckRadioButton;
    }
}

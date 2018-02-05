package com.example.nhvn.esfileexplorerclone.model;

import android.support.annotation.NonNull;

import com.example.nhvn.esfileexplorerclone.controler.MyFileControler;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nguye on 2/3/2018.
 */

public class MyFile extends File {
    private String permission;
    public String getPermission() {
        permission = "";
        if(this.canExecute()) permission += 'e';
        if(this.canRead()) permission += 'r';
        if(this.canWrite()) permission += 'w';
        return permission;
    }

    public String getLastModifiedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date(this.lastModified());
        return sdf.format(date);
    }

    public String getContain() {
        if(this.isFile()){
            return MyFileControler.formatFileLength(this);
        } else {
            return MyFileControler.getContainItems(this);
        }
    }

    public MyFile(@NonNull String pathname) {
        super(pathname);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public MyFile(String parent, @NonNull String child) {
        super(parent, child);
    }

    public MyFile(File parent, @NonNull String child) {
        super(parent, child);
    }

    public MyFile(@NonNull URI uri) {
        super(uri);
    }

}

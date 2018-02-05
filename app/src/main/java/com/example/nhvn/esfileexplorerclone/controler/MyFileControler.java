package com.example.nhvn.esfileexplorerclone.controler;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.nhvn.esfileexplorerclone.model.MyFile;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 * Created by nguye on 2/3/2018.
 */

public class MyFileControler {
    public static String EMPTY = "EMPTY";
    public static String IS_NOT_FILE = "IS_NOT_FILE";
    public static String IS_NOT_FOLDER = "IS_NOT_FOLDER";
    public static String VIEW_MODE = "VIEW_MODE";
    public static String SELECT_MODE = "SELECT_MODE";
    public static File[] getMountedStorages(Context context){
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        for(int i = 0; i < storages.length; i++){
            File parrent = storages[i];
            while (!parrent.getName().equals("Android")){
                parrent = parrent.getParentFile();
            }
            storages[i] = parrent.getParentFile();
        }
        return storages;
    }
    public static String formatFileLength(File file){
        if(!file.isFile()){
            return "IS_NOT_FILE";
        } else {
            return formatFileLength(file.length());
        }
    }
    public static String formatFileLength(long len){
        double length = (double) len;
        int type = 0; // B
        while (length > 1024){
            length /= 1024;
            type++;
        }
        String ext = "B";
        switch (type){
            case 0:
                ext = "B"; break;
            case 1:
                ext = "KB"; break;
            case 2:
                ext = "MB"; break;
            case 3:
                ext = "GB"; break;
            case 4:
                ext = "TB"; break;
            case 5:
                ext = "PB"; break;
            default:
                ext = "Too Large"; break;
        }
        return String.format("%.2f", length) + " " +ext;
    }
    public static String getContainItems(File file){
        if(!file.isDirectory()){
            return IS_NOT_FOLDER;
        } else {
            return file.listFiles().length + " item";
        }
    }
    public static ArrayList<MyFile> getFileList(File dir){
        ArrayList<MyFile> result = new ArrayList<MyFile>();
        if(!dir.isDirectory()){
            return null;
        } else {
            File[] folders = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() && !file.isHidden();
                }
            });
            for(File folder: folders){
                result.add(new MyFile(folder.getAbsolutePath()));
            }
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isFile() && !file.isHidden();
                }
            });
            for(File file: files){
                result.add(new MyFile(file.getAbsolutePath()));
            }
            return result;
        }
    }
}

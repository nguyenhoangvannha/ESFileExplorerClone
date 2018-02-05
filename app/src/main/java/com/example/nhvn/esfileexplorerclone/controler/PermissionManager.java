package com.example.nhvn.esfileexplorerclone.controler;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by nguye on 2/3/2018.
 */

public class PermissionManager {
    public static void checkAndRequestPermission(Context context, String[] permissions){
        ArrayList<String> permissionsNeeded = new ArrayList<String>();
        for(String permission: permissions){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionsNeeded.add(permission);
            }
        }
        if(permissionsNeeded.size() > 0){
            ActivityCompat.requestPermissions((Activity) context, permissionsNeeded.toArray(new String[permissionsNeeded.size()]),1);
        }
    }
    public static boolean checkAfterRequestPermission(Context context, String[] permissions){
        ArrayList<String> permissionsNeeded = new ArrayList<String>();
        for(String permission: permissions){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionsNeeded.add(permission);
            }
        }
        if(permissionsNeeded.size() > 0){
            return false;
        } else{
            return true;
        }
    }
}

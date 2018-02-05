package com.example.nhvn.esfileexplorerclone;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nhvn.esfileexplorerclone.adapter.StorageAdapter;
import com.example.nhvn.esfileexplorerclone.controler.MyFileControler;
import com.example.nhvn.esfileexplorerclone.controler.PermissionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageActivity extends AppCompatActivity {
    GridView storageGridView;
    File[] storages;
    StorageAdapter storageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        while (!PermissionManager.checkAfterRequestPermission(this, permissions)){
            Toast.makeText(this, getResources().getString(R.string.request_permission), Toast.LENGTH_SHORT).show();
            PermissionManager.checkAndRequestPermission(this, permissions);
        };
        initComponents();
        addEvents();
    }

    private void addEvents() {
        storageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(StorageActivity.this, FileActivity.class);
                intent.putExtra("PATH", storages[i].getAbsolutePath());
                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        storageGridView = findViewById(R.id.storage_gridview);
        storages = MyFileControler.getMountedStorages(this);
        storageAdapter = new StorageAdapter(this, storages);
        storageGridView.setAdapter(storageAdapter);
    }
}

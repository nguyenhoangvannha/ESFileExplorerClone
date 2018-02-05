package com.example.nhvn.esfileexplorerclone;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nhvn.esfileexplorerclone.controler.MyFileControler;
import com.example.nhvn.esfileexplorerclone.controler.PermissionManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManager.checkAndRequestPermission(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        Button storagesButton = findViewById(R.id.storages_button);
        storagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StorageActivity.class);
                startActivity(intent);
            }
        });
        EditText logEditText = findViewById(R.id.log_edittext);
        logEditText.setText(MyFileControler.getMountedStorages(this)[0].getAbsolutePath() + "\n DIR: "
                + Environment.getExternalStorageDirectory().getAbsolutePath());
    }
}

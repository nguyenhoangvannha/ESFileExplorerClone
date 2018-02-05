package com.example.nhvn.esfileexplorerclone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhvn.esfileexplorerclone.adapter.FileAdapter;
import com.example.nhvn.esfileexplorerclone.controler.MyFileControler;
import com.example.nhvn.esfileexplorerclone.model.MyFile;

import java.io.File;
import java.util.ArrayList;

public class FileActivity extends AppCompatActivity {
    ListView fileListView;
    ArrayList<MyFile> myFiles;
    FileAdapter fileAdapter;
    private String root = "";
    private String actionMode = MyFileControler.VIEW_MODE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        root = getIntent().getStringExtra("PATH");
        initComponents();
        if((new File(root)).listFiles().length == 0){
            TextView textView = findViewById(R.id.folder_state_textview);
            textView.setText(getResources().getString(R.string.empty_folder));
            textView.setVisibility(View.VISIBLE);
        }

        addEvents();
    }

    private void initComponents() {
        fileListView = findViewById(R.id.file_listview);
        myFiles = MyFileControler.getFileList(new File(root));
        fileAdapter = new FileAdapter(myFiles, this);
        fileListView.setAdapter(fileAdapter);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menu instanceof MenuBuilder){
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        MenuItem itemSearch = menu.findItem(R.id.mnu_search);
        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fileAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(actionMode.equals(MyFileControler.SELECT_MODE)){
            Toast.makeText(this, "Exit select mode", Toast.LENGTH_SHORT).show();
            resetListView();
            actionMode = MyFileControler.VIEW_MODE;
        } else {
            super.onBackPressed();
        }
    }

    public void toolbarOnClickListener(View view){
        switch (view.getId()){
            case R.id.toolbar_copy:
                break;
            case R.id.toolbar_cut:
                break;
            case R.id.toolbar_delete:
                break;
            case R.id.toolbar_rename:
                break;
            case R.id.toolbar_more:
                break;
        }
    }


    private void addEvents() {
        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(actionMode.equals(MyFileControler.VIEW_MODE)){
                    if (((MyFile)fileListView.getItemAtPosition(i)).isDirectory()){
                        Intent intent = new Intent(FileActivity.this, FileActivity.class);
                        intent.putExtra("PATH", myFiles.get(i).getAbsolutePath());
                        startActivity(intent);

                    } else {
                        Toast.makeText(FileActivity.this, "Updating", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    RadioButton radioButton = getViewByPosition(i, fileListView).findViewById(R.id.item_check_radiobutton);
                    radioButton.setChecked(!radioButton.isChecked());
                    if(radioButton.isChecked()){
                        view.setBackgroundColor(Color.LTGRAY);
                    } else {
                        view.setBackgroundColor(Color.WHITE);
                    }
                    Toast.makeText(FileActivity.this, fileListView.getChildCount()  + ":"  + getSelectedFiles().toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });
        fileListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackgroundColor(Color.LTGRAY);
                RadioButton radioButton = view.findViewById(R.id.item_check_radiobutton);
                radioButton.setVisibility(View.VISIBLE);
                radioButton.setChecked(!radioButton.isChecked());
                for(int index = 0; index < fileListView.getChildCount(); index++){
                    View v = getViewByPosition(index, fileListView);
                    ((RadioButton)v.findViewById(R.id.item_check_radiobutton)).setVisibility(View.VISIBLE);
                }
                actionMode = MyFileControler.SELECT_MODE;
                return true;
            }
        });
    }

    private ArrayList<MyFile> getSelectedFiles(){
        ArrayList<MyFile> result = new ArrayList<MyFile>();
        for(int i = 0; i < fileListView.getChildCount(); i++){
            View view = getViewByPosition(i, fileListView);
            RadioButton checkRadioButton = view.findViewById(R.id.item_check_radiobutton);
            if(checkRadioButton.isChecked()){
                result.add((MyFile) fileListView.getItemAtPosition(i));
            }
        }
        return result;
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
    private void resetListView(){
        for(int i = 0; i < fileListView.getChildCount(); i++){
            View v = getViewByPosition(i, fileListView);
            v.setBackgroundColor(Color.WHITE);
            RadioButton checkButton = (RadioButton)v.findViewById(R.id.item_check_radiobutton);
            checkButton.setChecked(false);
            checkButton.setVisibility(View.INVISIBLE);
        }
    }
}

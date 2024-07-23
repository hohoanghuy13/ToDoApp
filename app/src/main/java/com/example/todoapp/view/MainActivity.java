package com.example.todoapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.todoapp.R;
import com.example.todoapp.databinding.ActivityMainBinding;
import com.example.todoapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    AlertDialog dialog;
    //OnClickItemTask
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setMainViewModel(new MainViewModel());
        mainBinding.executePendingBindings();

        addEvents();
    }

    private void addEvents() {
        mainBinding.btnUpload.setOnClickListener(view -> {
            Intent intentUploadData = new Intent(getApplication(), UploadActivity.class);
            startActivity(intentUploadData);
        });
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();
    }
}
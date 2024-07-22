package com.example.todoapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.todoapp.R;
import com.example.todoapp.databinding.ActivityMainBinding;
import com.example.todoapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
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
}
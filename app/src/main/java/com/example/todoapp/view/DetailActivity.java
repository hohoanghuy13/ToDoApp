package com.example.todoapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.databinding.ActivityDetailBinding;
import com.example.todoapp.factory.TaskViewModelFactory;
import com.example.todoapp.model.Task;
import com.example.todoapp.viewmodel.DetailViewModel;

public class DetailActivity extends AppCompatActivity {

    DetailViewModel detailViewModel;
    ActivityDetailBinding detailBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentDetail = getIntent();
        if(intentDetail == null){
            Toast.makeText(this, "No task data available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Task task = (Task) intentDetail.getSerializableExtra("task_item");

        if(task == null) {
            Toast.makeText(this, "No task data available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        TaskViewModelFactory modelFactory = new TaskViewModelFactory(task);
        detailViewModel = new ViewModelProvider(this, modelFactory).get(DetailViewModel.class);

        detailBinding.setDetailViewModel(detailViewModel);
        detailBinding.setLifecycleOwner(this);
        detailBinding.executePendingBindings();


    }
}
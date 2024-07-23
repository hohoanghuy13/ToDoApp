package com.example.todoapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.todoapp.R;
import com.example.todoapp.adapter.TasksAdapter;
import com.example.todoapp.databinding.ActivityMainBinding;
import com.example.todoapp.model.Task;
import com.example.todoapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    AlertDialog dialog;
    TasksAdapter adapterTasks;
    MainViewModel mainViewModel;
    List<Task> tasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainBinding.setMainViewModel(mainViewModel);
        mainBinding.setLifecycleOwner(this);
        mainBinding.executePendingBindings();

        setUpObserver();
        setUpRecyclerView();
        addEvents();
    }

    private void setUpObserver() {
        mainViewModel.getTasksLiveData().observe(this, tasksDataChanged -> {
            tasks = tasksDataChanged;
            adapterTasks.setTasks(tasks);
            adapterTasks.notifyDataSetChanged();
        });

        mainViewModel.getStateDialog().observe(this, stateDialog -> {
            if(stateDialog) {
                showDialog();
            } else if(dialog != null){
                dialog.dismiss();
            }
        });
    }

    private void setUpRecyclerView() {
        adapterTasks = new TasksAdapter(MainActivity.this, tasks);
        GridLayoutManager layoutTasks = new GridLayoutManager(MainActivity.this, 1);
        mainBinding.rvTasks.setLayoutManager(layoutTasks);
        mainBinding.rvTasks.setAdapter(adapterTasks);
    }

    private void addEvents() {
        mainBinding.btnUpload.setOnClickListener(view -> {
            Intent intentUploadData = new Intent(MainActivity.this, UploadActivity.class);
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
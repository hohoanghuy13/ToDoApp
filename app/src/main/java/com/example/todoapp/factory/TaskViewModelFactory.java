package com.example.todoapp.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapp.model.Task;
import com.example.todoapp.viewmodel.TaskViewModel;

public class TaskViewModelFactory implements ViewModelProvider.Factory {
    private final Task task;

    public TaskViewModelFactory(Task taskData) {
        this.task = taskData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(task);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}

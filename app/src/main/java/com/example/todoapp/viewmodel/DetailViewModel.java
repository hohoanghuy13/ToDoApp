package com.example.todoapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapp.model.Task;

public class DetailViewModel extends ViewModel {
    public MutableLiveData<Task> taskMutableLiveData = new MutableLiveData<>();
    public Task task;

    public DetailViewModel(Task taskData) {
        this.task = taskData;
        taskMutableLiveData.setValue(task);
    }

    public LiveData<Task> getTaskMutableLiveData() {
        return taskMutableLiveData;
    }

    public void setTask(Task task) {
        this.task = task;
        taskMutableLiveData.setValue(task);
    }
}

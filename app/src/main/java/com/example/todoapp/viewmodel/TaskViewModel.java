package com.example.todoapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapp.model.Task;

public class TaskViewModel extends ViewModel {
    public MutableLiveData<Task> taskMutableLiveData;
    public Task task;

    public TaskViewModel() {
        this.taskMutableLiveData = new MutableLiveData<>();
        task = new Task();
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

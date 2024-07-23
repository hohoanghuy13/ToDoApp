package com.example.todoapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapp.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    public MutableLiveData<List<Task>> tasksLiveData;
    public List<Task> tasks;

    public MainViewModel() {
        this.tasksLiveData = new MutableLiveData<>();
        tasks = new ArrayList<>();
        tasksLiveData.setValue(tasks);
    }

    public LiveData<List<Task>> getTasksLiveData() {
        return tasksLiveData;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        tasksLiveData.setValue(tasks);
    }
    public void retrieveData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
        ValueEventListener valueEventListener;

        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tasks.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Task task = itemSnapshot.getValue(Task.class);
                    tasks.add(task);
                }
                //adapter.notifyChanged
                //dialog.dismiss
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //dialog.dismiss
            }
        });
    }
}

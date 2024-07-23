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
    public MutableLiveData<List<Task>> tasksLiveData = new MutableLiveData<>();
    public List<Task> tasks;
    public MutableLiveData<Boolean> stateDialog = new MutableLiveData<>();
    ValueEventListener valueEventListener;

    public MainViewModel() {
        tasks = new ArrayList<>();

        retrieveData();
    }

    public LiveData<Boolean> getStateDialog() {
        return stateDialog;
    }

    public LiveData<List<Task>> getTasksLiveData() {
        return tasksLiveData;
    }
    public void retrieveData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorial");

        stateDialog.setValue(true);
        //dk if

        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Task> newTasks = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Task task = itemSnapshot.getValue(Task.class);
                    newTasks.add(task);
                }

                if(!tasks.equals(newTasks)) {
                    tasks.clear();
                    tasks.addAll(newTasks);
                    tasksLiveData.setValue(tasks);
                }

                stateDialog.setValue(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                stateDialog.setValue(false);
            }
        });
    }
}

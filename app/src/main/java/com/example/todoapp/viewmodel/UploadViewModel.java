package com.example.todoapp.viewmodel;

import android.app.AlertDialog;
import android.app.Application;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.R;
import com.example.todoapp.model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadViewModel extends AndroidViewModel {
    public MutableLiveData<Task> taskMutableLiveData;
    public Task task;
    private final Application applications;
    public MutableLiveData<Uri> uriMutableLiveData;
    public Uri uri;
    public MutableLiveData<String> toastMessage;
    public MutableLiveData<Boolean> dialogEvent;

    public UploadViewModel(@NonNull Application application) {
        super(application);
        applications = application;
        taskMutableLiveData = new MutableLiveData<>();
        uriMutableLiveData = new MutableLiveData<>();
        toastMessage = new MutableLiveData<>();
        dialogEvent = new MutableLiveData<>();
        task = new Task();
        taskMutableLiveData.setValue(task);
    }

    public MutableLiveData<Boolean> getDialogEvent() {
        return dialogEvent;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<Task> getTask() {
        return taskMutableLiveData;
    }

    public LiveData<Uri> getUri() {
        return uriMutableLiveData;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        uriMutableLiveData.setValue(uri);
    }

    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Android Images").child(uri.getLastPathSegment());

        dialogEvent.setValue(true);

        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            com.google.android.gms.tasks.Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isComplete());
            Uri uriImage = (Uri) uriTask.getResult();
            task.setImage(uriImage.toString());
            taskMutableLiveData.setValue(task);
            uploadData();
            dialogEvent.setValue(false);
        }).addOnFailureListener(e -> dialogEvent.setValue(false));
    }

    public void uploadData() {
        Task dataInput = taskMutableLiveData.getValue();
        FirebaseDatabase.getInstance().getReference("Android Tutorial")
                .child(dataInput.getTitle())
                .setValue(dataInput)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        toastMessage.setValue("Saved");
                    }
                }).addOnFailureListener(e -> {
                    toastMessage.setValue(e.getMessage());
                });
    }

//    public void onTitleTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
//        String textInput = text.toString();
//        Task taskData = taskMutableLiveData.getValue();
//        if (taskData != null && !textInput.equals(taskData.getTitle())) {
//            taskData.setTitle(textInput);
//            taskMutableLiveData.setValue(task);
//        }
//    }
//
//    public void onDescriptionTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
//        String textInput = text.toString();
//        Task taskData = taskMutableLiveData.getValue();
//        if (taskData != null && !textInput.equals(taskData.getDescription())) {
//            task.setDescription(textInput);
//            taskMutableLiveData.setValue(task);
//        }
//    }
//
//    public void onLangTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
//        String textInput = text.toString();
//        Task taskData = taskMutableLiveData.getValue();
//        if (taskData != null && !textInput.equals(taskData.getLang())) {
//            task.setLang(textInput);
//            taskMutableLiveData.setValue(task);
//        }
//    }
}

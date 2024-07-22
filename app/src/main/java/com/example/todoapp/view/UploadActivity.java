package com.example.todoapp.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todoapp.R;
import com.example.todoapp.databinding.ActivityUploadBinding;
import com.example.todoapp.model.Task;
import com.example.todoapp.viewmodel.UploadViewModel;

public class UploadActivity extends AppCompatActivity {

    ActivityUploadBinding uploadBinding;
    UploadViewModel uploadViewModel;
    Uri uri;
    String imageUrl;
    ActivityResultLauncher<Intent> activityResultLauncher;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uploadViewModel = new ViewModelProvider(this).get(UploadViewModel.class);

        uploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload);
        uploadBinding.setUploadViewModel(uploadViewModel);
        uploadBinding.executePendingBindings();
        uploadBinding.setLifecycleOwner(this);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();

                        uploadViewModel.setUri(data.getData());
                    } else {
                        Toast.makeText(UploadActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        uploadViewModel.getUri().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                uploadBinding.imgUpload.setImageURI(uri);
            }
        });

        uploadViewModel.getToastMessage().observe(this, message -> {
            if(message != null) {
                Toast.makeText(UploadActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        uploadViewModel.getDialogEvent().observe(this, dialogSaving -> {
            if (dialogSaving) {
                showDiaLogSaving();
            } else if (!dialogSaving && dialog != null) {
                dialog.dismiss();
            }
        });

        addEvents();
    }

    private void showDiaLogSaving() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();
    }

    private void addEvents() {
        uploadBinding.imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);

                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
    }

    @BindingAdapter("imageUrl")
    public static void imageUrl(ImageView imageView, String imageUrl) {
        if(imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .into(imageView);
        }
    }
}
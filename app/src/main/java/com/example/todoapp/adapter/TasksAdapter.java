package com.example.todoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.databinding.TaskItemBinding;
import com.example.todoapp.model.Task;
import com.example.todoapp.view.DetailActivity;
import com.example.todoapp.viewmodel.TaskViewModel;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksViewHolder> {
    List<Task> tasks;
    Context context;

    public TasksAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TaskItemBinding taskItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.task_item, parent, false);

        return new TasksViewHolder(taskItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        Task task = tasks.get(position);

        if(task == null) {
            Log.e("NullPointerException", "Task is null");
            return;
        }

        //Chưa sử dụng ViewModelFactory
        TaskViewModel taskViewModel = new TaskViewModel(task);
        holder.taskItemBinding.setTaskViewModel(taskViewModel);
        holder.taskItemBinding.executePendingBindings();

        holder.taskItemBinding.cvTask.setOnClickListener(view -> {
            Intent intentGoToDetailActivity = new Intent(context, DetailActivity.class);
            intentGoToDetailActivity.putExtra("task_item", task);
            context.startActivity(intentGoToDetailActivity);
        });
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }
}

class TasksViewHolder extends RecyclerView.ViewHolder {
    TaskItemBinding taskItemBinding;
    public TasksViewHolder(@NonNull TaskItemBinding itemBinding) {
        super(itemBinding.getRoot());
        this.taskItemBinding = itemBinding;
    }
}

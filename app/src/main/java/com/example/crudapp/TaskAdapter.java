package com.example.crudapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskListener onTaskListener;

    // Constructor
    public TaskAdapter(List<Task> taskList, OnTaskListener onTaskListener) {
        this.taskList = taskList;
        this.onTaskListener = onTaskListener;
    }

    // Create new views
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView, onTaskListener);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.dueDate.setText(task.getDueDate());
    }

    // Return the size of the dataset
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // Provide a reference to the views for each data item
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, dueDate;
        public Button viewButton, editButton, deleteButton;

        public TaskViewHolder(View view, OnTaskListener listener) {
            super(view);
            title = view.findViewById(R.id.tvTaskTitle);
            description = view.findViewById(R.id.tvTaskDescription);
            dueDate = view.findViewById(R.id.tvTaskDueDate);
            viewButton = view.findViewById(R.id.btnViewTask);
            editButton = view.findViewById(R.id.btnEditTask);
            deleteButton = view.findViewById(R.id.btnDeleteTask);

            viewButton.setOnClickListener(v -> listener.onViewClicked(getAdapterPosition()));
            editButton.setOnClickListener(v -> listener.onEditClicked(getAdapterPosition()));
            deleteButton.setOnClickListener(v -> listener.onDeleteClicked(getAdapterPosition()));
        }
    }

    public interface OnTaskListener {
        void onViewClicked(int position);
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }
}

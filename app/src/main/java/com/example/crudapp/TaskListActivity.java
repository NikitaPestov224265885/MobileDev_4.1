package com.example.crudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudapp.DB.TaskDataManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<Task> taskList;
    private FloatingActionButton fabAddTask;
    private TaskDataManager taskDataManager; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        recyclerView = findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskDataManager = new TaskDataManager(this); // Initialize taskDataManager
        taskList = new ArrayList<>();

        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskListener() {
            @Override
            public void onViewClicked(int position) {
                // Implement view logic
            }

            @Override
            public void onEditClicked(int position) {
                // Implement edit logic
            }

            @Override
            public void onDeleteClicked(int position) {
                // Implement delete logic
            }
        });
        recyclerView.setAdapter(adapter); // Set the adapter

        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    private void loadTasks() {
        taskList.clear();
        taskList.addAll(taskDataManager.getAllTasks());
        adapter.notifyDataSetChanged();
    }
}

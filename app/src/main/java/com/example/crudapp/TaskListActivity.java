package com.example.crudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<Task> taskList;
    private FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        recyclerView = findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        // ... (Your existing code to set up the RecyclerView and adapter)

        // Initialize your floating action button
        fabAddTask = findViewById(R.id.fabAddTask);

        // Set the OnClickListener for the floating action button
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start an activity for adding a new task
                Intent intent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
                startActivity(intent);
            }
        });
    }
}

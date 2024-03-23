package com.example.crudapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudapp.DB.TaskDataManager;


public class ViewTaskActivity extends AppCompatActivity {

    private TextView tvTaskTitle, tvTaskDescription, tvTaskDueDate;
    private TaskDataManager taskDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvTaskTitle = findViewById(R.id.tvTaskTitle);
        tvTaskDescription = findViewById(R.id.tvTaskDescription);
        tvTaskDueDate = findViewById(R.id.tvTaskDueDate);

        taskDataManager = new TaskDataManager(this);

        // Retrieve the task ID from the Intent
        int taskId = getIntent().getIntExtra("TASK_ID", -1);
        if (taskId != -1) {
            Task task = taskDataManager.getTask(taskId);
            if (task != null) {
                tvTaskTitle.setText(task.getTitle());
                tvTaskDescription.setText(task.getDescription());
                tvTaskDueDate.setText(task.getDueDate());
            }
        }
    }

    // Override the Up button functionality to call finish()
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}


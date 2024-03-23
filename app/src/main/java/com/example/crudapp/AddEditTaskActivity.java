package com.example.crudapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudapp.DB.TaskDataManager;

import java.util.Calendar;

public class AddEditTaskActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etDueDate;
    private Button btnSaveTask;
    private TaskDataManager taskDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etTitle = findViewById(R.id.etTaskTitle);
        etDescription = findViewById(R.id.etTaskDescription);
        etDueDate = findViewById(R.id.etTaskDueDate);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        taskDataManager = new TaskDataManager(this);

        int taskId = getIntent().getIntExtra("TASK_ID", -1);
        if (taskId != -1) {
            // Retrieve the task details and populate the fields
            Task task = taskDataManager.getTask(taskId);
            if (task != null) {
                etTitle.setText(task.getTitle());
                etDescription.setText(task.getDescription());
                etDueDate.setText(task.getDueDate());
            }
        }

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });

        etDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void saveTask() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String dueDate = etDueDate.getText().toString();

        if (!title.isEmpty() && !dueDate.isEmpty()) {
            taskDataManager.insertTask(title, description, dueDate);
            finish();
        } else {
            etTitle.setError(title.isEmpty() ? "Title cannot be empty" : null);
            etDueDate.setError(dueDate.isEmpty() ? "Due date cannot be empty" : null);
        }
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDueDate.setText(String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}

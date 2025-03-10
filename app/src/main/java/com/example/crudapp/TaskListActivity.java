package com.example.crudapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
    private Button fabAddTask;
    private TaskDataManager taskDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        taskDataManager = new TaskDataManager(this);
        taskList = new ArrayList<>();

        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskListener() {
            public void onViewClicked(int position) {
                Task task = taskList.get(position);
                Intent intent = new Intent(TaskListActivity.this, ViewTaskActivity.class);
                intent.putExtra("TASK_ID", task.getId());
                startActivity(intent);
            }

            @Override
            public void onEditClicked(int position) {
                Task task = taskList.get(position);
                Intent intent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
                intent.putExtra("TASK_ID", task.getId()); // Pass the ID to edit
                startActivity(intent);
            }

            @Override
            public void onDeleteClicked(int position) {
                Task task = taskList.get(position);
                taskDataManager.deleteTask(task.getId());
                taskList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, taskList.size());
            }
        }, taskDataManager);


        recyclerView = findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
            startActivity(intent);
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


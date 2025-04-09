package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private EditText taskInput;
    private TextView taskCount;
    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        taskInput = findViewById(R.id.taskInput);
        taskCount = findViewById(R.id.taskCount);
        taskRecyclerView = findViewById(R.id.taskRecyclerView);

        taskAdapter = new TaskAdapter(taskList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(taskAdapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });

        updateTaskCount();
    }

    private void addNewTask() {
        String taskText = taskInput.getText().toString();
        if (!taskText.isEmpty()) {
            taskList.add(new Task(taskText, false));
            taskAdapter.notifyItemInserted;
            taskInput.getText().clear();
            updateTaskCount();
        } else {
            Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateTaskCount() {
        taskCount.setText("Tasks: " + taskList.size());
    }
}
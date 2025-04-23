package com.example.todo;

import android.annotation.SuppressLint;
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
    private RecyclerView recyclerView; // Unified RecyclerView variable
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize views
        taskInput = findViewById(R.id.taskInput);
        taskCount = findViewById(R.id.taskCount);
        recyclerView = findViewById(R.id.taskRecyclerView); // Use recyclerView (matching activity_main2.xml)

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Sample data (optional, remove if not needed)
        taskList.add(new Task("Task 1", false));
        taskList.add(new Task("Task 2", false));
        taskList.add(new Task("Task 3", false));
        taskAdapter.notifyDataSetChanged(); // Notify adapter of data changes

        // Set up the Add button
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
            taskInput.getText().clear();
            taskAdapter.notifyItemInserted(taskList.size() - 1); // Notify adapter of new item
            updateTaskCount();
        } else {
            Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTaskCount() {
        taskCount.setText("Tasks: " + taskList.size());
    }
}
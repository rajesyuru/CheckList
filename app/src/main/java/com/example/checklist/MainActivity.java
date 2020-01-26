package com.example.checklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ToDoAdapter mToDoAdapter;
    ArrayList<ToDo> mToDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mToDos = new ArrayList<>();

        mToDos.add(new ToDo("Eat", true));
        mToDos.add(new ToDo("Eat", true));
        mToDos.add(new ToDo("Eat", true));
        mToDos.add(new ToDo("Eat", true));

        mToDoAdapter = new ToDoAdapter(this, R.layout.todo_layout, mToDos, new ToDoAdapter.OnClickListener() {
            @Override
            public void onChecked(int position, boolean isChecked) {
                ToDo toDo = mToDos.get(position);
                toDo.setDone(isChecked);

                mToDos.set(position, toDo);
                mToDoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDelete(int position) {
                mToDos.remove(position);
                mToDoAdapter.notifyDataSetChanged();

            }
        });

        mRecyclerView.setAdapter(mToDoAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}

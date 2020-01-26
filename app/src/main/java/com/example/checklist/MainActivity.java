package com.example.checklist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_add, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            Intent intent = new Intent(this, AddChecklist.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String activity = data.getStringExtra("activity");
                mToDos.add(new ToDo(activity, false));
                mToDoAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

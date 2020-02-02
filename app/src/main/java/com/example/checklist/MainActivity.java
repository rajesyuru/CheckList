package com.example.checklist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ToDoAdapter mToDoAdapter;
    ArrayList<ToDo> mToDos;
    private static final String TAG = "!!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mToDos = new ArrayList<>();

        mToDoAdapter = new ToDoAdapter(this, R.layout.todo_layout, mToDos, new ToDoAdapter.OnClickListener() {
            @Override
            public void onChecked(int position, final boolean isChecked) {
                ToDo toDo = mToDos.get(position);
                toDo.setDone(isChecked);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("MyTodo");
                query.getInBackground(toDo.getId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.put("done", isChecked);

                            object.saveInBackground();
                        } else {
                            Log.d(TAG, "done: " + e.getLocalizedMessage());
                        }
                    }
                });

                mToDos.set(position, toDo);
                mToDoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onDelete(final int position) {

                ToDo toDo = mToDos.get(position);


                ParseQuery<ParseObject> query = ParseQuery.getQuery("MyTodo");

                query.getInBackground(toDo.getId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.deleteInBackground();
                        } else {
                            Log.d(TAG, "done: " + e.getLocalizedMessage());
                        }
                    }
                });

                mToDos.remove(position);
                mToDoAdapter.notifyDataSetChanged();

            }
        });

        mRecyclerView.setAdapter(mToDoAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        load();
    }

    private void load() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyTodo");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e ==null) {
                    mToDos.clear();

                    for (int i=0;i<objects.size();i++) {
                        ParseObject parseObject = objects.get(i);

                        boolean done = parseObject.getBoolean("done");
                        String activity = parseObject.getString("activity");

                        mToDos.add(new ToDo(parseObject.getObjectId(), activity, done));

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mToDoAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "done: " + e.getLocalizedMessage());
                }
            }
        });
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
                Log.d(TAG, "onActivityResult: " + activity);

                ParseObject parseObject = new ParseObject("MyTodo");

                parseObject.put("done", false);
                parseObject.put("activity", activity);

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d(TAG, "done: ");

                            load();
                        } else {
                            Log.d(TAG, "done: " + e.getLocalizedMessage());
                        }
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

package com.schrobieapps.recyclerviewonmainthread;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> recyclerViewItems;
    private RecyclerViewAdapter recyclerViewAdapter;

    private final String BACKGROUND_TREAD = "background";
    private final String UI_TREAD = "uiThread";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewItems = new ArrayList<>();
        addFakeRecyclerViewData();

        Button backgroundBtn = (Button) findViewById(R.id.update_data_background_btn);
        backgroundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncTask(BACKGROUND_TREAD);
            }
        });
        Button uiThreadBtn = (Button) findViewById(R.id.update_data_foreground_btn);
        uiThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncTask(UI_TREAD);
            }
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAdapter = new RecyclerViewAdapter(recyclerViewItems);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void startAsyncTask(String s){
        new ChangeListAsync().execute(s);
    }

    private void addFakeRecyclerViewData(){
        for(int i = 0; i < 50; i++){
            recyclerViewItems.add(String.valueOf(i));
        }
    }

    private void updateListWithBackgroundData(){
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private class ChangeListAsync extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            recyclerViewItems.clear();
            for(int i = 50; i > 0; i--){
                recyclerViewItems.add(String.valueOf(i));
            }
            //to test that you cant update the adapter on a background thread
            if(params[0].equals(BACKGROUND_TREAD)) {
                updateListWithBackgroundData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //test that you can update only on the main thread
            updateListWithBackgroundData();
        }
    }
}

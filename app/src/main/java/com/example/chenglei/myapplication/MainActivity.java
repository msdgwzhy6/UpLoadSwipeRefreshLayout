package com.example.chenglei.myapplication;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private UpLoadSwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        swipeRefreshLayout = (UpLoadSwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setFooterResource(R.layout.layout_up_load_view);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "is refreshing!", Toast.LENGTH_SHORT).show();
            }
        });
        swipeRefreshLayout.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {

            }
        });
    }

    private void initList() {
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
        list.add("item");
    }
}

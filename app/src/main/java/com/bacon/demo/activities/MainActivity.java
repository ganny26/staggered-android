package com.bacon.demo.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;


import com.bacon.demo.R;
import com.bacon.demo.adapter.MyGridAdapter;
import com.bacon.demo.application.ImageModel;
import com.bacon.demo.listener.EndlessRecyclerViewScrollListener;
import com.bacon.demo.service.LoadFlickerFeed;
import com.bacon.demo.views.ArrayAdapterSearchView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class MainActivity extends AppCompatActivity {

   // private static String TAG = "MainActivity";
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w300";

    private EndlessRecyclerViewScrollListener scrollListener;



    //public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    MyGridAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Toolbar customToolbar;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.se_activity: {
                startActivity(new Intent(this,SearchActivity.class));
                return true;
            }
            case R.id.menuSearch:{
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void addImages(int pageNumber){
        LoadFlickerFeed task = new LoadFlickerFeed(){
            @Override
            protected void onPreExecute() {
                progressBar = (ProgressBar) findViewById(R.id.main_progress);
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPostExecute(String result) {
                progressBar = (ProgressBar) findViewById(R.id.main_progress);
                progressBar.setVisibility(View.GONE);
                synchronized(adapter) {
                    try{
                       int startPosition = adapter.getItemCount();
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = (JSONArray) jsonObject.get("results");

                        for (int i=0;i<jsonArray.length();i++) {
                            ImageModel imageModel = new ImageModel();
                            int width = 300;
                            int height = 500;
                            JSONObject resultObject = jsonArray.getJSONObject(i);
                            String posterPathUrl = POSTER_BASE_URL +  resultObject.get("poster_path");
                            imageModel.setUrl(posterPathUrl);
                            imageModel.setWidth(width);
                            imageModel.setHeight(height);
                            adapter.addDrawable(imageModel);
                            adapter.notifyItemRangeInserted(startPosition, result.length());

                        }
                    }
                    catch(JSONException e){
                        e.printStackTrace();
                    }


                }
            }
        };

        task.execute(pageNumber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        customToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        this.setSupportActionBar(customToolbar);

        adapter = new MyGridAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.grid_view);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);

        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);


        addImages(1);

        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager){
            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                super.onScrolled(view, dx, dy);
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                final int curSize = adapter.getItemCount();
                addImages(page);

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        // Notify adapter with appropriate notify methods
                        adapter.notifyItemRangeInserted(curSize,20);
                        //addImages(page);
                    }
                });
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setDuration(3000);
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);


//
//        MenuItem searchItem = menu.findItem(R.id.menuSearch);
//        final ArrayAdapterSearchView searchView = (ArrayAdapterSearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                searchView.setText("helloooooooooo");
//            }
            //            @Override

//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                searchView.setText(adapter.getItem(position).toString());
//
//            }
 //       });

        return true;
    }


}
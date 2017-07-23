package com.bacon.demo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bacon.demo.R;
import com.bacon.demo.adapter.MyGridAdapter;
import com.bacon.demo.adapter.OnLoadMoreListener;
import com.bacon.demo.application.CallbackInterface;
import com.bacon.demo.application.ImageModel;
import com.bacon.demo.listener.EndlessRecyclerViewScrollListener;
import com.bacon.demo.service.LoadFlickerFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class MainActivity extends AppCompatActivity implements CallbackInterface {

    private static String TAG = "MainActivity";

    @Override
    public void onRequestCompleted(JSONObject request, JSONObject result, String type) {

    }

    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w300";

    private EndlessRecyclerViewScrollListener scrollListener;



    //public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    MyGridAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Toolbar customToolbar;
    LinearLayoutManager linearLayoutManager;
    private TextView faSearch;
    private Handler handler;





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
            case R.id.test_activity:{
                startActivity(new Intent(this,TestActivity.class));
            }
            case R.id.product_activity:{
                startActivity(new Intent(this,ProductActivity.class));
            }
            case R.id.notify:{
                startActivity(new Intent(this,ResultActivity.class));
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void addImages(int pageNumber){
        LoadFlickerFeed task = new LoadFlickerFeed(){
            @Override
            protected void onPreExecute() {
                progressBar = (ProgressBar) findViewById(R.id.toolbar_progress_bar);
                progressBar.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPostExecute(String result) {
                progressBar = (ProgressBar) findViewById(R.id.toolbar_progress_bar);
                progressBar.setIndeterminate(true);
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
                            JSONObject media = jsonArray.getJSONObject(i);
                           // JSONObject media = jsonArray.getJSONObject(i).getJSONObject("metadata").getJSONObject("media");
                            String mediaUrl = POSTER_BASE_URL +  media.get("poster_path");

                            //String mediaUrl = ""+media.getJSONArray("images").get(0);
                            Log.i(TAG,mediaUrl);
                            imageModel.setUrl(mediaUrl);
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

        getSetUp();
        handler = new Handler();
        linearLayoutManager = new LinearLayoutManager(this);
        customToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        this.setSupportActionBar(customToolbar);



        adapter = new MyGridAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.movie_recycle_view);
        recyclerView.setHasFixedSize(true);

        //adapter = new MyGridAdapter(imageModelList,recyclerView);
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
                        adapter.setLoaded();
                       // addImages(page);
                    }
                });
            }
        };

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                final int curSize = adapter.getItemCount();
                adapter.notifyItemInserted(curSize-1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                       // studentList.remove(studentList.size() - 1);
                        adapter.notifyItemRemoved(curSize);
                        //add items one by one
                        int start = curSize;
                        int end = start + 20;

                        for (int i = start + 1; i <= end; i++) {
                         //   studentList.add(new Student("Student " + i, "AndroidStudent" + i + "@gmail.com"));
                            adapter.notifyItemInserted(curSize);
                        }
                        adapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 2000);

            }
        });
        recyclerView.addOnScrollListener(scrollListener);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setDuration(3000);
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));
    }

    private void getSetUp() {

        /**
         * Code for notification builder*/

    }



}
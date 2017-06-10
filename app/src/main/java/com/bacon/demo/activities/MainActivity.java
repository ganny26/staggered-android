package com.bacon.demo.activities;

import android.content.Intent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;


import com.bacon.demo.R;
import com.bacon.demo.adapter.MyGridAdapter;
import com.bacon.demo.application.ImageModel;
import com.bacon.demo.service.LoadFlickerFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

   // private static String TAG = "MainActivity";
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w300";

    //public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String[] colors = new String[] {"red", "blue", "black", "white", "yellow"};
    private static final String[] locations = new String[] {"seattle", "sf", "LA", "NYC"};

    MyGridAdapter adapter;
    RecyclerView gridView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String color = colors[((int) (colors.length * Math.random()))];
        String location = locations[((int) (locations.length * Math.random()))];
        String term = (String) item.getTitle();
        String[] tags = new String[]{color,location,term};
        getImagesForTag(tags);
        return true;
    }

    private void getImagesForTag(String[] tags)  {
        LoadFlickerFeed task = new LoadFlickerFeed(){
            @Override
            public void onPostExecute(String result) {
                synchronized(adapter) {
                    try{
                        int startPosition = adapter.getItemCount();
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = (JSONArray) jsonObject.get("results");
                        for (int i=0;i<jsonArray.length();i++) {
                            ImageModel imageModel = new ImageModel();
                            int width = 150;
                            int height = (int) (50 + Math.random() * 450);
                            JSONObject resultObject = jsonArray.getJSONObject(i);
                            String posterPathUrl = POSTER_BASE_URL +  resultObject.get("poster_path");
                            //
                            // String posterPathUrl = POSTER_BASE_URL +  resultObject.get("poster_path");
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
        task.execute(tags);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MyGridAdapter(this);
        gridView = (RecyclerView) findViewById(R.id.grid_view);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridView.setLayoutManager(sglm);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


}
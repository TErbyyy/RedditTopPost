package com.example.paginationdemo;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

            NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MainData> dataArrayList=new ArrayList<MainData>();
    MainAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);


        adapter =new MainAdapter(MainActivity.this,MainActivity.this,dataArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        getData();


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){

                    progressBar.setVisibility(View.VISIBLE);
                    getData();
                }
            }
        });



    }



    private void getData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MainInterface mainInterface =retrofit.create(MainInterface.class);

        Call<String> call = mainInterface.STRING_CALL();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()&& response.body() != null){
                    progressBar.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject=new JSONObject(response.body());
                        jsonObject=jsonObject.getJSONObject("data");
                        JSONArray jsonArray=jsonObject.getJSONArray("children");
                        parseResult(jsonArray);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void parseResult(JSONArray jsonArray) {

        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject object =jsonArray.getJSONObject(i).getJSONObject("data");
                MainData data = new MainData();
                data.setImage(object.getString("thumbnail"));
                data.setName(object.getString("title"));
                data.setBottom_line("auth:"+object.getString("subreddit")
                        +"   "+getHourAgo(object.getLong("created_utc"))+
                        "  com: "+object.getString("num_comments"));
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new MainAdapter(MainActivity.this,MainActivity.this,dataArrayList);
            recyclerView.setAdapter(adapter);
        }
    }
    public String getHourAgo(long time){
        long hour= ((System.currentTimeMillis()/1000-time)/60/60);
        return hour+" hour ago";

    }
}
package com.example.paginationdemo;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.URI;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<MainData> dataArrayList;
    private Activity activity;
    private Context context;

    public MainAdapter(Context context,Activity activity,ArrayList<MainData> dataArrayList){
        this.activity=activity;
        this.dataArrayList=dataArrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainData data =dataArrayList.get(position);

        Glide.with(activity).load(data.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(context,DisplayMessageActivity.class);


                intent.putExtra("pox",data.getImage());

                 context.startActivity(intent);

            }
        });
        holder.bottom_line.setText(data.getBottom_line());

        holder.textView.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView bottom_line;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            textView =itemView.findViewById(R.id.text_view);
            bottom_line=itemView.findViewById(R.id.bottom_line);
        }
    }
}

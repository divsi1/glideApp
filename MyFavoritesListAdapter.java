package com.example.testassignment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyFavoritesListAdapter  extends RecyclerView.Adapter<MyFavoritesListAdapter.ViewHolder> {

    private ArrayList<Photo> favListdata;
    Context context;
    // RecyclerView recyclerView;
    public MyFavoritesListAdapter(ArrayList<Photo> favListdata, Context context) {
        this.favListdata = favListdata;
        this.context=context;
    }
    @NonNull
    @Override
    public MyFavoritesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_favorite_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Photo myListData = favListdata.get(position);
        holder.textView.setText(favListdata.get(position).getTitle());
        //holder.imageView.setImageResource(listdata[position].getImgId());;
        //listdata.get(position).getUrl()
        // Glide.with(context).load(listdata.get(position).getUrl()).into(holder.imageView);

        Glide.with(context).load("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300").into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + myListData.getUrl(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(favListdata!=null)
        {
            return favListdata.size();

        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}

package com.example.testassignment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<Photo> listdata;
    Context context;
    Bundle mBundle;
    AddToFavoritesInterface addToFavoritesInterface;
    // RecyclerView recyclerView;
    public ListAdapter(ArrayList<Photo> listdata, Context context,AddToFavoritesInterface addToFavoritesInterface) {
        this.listdata = listdata;
        this.context=context;
        this.addToFavoritesInterface=addToFavoritesInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Photo myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getTitle());
        //holder.imageView.setImageResource(listdata[position].getImgId());;
        //listdata.get(position).getUrl()
        Log.d("url_image",listdata.get(position).getUrl());
       // Glide.with(context).load(listdata.get(position).getUrl()).into(holder.imageView);

        Glide.with(context).load("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300").into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + myListData.getUrl(), Toast.LENGTH_LONG).show();
            }
        });


                //download image
        holder.downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"successfully downloaded",Toast.LENGTH_SHORT).show();
                Runnable mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // Do some work //myListData.getThumbnailUrl
                        //https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300
                        getBitmap(("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300"));
                    }
                };
                Executor mExecutor = Executors.newSingleThreadExecutor(); mExecutor.execute(mRunnable);
            }
        });


        holder.infoIcon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                //     AppCompatActivity activity = (AppCompatActivity) view.getContext();
                MainActivity mainActivity=(MainActivity)view.getContext();
                Fragment mFragment = new InfoFragment();
                FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
                mBundle = new Bundle();
                mBundle.putInt("photo_id",myListData.getId());
                mBundle.putString("photo_title",myListData.getTitle());
                mBundle.putString("photo_url",myListData.getUrl());
                mBundle.putString("photo_thumbnail_url",myListData.getThumbnailUrl());
                mFragment.setArguments(mBundle);

                fragmentManager.beginTransaction().replace(R.id.container_layout, mFragment).
                        addToBackStack(null).commit();

            }
        });

        //add to fav
        holder.addtofavImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"photo added to fav list",Toast.LENGTH_LONG).show();
                addToFavoritesInterface.getFavPhoto(myListData);
            }
        });



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
           //     AppCompatActivity activity = (AppCompatActivity) view.getContext();
                MainActivity mainActivity=(MainActivity)view.getContext();
                Fragment mFragment = new InfoFragment();
               FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
                mBundle = new Bundle();
                mBundle.putInt("photo_id",myListData.getId());
                mBundle.putString("photo_title",myListData.getTitle());
                mBundle.putString("photo_url",myListData.getUrl());
                mBundle.putString("photo_thumbnail_url",myListData.getThumbnailUrl());
                mFragment.setArguments(mBundle);

                fragmentManager.beginTransaction().replace(R.id.container_layout, mFragment).
                        addToBackStack(null).commit();

            }
        });
    }



    @Override
    public int getItemCount() {
        if(listdata!=null)
        {
            return listdata.size();

        }
        return 0;

    }


    //DOWNLOAD
    public static Bitmap getBitmap(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Bitmap d = BitmapFactory.decodeStream(is);
            is.close();
            Log.d("download sucess","sucess");
            return d;
        } catch (Exception e) {
            Log.d("exp","fail");
            return null;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ImageView infoIcon;


        //divya
        public  ImageView downloadImage;
        public  ImageView addtofavImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.infoIcon=(ImageView)itemView.findViewById(R.id.img_info);
            this.downloadImage=(ImageView)itemView.findViewById(R.id.img_download);
            this.addtofavImage=(ImageView)itemView.findViewById(R.id.img_add_to_favorites);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
package com.example.testassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class InfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_info, null, false);
        Bundle bundle = this.getArguments();
        String titleOfData = null;
        if (bundle != null) {
            String idOfData = bundle.get("photo_id").toString();
            titleOfData = bundle.get("photo_title").toString();

        }

        TextView textView = view.findViewById(R.id.txt_title);
        textView.setText(titleOfData);
        ImageView imageView=view.findViewById(R.id.img_url);
        Glide.with(this.getContext()).
                load("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300").
                into(imageView);


        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }

}

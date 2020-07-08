package com.example.testassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyFavoritesFragment extends Fragment {
    private ArrayList<Photo> mMyFavoritePhotos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_favorite_listview, null, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMyFavoritePhotos= (ArrayList<Photo>) bundle.get("valuesArray");

        }

        RecyclerView recyclerView = view.findViewById(R.id.lst_my_favorites);
        MyFavoritesListAdapter adapter = new MyFavoritesListAdapter(mMyFavoritePhotos,this.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }


}

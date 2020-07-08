package com.example.testassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements FetchPhotoCallback,
        AddToFavoritesInterface{

    private ArrayList<Photo>mResultListWithFeildsFromAPI;
    private ArrayList<Photo>mMyFavoritePhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("divya","oncreate");
        //action bar
        androidx.appcompat.widget.Toolbar mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mResultListWithFeildsFromAPI=getPhotos("https://jsonplaceholder.typicode.com/photos");
      //  Log.d("divya",mResultListWithFeildsFromAPI.toString());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcylPhotos);
        ListAdapter adapter = new ListAdapter(mResultListWithFeildsFromAPI,this,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        //   setSupportActionBar(mTopToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_favorite)
        {
            Fragment mFragment = new MyFavoritesFragment();
            FragmentManager fragmentManager=this.getSupportFragmentManager();
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("valuesArray", mMyFavoritePhotos);
            mFragment.setArguments(mBundle);

            fragmentManager.beginTransaction().replace(R.id.container_layout, mFragment).
                    addToBackStack(null).commit();

        }
        return true;
    }

    @Override
    public void onFetchPhotoSuccess(String mResult) {
        String response=mResult;
    }

    //code to fetch rrsults
    private ArrayList<Photo> getPhotos(String photoURL) {

        URL url= null;
        try {
            url = new URL(photoURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

  Log.d("url",url.toString());
        try {
            String strResult= new FetchPhotpApi(this).execute(url).get();
            try {
                Log.d("url",strResult);

                if(mResultListWithFeildsFromAPI!=null)
                {
                    mResultListWithFeildsFromAPI.clear();
                }
             //   JSONObject myResponse = new JSONObject(strResult);//divya
                JSONArray myResponse = new JSONArray(strResult);
                for(int i=0;i<myResponse.length();i++)
                {
                    if(mResultListWithFeildsFromAPI==null)
                    {
                        mResultListWithFeildsFromAPI=new ArrayList<Photo>();
                    }
                    else
                    {
                        //  "albumId": 1,
                        //    "id": 1,
                        //    "title": "accusamus beatae ad facilis cum similique qui sunt",
                        //    "url": "https://via.placeholder.com/600/92c952",
                        //    "thumbnailUrl": "https://via.placeholder.com/150/92c952"
                        Photo photo=new
                                Photo(
                                myResponse.getJSONObject(i).optInt("albumId"),
                                myResponse.getJSONObject(i).optInt("id"),
                                myResponse.getJSONObject(i).optString("title"),
                                myResponse.getJSONObject(i).optString("url"),
                                myResponse.getJSONObject(i).optString("thumbnailUrl"));
                        //    mResultListFromRestApi.add(mPredictions.getJSONObject(i).getString("description"));

                        mResultListWithFeildsFromAPI.add(photo);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //android.os.SystemClock.sleep(1000);
//        Log.d("response",mResultListWithFeildsFromAPI.toString());
        return mResultListWithFeildsFromAPI;

    }

    @Override
    public void getFavPhoto(Photo photo) {
        if(mMyFavoritePhotos==null)
        {
            mMyFavoritePhotos=new ArrayList<Photo>();
            mMyFavoritePhotos.add(photo);
        }
        else
        {
            mMyFavoritePhotos.add(photo);
        }
    }


}

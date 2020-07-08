package com.example.testassignment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

 class FetchPhotpApi extends AsyncTask<URL, Void, String> {

    private String resp;
    // private int tag;
    FetchPhotoCallback fetchPhotoCallback=null;

    public FetchPhotpApi(FetchPhotoCallback fetchPhotoCallback)
    {
        this.fetchPhotoCallback=fetchPhotoCallback;
    }
    @Override
    protected String doInBackground(URL... params) {
        //publishProgress("Sleeping..."); // Calls onProgressUpdate()
        final StringBuilder response  = new StringBuilder();
        HttpURLConnection httpconn = null;
        try {
            httpconn = (HttpURLConnection)params[0].openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null)
                {
                    response.append(strLine);
                }
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        fetchPhotoCallback.onFetchPhotoSuccess(s);
    }
}

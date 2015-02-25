package com.ratus.instagramclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID="5e4bb8b442144e2cad975512543ecdb8";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photos = new ArrayList<>();

        // Find listview
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //Create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);

        lvPhotos.setAdapter(aPhotos);
        // Send out api request to popular photos
        fetchPopularPhotos();
    }


    // Trigger API request
    public void fetchPopularPhotos() {

        String url = "https://api.instagram.com/v1/media/popular?client_id=5e4bb8b442144e2cad975512543ecdb8";

        // create the netwrok client
        AsyncHttpClient client = new AsyncHttpClient();

        // Trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {

            //OnSucces (worked)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("INSTA DEBUG", response.toString());

                // Iterate each of the photo items and decode the item into a java object

                JSONArray photosJSON = null;

                try {
                    photosJSON = response.getJSONArray("data");
                    // iterate arrays of posts
                    for(int i = 0; i<photosJSON.length();i++){
                        //get json object at that position
                        JSONObject photoJSON = photosJSON.getJSONObject(i);

                        //decode the attribute of the jsob into a data model
                        InstagramPhoto photo = new InstagramPhoto();

                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imageUrl= photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight= photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount= photoJSON.getJSONObject("likes").getInt("count");

                        // add the decoded objects to the photos
                        photos.add(photo);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

                // callback
                aPhotos.notifyDataSetChanged();
            }


            //onFailure (fail)


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Do something
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

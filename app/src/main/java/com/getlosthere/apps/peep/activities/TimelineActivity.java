package com.getlosthere.apps.peep.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.peep.R;
import com.getlosthere.apps.peep.adapters.TweetsAdapter;
import com.getlosthere.apps.peep.applications.TwitterApplication;
import com.getlosthere.apps.peep.listeners.EndlessRecyclerViewScrollListener;
import com.getlosthere.apps.peep.models.Tweet;
import com.getlosthere.apps.peep.rest_clients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient client;
//    private TweetsArrayAdapter adapter;
    private TweetsAdapter adapter;
    private ArrayList<Tweet> tweets;
//    private ListView lvTweets;
    private RecyclerView rvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupView();
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void setupView(){
        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        tweets = new ArrayList<>();
//        adapter = new TweetsArrayAdapter(this, tweets);
        adapter = new TweetsAdapter(this, tweets);
//        lvTweets.setAdapter(adapter);
        rvTweets.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
            }
        });
    }

    private void populateTimeline() {
        int curSize = adapter.getItemCount(); // for endless scroll maybe?
        long maxId = curSize > 0 ? tweets.get(curSize - 1).getUid() : 1;
        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                Log.d("DEBUG",response.toString());
              //  adapter.addAll(Tweet.fromJSONArray(response));
                int curSize = adapter.getItemCount();
                ArrayList<Tweet> newTweets = Tweet.fromJSONArray(response);
                tweets.addAll(newTweets);
                adapter.notifyItemRangeInserted(curSize, newTweets.size()-1);
                Log.d("DEBUG",adapter.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG","STATUS CODE = " + Integer.toString(statusCode));
                Log.d("DEBUG",responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG","STATUS CODE = " + Integer.toString(statusCode));
                Log.d("DEBUG",errorResponse.toString());
            }

            @Override
            public void onUserException(Throwable error) {
                Log.d("DEBUG",error.toString());
            }
        });
    }
}

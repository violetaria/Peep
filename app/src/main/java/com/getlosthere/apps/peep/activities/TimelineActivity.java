package com.getlosthere.apps.peep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.adapters.TweetsAdapter;
import com.getlosthere.apps.peep.applications.TwitterApplication;
import com.getlosthere.apps.peep.helpers.NetworkHelper;
import com.getlosthere.apps.peep.listeners.EndlessRecyclerViewScrollListener;
import com.getlosthere.apps.peep.models.Tweet;
import com.getlosthere.apps.peep.rest_clients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient client;
    private TweetsAdapter adapter;
    private ArrayList<Tweet> tweets;
    private RecyclerView rvTweets;
    private final int REQUEST_CODE_COMPOSE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupView();
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void setupView(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.peep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    private void launchComposeMessage(){
        Intent i = new Intent(this, ComposeActivity.class);

        // Need to come back in here and save the tweet
        startActivityForResult(i, REQUEST_CODE_COMPOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COMPOSE) {
            Tweet newTweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0, newTweet);
            adapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
//            Toast.makeText(this, "Peep successful!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miComposeTweet:
                launchComposeMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void populateTimeline() {
        int curSize = adapter.getItemCount(); // for endless scroll maybe?
        long maxId = curSize > 0 ? tweets.get(curSize - 1).getUid() : 1;
        if (NetworkHelper.isOnline() && NetworkHelper.isNetworkAvailable(this)) {
            client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    // Log.d("DEBUG",response.toString());
                    int curSize = adapter.getItemCount();
                    ArrayList<Tweet> newTweets = Tweet.fromJSONArray(response);
                    tweets.addAll(newTweets);
                    adapter.notifyItemRangeInserted(curSize, newTweets.size() - 1);
                    // Log.d("DEBUG", adapter.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", "STATUS CODE = " + Integer.toString(statusCode));
                    Log.d("DEBUG", responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("DEBUG", "STATUS CODE = " + Integer.toString(statusCode));
                    Log.d("DEBUG", errorResponse.toString());
                }

                @Override
                public void onUserException(Throwable error) {
                    Log.d("DEBUG", error.toString());
                }
            });
        } else {
            Toast.makeText(this, "Check your network connection",Toast.LENGTH_LONG).show();
        }
    }


}

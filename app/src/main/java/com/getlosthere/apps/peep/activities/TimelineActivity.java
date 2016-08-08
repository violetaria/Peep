package com.getlosthere.apps.peep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.adapters.TweetsAdapter;
import com.getlosthere.apps.peep.applications.TwitterApplication;
import com.getlosthere.apps.peep.fragments.ComposeDialogFragment;
import com.getlosthere.apps.peep.helpers.ItemClickSupportHelper;
import com.getlosthere.apps.peep.helpers.NetworkHelper;
import com.getlosthere.apps.peep.listeners.EndlessRecyclerViewScrollListener;
import com.getlosthere.apps.peep.models.Tweet;
import com.getlosthere.apps.peep.rest_clients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.ComposeDialogListener{
    private TwitterClient client;
    private TweetsAdapter adapter;
    private ArrayList<Tweet> tweets;
    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.design_bottom_sheet) RecyclerView rvBottomSheet;
    @BindView(R.id.main_content) CoordinatorLayout coordinatorLayout;

    private final int REQUEST_CODE_COMPOSE = 30;
    private final int REQUEST_CODE_DETAIL = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        setupView();
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void setupView(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.peep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int curSize = adapter.getItemCount();
                adapter.clear();
                adapter.notifyItemRangeRemoved(0,curSize);
                populateTimeline();
            }
        });

        ItemClickSupportHelper.addTo(rvTweets).setOnItemClickListener(
                new ItemClickSupportHelper.OnItemClickListener(){
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v){
                        launchTweetDetail(position);
                    }
                }
        );

        rvBottomSheet.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    private void launchComposeMessage(){
        ComposeDialogFragment myDialog = new ComposeDialogFragment();

        FragmentManager fm = getSupportFragmentManager();
        myDialog.show(fm, "fragment_compose_dialog");
    }

    private void launchTweetDetail(int position){
        Intent i = new Intent(this, DetailActivity.class);

        Tweet tweet = tweets.get(position);

        i.putExtra("tweet",Parcels.wrap(tweet));

        startActivityForResult(i, REQUEST_CODE_DETAIL);

    }

    @Override
    public void onFinishedComposePeepDialog(Tweet newTweet) {
//        Tweet newTweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
        tweets.add(0, newTweet);
        adapter.notifyItemInserted(0);
        rvTweets.scrollToPosition(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COMPOSE) {
            Tweet newTweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0, newTweet);
            adapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
//            Toast.makeText(this, "Peep successful!", Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
            // maybe see if there are new tweets?
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
                    ArrayList<Tweet> newTweets = Tweet.fromJSONArray(response);
                    loadTweetsToAdapter(adapter.getItemCount(), newTweets);
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
            ArrayList<Tweet> dbTweets = Tweet.getAll();
            loadTweetsToAdapter(adapter.getItemCount(),dbTweets);
            Toast.makeText(this, "You're offline, using DB. Check your network connection",Toast.LENGTH_LONG).show();

        }
        // check to see if this was called from a refresh
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }
    }

    private void loadTweetsToAdapter(int curSize, ArrayList<Tweet> newTweets){
        tweets.addAll(newTweets);
        adapter.notifyItemRangeInserted(curSize, newTweets.size() - 1);
    }

}

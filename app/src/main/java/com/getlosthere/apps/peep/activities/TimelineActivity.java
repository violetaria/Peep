package com.getlosthere.apps.peep.activities;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.applications.TwitterApplication;
import com.getlosthere.apps.peep.fragments.ComposeDialogFragment;
import com.getlosthere.apps.peep.fragments.TweetsListFragment;
import com.getlosthere.apps.peep.helpers.NetworkHelper;
import com.getlosthere.apps.peep.models.Tweet;
import com.getlosthere.apps.peep.rest_clients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.ComposeDialogListener{
    private TwitterClient client;

    @BindView(R.id.design_bottom_sheet) RecyclerView rvBottomSheet;
    @BindView(R.id.main_content) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.fab) FloatingActionButton btnFab;
    TweetsListFragment fTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            fTimeline = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        }
        setupView();
        client = TwitterApplication.getRestClient();
        populateTimeline(1);
    }

    private void setupView(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.peep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        rvBottomSheet.setLayoutManager(new LinearLayoutManager(this));

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(rvBottomSheet);

        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchComposeMessage();
            }
        });
    }

    private void launchComposeMessage(){
        ComposeDialogFragment myDialog = new ComposeDialogFragment();

        FragmentManager fm = getSupportFragmentManager();
        myDialog.show(fm, "fragment_compose_dialog");
    }


    @Override
    public void onFinishedComposePeepDialog(Tweet tweet) {
        fTimeline.add(tweet);
    }

    // TODO do we need this even here?  nothing gets done after the person look at tweet detail
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COMPOSE) {
//            Tweet newTweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
//            fTimeline.add(newTweet);
////            Toast.makeText(this, "Peep successful!", Toast.LENGTH_SHORT).show();
//        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
//            // maybe see if there are new tweets?
//        }
//    }

    public void populateTimeline(long maxId) {
        if (NetworkHelper.isOnline() && NetworkHelper.isNetworkAvailable(this)) {
            client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    ArrayList<Tweet> newTweets = Tweet.fromJSONArray(response);
                    fTimeline.addAll(newTweets);
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
            fTimeline.addAll(dbTweets);
            Toast.makeText(this, "You're offline, using DB. Check your network connection",Toast.LENGTH_LONG).show();
        }
    }

}

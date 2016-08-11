package com.getlosthere.apps.peep.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.getlosthere.apps.peep.applications.TwitterApplication;
import com.getlosthere.apps.peep.helpers.NetworkHelper;
import com.getlosthere.apps.peep.models.Tweet;
import com.getlosthere.apps.peep.rest_clients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by violetaria on 8/11/16.
 */
public class UserTimelineFragment extends TweetsListFragment{
    private String screenName;
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenName = getArguments().getString("screen_name");
        client = TwitterApplication.getRestClient();
        populateTimeline(1);
    }

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment fUserTimeline = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name",screenName);
        fUserTimeline.setArguments(args);
        return fUserTimeline;
    }

    public void populateTimeline(long maxId) {
        if (NetworkHelper.isOnline() && NetworkHelper.isNetworkAvailable(getActivity())) {
            client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    ArrayList<Tweet> newTweets = Tweet.fromJSONArray(response);
                    addAll(newTweets);
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
            // TODO Fix this somehow to be the user query for tweets
            ArrayList<Tweet> dbTweets = Tweet.getAll();
            addAll(dbTweets);
            Toast.makeText(getActivity(), "You're offline, using DB. Check your network connection",Toast.LENGTH_LONG).show();
        }
    }

}

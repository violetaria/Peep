package com.getlosthere.apps.peep.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.activities.DetailActivity;
import com.getlosthere.apps.peep.activities.ProfileActivity;
import com.getlosthere.apps.peep.adapters.TweetsAdapter;
import com.getlosthere.apps.peep.helpers.ItemClickSupportHelper;
import com.getlosthere.apps.peep.listeners.EndlessRecyclerViewScrollListener;
import com.getlosthere.apps.peep.models.Tweet;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by violetaria on 8/9/16.
 */
public abstract class TweetsListFragment extends Fragment {
    public TweetsAdapter adapter;
    public ArrayList<Tweet> tweets;
    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    private final int REQUEST_CODE_DETAIL = 40;

    abstract void populateTimeline(long maxId);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        ButterKnife.bind(this,contentView);
        setupView(contentView);
        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
    }

    private void setupView(View view){
        adapter = new TweetsAdapter(getActivity(), tweets);
        adapter.setViewHolderListener(new TweetsAdapter.ViewHolderListener() {
            @Override
            public void launchProfileActivity(String screenName) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);

                i.putExtra("screen_name",screenName);

                startActivity(i);
            }
        });

        rvTweets.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);

        ItemClickSupportHelper.addTo(rvTweets).setOnItemClickListener(
                new ItemClickSupportHelper.OnItemClickListener(){
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v){
                        launchTweetDetail(position);
                    }
                }
        );

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                int curSize = adapter.getItemCount(); // for endless scroll maybe?
                long maxId = curSize > 0 ? tweets.get(curSize - 1).getUid() : 1;
                populateTimeline(maxId);
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int curSize = adapter.getItemCount();
                adapter.clear();
                adapter.notifyItemRangeRemoved(0,curSize);
                populateTimeline(1);
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void launchTweetDetail(int position){
        Intent i = new Intent(getActivity(), DetailActivity.class);

        Tweet tweet = tweets.get(position);

        i.putExtra("tweet", Parcels.wrap(tweet));

        startActivityForResult(i, REQUEST_CODE_DETAIL);
    }

//    // ## TODO fix this where we get the info back from an activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
//            // maybe see if there are new tweets?
//        }
//    }


    public void addAll(ArrayList<Tweet> newTweets) {
        int curSize = adapter.getItemCount();
        tweets.addAll(newTweets);
        adapter.notifyItemRangeInserted(curSize, newTweets.size() - 1);
    }

    public void add(Tweet newTweet){
        tweets.add(0, newTweet);
        adapter.notifyItemInserted(0);
        rvTweets.scrollToPosition(0);
    }
}

package com.getlosthere.apps.peep.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.applications.TwitterApplication;
import com.getlosthere.apps.peep.fragments.UserTimelineFragment;
import com.getlosthere.apps.peep.models.User;
import com.getlosthere.apps.peep.rest_clients.TwitterClient;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements UserTimelineFragment.OnLoadedListener{
    @BindView(R.id.ivProfileImage) ImageView mIvProfileImage;
    @BindView(R.id.tvName) TextView mTvName;
    @BindView(R.id.tvTagline) TextView mTvTagline;
    @BindView(R.id.rlUserHeader) RelativeLayout mRlUserHeader;
    @BindView(R.id.tvFollwers) TextView mTvFollwers;
    @BindView(R.id.tvFollowing) TextView mTvFollowing;
    @BindView(R.id.llCounts) LinearLayout mLlCounts;
    private TwitterClient client;
    User user;
    UserTimelineFragment fragmentUserTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.peep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        client = TwitterApplication.getRestClient();

        String screenName = getIntent().getStringExtra("screen_name");
        
        if (savedInstanceState == null) {
            fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private void populateUserProfileHeader(User user) {
        getSupportActionBar().setTitle(user.getScreenName());
        mTvName.setText(user.getName());
        mTvTagline.setText(user.getTagline());
        mTvFollowing.setText(user.getFollowingCount() + " Following");
        mTvFollwers.setText(user.getFollowersCount() + " Followers");
        Picasso.with(this).load(user.getProfileImageUrl()).fit().into(mIvProfileImage);
    }

    public void onUserProfileLoaded(User user) {
        Log.d("DEBUG",Boolean.toString(fragmentUserTimeline.isVisible()));
        if (fragmentUserTimeline != null && fragmentUserTimeline.isVisible()){
            populateUserProfileHeader(user);
        }
    }
}

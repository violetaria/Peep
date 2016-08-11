package com.getlosthere.apps.peep.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.applications.TwitterApplication;
import com.getlosthere.apps.peep.fragments.UserTimelineFragment;
import com.getlosthere.apps.peep.models.User;
import com.getlosthere.apps.peep.rest_clients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.ivProfileImage)
    ImageView mIvProfileImage;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvTagline)
    TextView mTvTagline;
    @BindView(R.id.rlUserHeader)
    RelativeLayout mRlUserHeader;
    @BindView(R.id.tvFollwers)
    TextView mTvFollwers;
    @BindView(R.id.tvFollowing)
    TextView mTvFollowing;
    @BindView(R.id.llCounts)
    LinearLayout mLlCounts;
    private TwitterClient client;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.peep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                getSupportActionBar().setTitle(user.getScreenName());
                populateUserProfileHeader(user);
            }
        });

        String screenName = getIntent().getStringExtra("screen_name");
        if (savedInstanceState == null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.flContainer, fragmentUserTimeline);

            ft.commit();
        }
    }

    private void populateUserProfileHeader(User user) {
        mTvName.setText(user.getName());
        mTvTagline.setText(user.getTagline());
        mTvFollowing.setText(user.getFollowingCount() + " Following");
        mTvFollwers.setText(user.getFollowersCount() + " Followers");
        Picasso.with(this).load(user.getProfileImageUrl()).fit().into(mIvProfileImage);
    }
}

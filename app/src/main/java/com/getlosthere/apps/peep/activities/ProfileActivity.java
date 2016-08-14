package com.getlosthere.apps.peep.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.fragments.ProfileHeaderFragment;
import com.getlosthere.apps.peep.fragments.UserTimelineFragment;
import com.getlosthere.apps.peep.models.User;

import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements UserTimelineFragment.OnLoadedListener{
//    @BindView(R.id.ivProfileImage) ImageView mIvProfileImage;
//    @BindView(R.id.tvName) TextView mTvName;
//    @BindView(R.id.tvTagline) TextView mTvTagline;
//    @BindView(R.id.rlUserHeader) RelativeLayout mRlUserHeader;
//    @BindView(R.id.tvFollwers) TextView mTvFollowers;
//    @BindView(R.id.tvFollowing) TextView mTvFollowing;
//    @BindView(R.id.llCounts) LinearLayout mLlCounts;
    UserTimelineFragment fragmentUserTimeline;
    ProfileHeaderFragment fragmentProfileHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.peep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        String screenName = getIntent().getStringExtra("screen_name");

        if (savedInstanceState == null) {
            fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

//    private void populateUserProfileHeader(User user) {
//        getSupportActionBar().setTitle(user.getScreenName());
//        mTvName.setText(user.getName());
//        mTvTagline.setText(user.getTagline());
//        mTvFollowing.setText(user.getFollowingCount() + " Following");
//        mTvFollowers.setText(user.getFollowersCount() + " Followers");
//        Picasso.with(this).load(user.getProfileImageUrl()).fit().into(mIvProfileImage);
//    }

    public void onUserProfileLoaded(User user) {
        if (fragmentUserTimeline != null && fragmentUserTimeline.isVisible()){
            getSupportActionBar().setTitle(user.getScreenName());
            fragmentProfileHeader = ProfileHeaderFragment.newInstance(user);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flHeader, fragmentProfileHeader);
            ft.commit();
            //populateUserProfileHeader(user);
        }
    }
}

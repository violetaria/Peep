package com.getlosthere.apps.peep.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by violetaria on 8/14/16.
 */
public class ProfileHeaderFragment extends Fragment {
    @BindView(R.id.ivProfileImage) ImageView mIvProfileImage;
    @BindView(R.id.tvName) TextView mTvName;
    @BindView(R.id.tvTagline) TextView mTvTagline;
    @BindView(R.id.rlUserHeader) RelativeLayout mRlUserHeader;
    @BindView(R.id.tvFollwers) TextView mTvFollowers;
    @BindView(R.id.tvFollowing) TextView mTvFollowing;
    @BindView(R.id.llCounts) LinearLayout mLlCounts;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_profile_header, container, false);
        ButterKnife.bind(this, contentView);
        setupView(contentView);
        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = Parcels.unwrap(getArguments().getParcelable("user"));
    }

    private void setupView(View view) {
        mTvName.setText(user.getName());
        mTvTagline.setText(user.getTagline());
        mTvFollowing.setText(user.getFollowingCount() + " Following");
        mTvFollowers.setText(user.getFollowersCount() + " Followers");
        Picasso.with(view.getContext()).load(user.getProfileImageUrl()).fit().into(mIvProfileImage);
    }

    public static ProfileHeaderFragment newInstance(User user){
        ProfileHeaderFragment fProfileHeaderFragment = new ProfileHeaderFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(user));
        fProfileHeaderFragment.setArguments(args);
        return fProfileHeaderFragment;
    }
}

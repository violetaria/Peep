package com.getlosthere.apps.peep.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.models.Tweet;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvLikeCount) TextView tvLikeCount;
    @BindView(R.id.tvScreenName) TextView tvScreenName;
    @BindView(R.id.tvPostDate) TextView tvPostDate;
    @BindView(R.id.tvUsername) TextView tvUsername;

    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        setupViews();
    }

    private void setupViews(){
        tvBody.setText(tweet.getBody());
        tvScreenName.setText(tweet.getUser().getScreenName());
        tvUsername.setText(tweet.getUser().getName());
        tvLikeCount.setText(tweet.getFavoriteCountString());
        ivProfileImage.setImageResource(0);
        Picasso.with(this).load(tweet.getUser().getProfileImageUrl()).transform(new RoundedCornersTransformation(7, 7)).fit().centerInside().into(ivProfileImage);

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        String postDateStr = null;
        sf.setLenient(true);
        try {
            Date postDate = sf.parse(tweet.getCreatedAt());
            //Log.d("DEBUG",postDate.toString());
            DateFormat formatter = new SimpleDateFormat("h:mm a - dd MMM yy");
            postDateStr = formatter.format(postDate);
            // 4:30 PM - 06 Aug 16
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (postDateStr != null) {
            tvPostDate.setText(postDateStr);
        }

    }
}

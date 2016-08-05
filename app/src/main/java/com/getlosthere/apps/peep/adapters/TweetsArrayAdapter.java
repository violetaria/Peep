package com.getlosthere.apps.peep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.peep.R;
import com.getlosthere.apps.peep.helpers.RelativeTimeHelper;
import com.getlosthere.apps.peep.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by violetaria on 8/3/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{


    public TweetsArrayAdapter(Context context, List<Tweet> tweets){
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    // Add View Holder Pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        TextView tvPostDate = (TextView) convertView.findViewById(R.id.tvPostDate);

        tvUsername.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvScreenName.setText("@" + tweet.getUser().getScreenName());
        tvPostDate.setText(RelativeTimeHelper.getRelativeTimeAgo(tweet.getCreatedAt()));

        // clear out old image for recycle view
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return convertView;
    }
}

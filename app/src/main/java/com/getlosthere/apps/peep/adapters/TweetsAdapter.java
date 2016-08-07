package com.getlosthere.apps.peep.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.helpers.RelativeTimeHelper;
import com.getlosthere.apps.peep.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by violetaria on 8/4/16.
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    private List<Tweet> tweets;
    private Context context;

    private Context getContext(){
        return context;
    }

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvPostDate;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            tvPostDate = (TextView) itemView.findViewById(R.id.tvPostDate);
        }
    }

    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);

        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetsAdapter.ViewHolder holder, int position) {
        // get tweeet
        Tweet tweet = tweets.get(position);

        // get stuff from view holder
        ImageView ivProfileImage = holder.ivProfileImage;
        TextView tvUsername = holder.tvUsername;
        TextView tvBody = holder.tvBody;
        TextView tvScreenName = holder.tvScreenName;
        TextView tvPostDate = holder.tvPostDate;

        // set data up
        tvUsername.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvScreenName.setText("@" + tweet.getUser().getScreenName());
        tvPostDate.setText(RelativeTimeHelper.getRelativeTimeAgo(tweet.getCreatedAt()));

        // clear out old image for recycle view
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).transform(new RoundedCornersTransformation(2, 2)).fit().centerInside().into(ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}

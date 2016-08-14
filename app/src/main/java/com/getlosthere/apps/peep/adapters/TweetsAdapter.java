package com.getlosthere.apps.peep.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.helpers.PatternEditableBuilder;
import com.getlosthere.apps.peep.helpers.RelativeTimeHelper;
import com.getlosthere.apps.peep.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by violetaria on 8/4/16.
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    private List<Tweet> tweets;
    private Context context;
    private ViewHolderListener listener;

    private Context getContext() {
        return context;
    }

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
        this.listener = null;
    }

    public void setViewHolderListener(ViewHolderListener listener){
        this.listener = listener;
    }

    public static interface ViewHolderListener {
        public void launchProfileActivity(String screenName);
        public void launchReplyDialog(String screenName);
        public void retweetTweet(long tweetId);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvPostDate;
        public TextView tvLikeCount;
        public ImageView ivReplyImage;
        public TextView tvRetweetCount;
        public ImageView ivRetweetImage;

        public interface ViewHolderListener {

        }

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = ButterKnife.findById(itemView, R.id.ivProfileImage);
            tvUsername = ButterKnife.findById(itemView, R.id.tvUsername);
            tvBody = ButterKnife.findById(itemView, R.id.tvBody);
            tvScreenName = ButterKnife.findById(itemView, R.id.tvScreenName);
            tvPostDate = ButterKnife.findById(itemView, R.id.tvPostDate);
            tvLikeCount = ButterKnife.findById(itemView, R.id.tvLikeCount);
            ivReplyImage = ButterKnife.findById(itemView, R.id.ivReply);
            tvRetweetCount = ButterKnife.findById(itemView, R.id.tvRetweetCount);
            ivRetweetImage = ButterKnife.findById(itemView, R.id.ivRetweet);
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
        TextView tvLikeCount = holder.tvLikeCount;
        ImageView ivReplyImage = holder.ivReplyImage;
        TextView tvRetweetCount = holder.tvRetweetCount;
        ImageView ivRetweetImage = holder.ivRetweetImage;

        // set data up
        tvUsername.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String screenName) {
                                listener.launchProfileActivity(screenName);
                            }
                        }).into(tvBody);
        tvScreenName.setText(tweet.getUser().getScreenName());
        tvPostDate.setText(RelativeTimeHelper.getRelativeTimeAgo(tweet.getCreatedAt()));
        tvLikeCount.setText(tweet.getFavoriteCountString());
        tvRetweetCount.setText(tweet.getRetweetCountString());

        // clear out old image for recycle view
        ivProfileImage.setImageResource(android.R.color.transparent);
        ivProfileImage.setTag(tweet.getUser().getScreenName());
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.launchProfileActivity(view.getTag().toString());
            }
        });
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).transform(new RoundedCornersTransformation(7, 7)).fit().centerInside().into(ivProfileImage);

        ivReplyImage.setTag(tweet.getUser().getScreenName());
        ivReplyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG",view.getTag().toString());
                listener.launchReplyDialog(view.getTag().toString());
            }
        });

        ivRetweetImage.setTag(tweet.getUid());
        ivRetweetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.retweetTweet((Long) view.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }
}

package com.getlosthere.apps.peep.models;



    /*
    [
    {"created_at":"Wed Aug 03 03:12:49 +0000 2016",
    "id":760674758790873089,"id_str":"760674758790873089",
    "text":"Roswell Publix slaying: 200 weep, mourn at vigil for Carter Davis https:\/\/t.co\/zeglcUKWti https:\/\/t.co\/LGE9z4mhS2",
    "truncated":false,
    "entities":
    {"hashtags":[],
    "symbols":[],
    "user_mentions":[],
    "urls":
        [
            {"url":"https:\/\/t.co\/zeglcUKWti",
            "expanded_url":"http:\/\/on-ajc.com\/2b1nxM0",
    "display_url":"on-ajc.com\/2b1nxM0","indices":[66,89]}],
    "media":[{"id":760674754152005632,"id_str":"760674754152005632",
    "indices":[90,113],"media_url":"http:\/\/pbs.twimg.com\/media\/Co51qJ7WgAAcje7.jpg",
    "media_url_https":"https:\/\/pbs.twimg.com\/media\/Co51qJ7WgAAcje7.jpg","url":"https:\/\/t.co\/LGE9z4mhS2","display_url":"pic.twitter.com\/LGE9z4mhS2","expanded_url":"http:\/\/twitter.com\/ajc\/status\/760674758790873089\/photo\/1","type":"photo","sizes":{"medium":{"w":1200,"h":723,"resize":"fit"},"thumb":{"w":150,"h":150,"resize":"crop"},"large":{"w":2048,"h":1235,"resize":"fit"},"small":{"w":680,"h":410,"resize":"fit"}}}]},"extended_entities":{"media":[{"id":760674754152005632,"id_str":"760674754152005632","indices":[90,113],"media_url":"http:\/\/pbs.twimg.com\/media\/Co51qJ7WgAAcje7.jpg","media_url_https":"https:\/\/pbs.twimg.com\/media\/Co51qJ7WgAAcje7.jpg","url":"https:\/\/t.co\/LGE9z4mhS2","display_url":"pic.twitter.com\/LGE9z4mhS2","expanded_url":"http:\/\/twitter.com\/ajc\/status\/760674758790873089\/photo\/1","type":"photo","sizes":{"medium":{"w":1200,"h":723,"resize":"fit"},"thumb":{"w":150,"h":150,"resize":"crop"},"large":{"w":2048,"h":1235,"resize":"fit"},"small":{"w":680,"h":410,"resize":"fit"}}}]},"source":"<a href=\"http:\/\/www.socialflow.com\" rel=\"nofollow\">SocialFlow<\/a>","in_reply_to_status_id":null,"in_reply_to_status_id_str":null,"in_reply_to_user_id":null,"in_reply_to_user_id_str":null,"in_reply_to_screen_name":null,
    "user":{"id":4170491,"id_str":"4170491",
    "name":"AJC",
    "screen_name":"ajc",
    "location":"Atlanta, GA",
    "description":"Atlanta's best source for news.",
    "url":"http:\/\/t.co\/EvK8XZMw4P",
    "entities":{"url":{"urls":[{"url":"http:\/\/t.co\/EvK8XZMw4P","expanded_url":"http:\/\/www.ajc.com","display_url":"ajc.com","indices":[0,22]}]},"description":{"urls":[]}},"protected":false,"followers_count":427847,"friends_count":58537,"listed_count":4436,"created_at":"Wed Apr 11 12:50:15 +0000 2007","favourites_count":325,"utc_offset":-14400,"time_zone":"Eastern Time (US & Canada)","geo_enabled":true,"verified":true,"statuses_count":145308,"lang":"en","contributors_enabled":false,"is_translator":false,"is_translation_enabled":false,
    "profile_background_color":"00539B","profile_background_image_url":"http:\/\/pbs.twimg.com\/profile_background_images\/378800000091127693\/6d3a47ce57110dc1a703b0110831c19e.jpeg","profile_background_image_url_https":"https:\/\/pbs.twimg.com\/profile_background_images\/378800000091127693\/6d3a47ce57110dc1a703b0110831c19e.jpeg","profile_background_tile":false,
    "profile_image_url":"http:\/\/pbs.twimg.com\/profile_images\/667060822930145280\/ZFty7bte_normal.jpg","profile_image_url_https":"https:\/\/pbs.twimg.com\/profile_images\/667060822930145280\/ZFty7bte_normal.jpg","profile_banner_url":"https:\/\/pbs.twimg.com\/profile_banners\/4170491\/1425070056","profile_link_color":"00539B","profile_sidebar_border_color":"FFFFFF","profile_sidebar_fill_color":"F0F5ED","profile_text_color":"333333","profile_use_background_image":false,"has_extended_profile":false,"default_profile":false,"default_profile_image":false,"following":true,"follow_request_sent":false,"notifications":false},"geo":null,"coordinates":null,"place":null,"contributors":null,"is_quote_status":false,"retweet_count":0,"favorite_count":0,"favorited":false,"retweeted":false,"possibly_sensitive":false,"possibly_sensitive_appealable":false,"lang":"en"}

    ]
    */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by violetaria on 8/2/16.
 */
@Table(name = "Tweets")
@Parcel(analyze = { Tweet.class})
public class Tweet extends Model {
    @Column(name = "Body")
    public String body;

    @Column(name = "UID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long uid;

    @Column(name = "CreatedAt")
    public String createdAt;

    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.NO_ACTION)
    public User user;

    @Column(name = "FavoritedCount")
    public int favoriteCount;

    @Column(name = "RetweetCount")
    public int retweetCount;

    @Column(name = "Favorited")
    public boolean favorited;

    public int getRetweetCount() { return retweetCount; };

    public boolean getFavorited() { return favorited; }

    public String getRetweetCountString() { return Integer.toString(retweetCount); }

    public User getUser(){
        return user;
    }

    public long getUid() {
        return uid;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getFavoriteCount() { return favoriteCount; }

    public String getFavoriteCountString() { return Integer.toString(favoriteCount); }

    public Tweet(){
        super();
    }

    public static Tweet findOrCreateFromJSONObect(JSONObject jsonObject){
        long rid = 0;
        try {
            rid = jsonObject.getLong("id");
            Tweet existingTweet = new Select().from(Tweet.class).where("uid = ?",rid).executeSingle();

            if (existingTweet != null) {
                return existingTweet;
            } else {
                Tweet tweet = Tweet.fromJSONObject(jsonObject);
                tweet.save();
                return tweet;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null; // ## TODO fix this
    }

    public static Tweet fromJSONObject(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.favoriteCount = jsonObject.getInt("favorite_count");
            tweet.favorited = jsonObject.getBoolean("favorited");
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.user = User.findOrCreateFromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.findOrCreateFromJSONObect(tweetJson);
                if (tweet != null) {
                    tweets.add(i,tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }

    public static ArrayList<Tweet> getAll(){
        ArrayList<Tweet> tweetArrayList = new ArrayList<Tweet>();
        List<Tweet> tweetList = new Select().from(Tweet.class).orderBy("uid DESC").limit(200).execute();
        for(int i = 0; i < tweetList.size(); i++) {
            tweetArrayList.add(tweetList.get(i));
        }
        return tweetArrayList;
    }

//    public static List<Tweet> getMentions(User user) {
//        // This is how you execute a query
//        return new Select()
//                .from(Tweet.class)
//                .where("User = ?", category.getId())
//                .orderBy("Name ASC")
//                .execute();
//    }

}

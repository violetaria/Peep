package com.getlosthere.apps.peep.rest_clients;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.getlosthere.apps.peep.BuildConfig;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = BuildConfig.CONSUMER_KEY;
    public static final String REST_CONSUMER_SECRET = BuildConfig.SECRET_KEY; // Change this
    public static final String REST_CALLBACK_URL = "oauth://glhpeep";

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count",25);
        if (maxId != 1) {
            params.put("max_id",maxId);
        }
        client.get(apiUrl, params, handler);
    }

    public void postStatusUpdate(RequestParams params, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        client.post(apiUrl,params, handler);
    }

    public void getMentions(long maxId, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count",25);
        client.get(apiUrl, params, handler);
    }

    public void getCurrentUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        client.get(apiUrl, null, handler);
    }

    public void getUserTimeline(String screenName, long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("screen_name",screenName);
        if (maxId != 1) {
            params.put("max_id", maxId);
        }
        params.put("count",25);
        client.get(apiUrl, params, handler);
    }

    public void getOtherUserInfo(String screenName, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name",screenName);
        client.get(apiUrl, params, handler);
    }


    public void postRetweet(Long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/retweet/" + Long.toString(tweetId) + ".json");
        client.post(apiUrl, null, handler);
    }


    public void likeTweet(Long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("favorites/create.json");
        RequestParams params = new RequestParams();
        params.put("id",tweetId);
        client.post(apiUrl, params, handler);
    }

    public void unlikeTweet(Long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("favorites/destroy.json");
        RequestParams params = new RequestParams();
        params.put("id",tweetId);
        client.post(apiUrl, params, handler);
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
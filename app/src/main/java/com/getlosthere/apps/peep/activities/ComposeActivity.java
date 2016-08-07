package com.getlosthere.apps.peep.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.applications.TwitterApplication;
import com.getlosthere.apps.peep.helpers.NetworkHelper;
import com.getlosthere.apps.peep.models.Tweet;
import com.getlosthere.apps.peep.rest_clients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;
    private TextView tvLetterCount;
    private EditText etBody;
    private Button btnPeep;
    private Tweet tweet;
    private final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();

        setupViews();
    }

    private void setupViews(){
        tvLetterCount = (TextView) findViewById(R.id.tvLetterCount);
        etBody = (EditText) findViewById(R.id.etPeepBody);
        btnPeep = (Button) findViewById(R.id.btnPeep);


    }

    public void savePeep(View view){
        RequestParams params = new RequestParams();
        params.put("status",etBody.getText());

        if (NetworkHelper.isOnline() && NetworkHelper.isNetworkAvailable(this)) {
            client.postStatusUpdate(params,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    tweet = Tweet.fromJSONObject(response);
                    Intent data = new Intent();

                    data.putExtra("tweet", Parcels.wrap(tweet));

                    setResult(RESULT_OK,data);
                    activity.finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", "STATUS CODE = " + Integer.toString(statusCode));
                    Log.d("DEBUG", responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("DEBUG", "STATUS CODE = " + Integer.toString(statusCode));
                    Log.d("DEBUG", errorResponse.toString());
                }

                @Override
                public void onUserException(Throwable error) {
                    Log.d("DEBUG", error.toString());
                }
            });

        } else {
            Toast.makeText(this, "Check your network connection",Toast.LENGTH_LONG).show();
        }
    }
}

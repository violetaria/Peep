package com.getlosthere.apps.peep.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ComposeDialogFragment extends BottomSheetDialogFragment {
    private TwitterClient client;
    @BindView(R.id.tvLetterCount) TextView tvLetterCount;
    @BindView(R.id.etPeepBody) EditText etBody;
    @BindView(R.id.btnPeep) Button btnPeep;
    @BindView(R.id.ibtnCancel) ImageButton ibtnCancel;
    private Tweet tweet;
    private final static int MAX_CHAR_COUNT = 140;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public interface ComposeDialogListener {
        void onFinishedComposePeepDialog(Tweet tweet);
    }

    @Override
    public void setupDialog(Dialog dialog, int style){
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_compose_dialog, null);
        dialog.setContentView(contentView);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ButterKnife.bind(this,contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        client = TwitterApplication.getRestClient();
        setupViews();
    }

    private void setupViews(){
        tvLetterCount.setText(Integer.toString(MAX_CHAR_COUNT));
        etBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int curLength = etBody.getText().length();
                tvLetterCount.setText(Integer.toString(MAX_CHAR_COUNT - curLength));
            }
        });
        btnPeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePeep(view);
            }
        });
        ibtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        etBody.requestFocus();
    }

    public void savePeep(View view){
        RequestParams params = new RequestParams();
        params.put("status",etBody.getText());

        if (NetworkHelper.isOnline() && NetworkHelper.isNetworkAvailable(getContext())) {
            client.postStatusUpdate(params,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    tweet = Tweet.fromJSONObject(response);
                    ComposeDialogListener listener = (ComposeDialogListener) getActivity();
                    listener.onFinishedComposePeepDialog(tweet);
                    dismiss();
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
            Toast.makeText(getActivity(), "Check your network connection",Toast.LENGTH_LONG).show();
        }
    }


}
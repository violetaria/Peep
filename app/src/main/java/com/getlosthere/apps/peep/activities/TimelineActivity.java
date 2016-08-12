package com.getlosthere.apps.peep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.adapters.TweetsPagerAdapter;
import com.getlosthere.apps.peep.fragments.ComposeDialogFragment;
import com.getlosthere.apps.peep.fragments.TweetsListFragment;
import com.getlosthere.apps.peep.models.Tweet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.ComposeDialogListener{
    @BindView(R.id.design_bottom_sheet) RecyclerView rvBottomSheet;
    @BindView(R.id.main_content) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.fab) FloatingActionButton btnFab;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) PagerSlidingTabStrip tabStrip;

    TweetsListFragment fTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ButterKnife.bind(this);
//        if (savedInstanceState == null) {
//            fTimeline = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
//        }
        setupView();
    }

    private void setupView(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.peep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        rvBottomSheet.setLayoutManager(new LinearLayoutManager(this));

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(rvBottomSheet);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchComposeMessage();
            }
        });

        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        tabStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO fix
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);

        startActivity(i);
    }

    private void launchComposeMessage(){
        ComposeDialogFragment myDialog = new ComposeDialogFragment();

        FragmentManager fm = getSupportFragmentManager();
        myDialog.show(fm, "fragment_compose_dialog");
    }

    // TODO figure out how to deal with modal dialog from fragments

    @Override
    public void onFinishedComposePeepDialog(Tweet tweet) {
        fTimeline.add(tweet);
    }

    // TODO do we need this even here?  nothing gets done after the person look at tweet detail
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COMPOSE) {
//            Tweet newTweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
//            fTimeline.add(newTweet);
////            Toast.makeText(this, "Peep successful!", Toast.LENGTH_SHORT).show();
//        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
//            // maybe see if there are new tweets?
//        }
//    }


}

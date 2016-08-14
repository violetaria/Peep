package com.getlosthere.apps.peep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.getlosthere.apps.peep.R;
import com.getlosthere.apps.peep.adapters.TweetsPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) PagerSlidingTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ButterKnife.bind(this);
        setupView();
    }

    private void setupView(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.peep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        tabStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // TODO fix
//        return super.onOptionsItemSelected(item);
//    }

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);

        startActivity(i);
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

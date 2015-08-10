package com.udacity.gradle.builditbigger.paid;

/**
 * Created by Pedro on 8/8/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.pedrocarrillo.jokedisplay.JokeActivity;
import com.pedrocarrillo.jokedisplay.JokeFragment;
import com.udacity.gradle.builditbigger.IAsyncTaskListener;
import com.udacity.gradle.builditbigger.JokeAsyncTask;
import com.udacity.gradle.builditbigger.R;

public class MainActivity extends ActionBarActivity implements IAsyncTaskListener {

    InterstitialAd mInterstitialAd;
    String mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
                Intent intent = new Intent(MainActivity.this, JokeActivity.class);
                intent.putExtra(JokeFragment.JOKE_KEY, mJoke);
                startActivity(intent);
            }
        });
        requestNewInterstitial();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask(this);
        jokeAsyncTask.execute();
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
    }

    @Override
    public void onResult(Object o) {
        mJoke = (String)o;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("C8FD96A7288C5DA600C5ED87CA38B207")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

}

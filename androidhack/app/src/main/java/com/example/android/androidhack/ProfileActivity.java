package com.example.android.androidhack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProfileActivity extends AppCompatActivity  implements
        LoaderManager.LoaderCallbacks<Object>{


    private static final int USER_SEARCH_LOADER = 22;
    private static final int EVENT_SEARCH_LOADER = 23;

    ProfileAdapter mAdapter;
    RecyclerView mNumbersList;

    String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mId = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

        mNumbersList = (RecyclerView) findViewById(R.id.profile_rv);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mNumbersList.setLayoutManager(layoutManager);

        mAdapter = new ProfileAdapter(getApplicationContext());

        mNumbersList.setAdapter(mAdapter);


        getSupportLoaderManager().initLoader(USER_SEARCH_LOADER, null, ProfileActivity.this);
        getSupportLoaderManager().initLoader(EVENT_SEARCH_LOADER, null, ProfileActivity.this);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Object>(this) {

            Object mEvent;
            Object mUser;

            @Override
            protected void onStartLoading() {

                if (mUser != null && getId() == USER_SEARCH_LOADER) {
                    deliverResult(mUser);
                } else if(mEvent != null && getId() == EVENT_SEARCH_LOADER){
                    deliverResult(mEvent);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Object loadInBackground() {
                if (getId() == USER_SEARCH_LOADER){
                    URL weatherRequestUrl;

                    if (mId.equals("0")) weatherRequestUrl = NetworkUtils.buildUserUrl("1");
                    else weatherRequestUrl = NetworkUtils.buildUserUrl(mId);

                    try {
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                        ProfileAdapter.User myUser;
                        try{
                            myUser = JsonUtils.getUserFromJson(jsonResponse);
                        }catch (Exception e){
                            myUser = new ProfileAdapter.User("Joseph Hutchinson", "Penn State", "Hi, my name is Joseph!  I am a senior at Penn State studying Electrical Engineering.  I work really hard to get the grades I do.  Please support me!", "josephhutch");
                        }

                        return myUser;
                    } catch(Exception e) {
                        return new ProfileAdapter.User("Joseph Hutchinson", "Penn State", "Hi, my name is Joseph!  I am a senior at Penn State studying Electrical Engineering.  I work really hard to get the grades I do.  Please support me!", "josephhutch");
                    }

                } else if (getId() == EVENT_SEARCH_LOADER) {
                    String userQuery = "http://hack.nae9tmkea8.us-east-2.elasticbeanstalk.com/todo/api/v1.0/events";
                    URL weatherRequestUrl = NetworkUtils.buildUrl(userQuery);

                    try {
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                        List<ProfileAdapter.Event> myEvent;
                        try{
                            myEvent = JsonUtils.getEventsFromJson(jsonResponse);
                        }catch (Exception e){
                            return null;
                        }

                        return myEvent;
                    } catch(Exception e) {
                        return null;
                    }
                } else {
                    return null;
                }
            }

            // TODO (3) Override deliverResult and store the data in mGithubJson

            @Override
            public void deliverResult(Object data) {
                // TODO (4) Call super.deliverResult after storing the data
                if (getId() == USER_SEARCH_LOADER) {
                    mUser = data;
                } else if(getId() == EVENT_SEARCH_LOADER){
                    mEvent = data;
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

//        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (loader.getId() == USER_SEARCH_LOADER) {
            mAdapter.setUser((ProfileAdapter.User) data);
        } else if (loader.getId() == EVENT_SEARCH_LOADER && null != data) {
            mAdapter.setEvent((List<ProfileAdapter.Event>) data);
        }

        if (null == data) {
//            showErrorMessage();
        } else {

        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//
//        String queryUrl = mUrlDisplayTextView.getText().toString();
//        outState.putString(SEARCH_QUERY_URL_EXTRA, queryUrl);
    }


    public void launchPayActivity(View view){
        Intent payActivityIntent = new Intent(this, PayActivity.class);
        payActivityIntent.putExtra(Intent.EXTRA_TEXT, mId);
        startActivity(payActivityIntent);
    }

}

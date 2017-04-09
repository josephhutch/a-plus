package com.example.android.androidhack;

import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Object>{

    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    private static final String USER_BASE_URL = "http://hack.nae9tmkea8.us-east-2.elasticbeanstalk.com/todo/api/v1.0/users/josephhutch";


    private static final int USER_SEARCH_LOADER = 22;
    private static final int EVENT_SEARCH_LOADER = 23;

    StudentAdapter mAdapter;
    RecyclerView mNumbersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        mNumbersList = (RecyclerView) findViewById(R.id.pledge_rv);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mNumbersList.setLayoutManager(layoutManager);

        mAdapter = new StudentAdapter(getApplicationContext());

        mNumbersList.setAdapter(mAdapter);


        getSupportLoaderManager().initLoader(USER_SEARCH_LOADER, null, StudentActivity.this);
        getSupportLoaderManager().initLoader(EVENT_SEARCH_LOADER, null, StudentActivity.this);
    }

    private void lookupUser() {

        Uri builtUri = Uri.parse(USER_BASE_URL).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        System.out.println(builtUri.toString());
        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, url.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(USER_SEARCH_LOADER);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(USER_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(USER_SEARCH_LOADER, queryBundle, this);
        }
    }

    @Override
    public Loader<Object> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Object>(this) {

            // TODO (1) Create a String member variable called mGithubJson that will store the raw JSON
            Object mEvent;
            Object mUser;

            @Override
            protected void onStartLoading() {

//                mLoadingIndicator.setVisibility(View.VISIBLE);

                // TODO (2) If mGithubJson is not null, deliver that result. Otherwise, force a load
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
                    String userQuery = "http://hack.nae9tmkea8.us-east-2.elasticbeanstalk.com/todo/api/v1.0/users/josephhutch";
                    URL weatherRequestUrl = NetworkUtils.buildUrl(userQuery);

                    try {
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                        StudentAdapter.User myUser;
                        try{
                            myUser = JsonUtils.getUserFromJson(jsonResponse);
                        }catch (Exception e){
                            myUser = new StudentAdapter.User("Joseph Hutchinson", "Penn State", "Hi, my name is Joseph!  I am a senior at Penn State studying Electrical Engineering.  I work really hard to get the grades I do.  Please support me!", "josephhutch");
                        }

                        return myUser;
                    } catch(Exception e) {
                        return new StudentAdapter.User("Joseph Hutchinson", "Penn State", "Hi, my name is Joseph!  I am a senior at Penn State studying Electrical Engineering.  I work really hard to get the grades I do.  Please support me!", "josephhutch");
                    }

                } else if (getId() == EVENT_SEARCH_LOADER) {
                    String userQuery = "http://hack.nae9tmkea8.us-east-2.elasticbeanstalk.com/todo/api/v1.0/events";
                    URL weatherRequestUrl = NetworkUtils.buildUrl(userQuery);

                    try {
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                        List<StudentAdapter.Event> myEvent;
                        try{
                            myEvent = JsonUtils.getEventsFromJson(jsonResponse);
                            if(MainActivity.paymentAmount != null && !MainActivity.paymentAmount.equals("")) myEvent.add(0, new StudentAdapter.Event("John just supported you", "John is giving you " + MainActivity.paymentAmount + " per A", "April 9, 2017", ""));
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
            mAdapter.setUser((StudentAdapter.User) data);
        } else if (loader.getId() == EVENT_SEARCH_LOADER && null != data) {
            mAdapter.setEvent((List<StudentAdapter.Event>) data);
        }

        if (null == data) {
//            showErrorMessage();
        } else {

        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemThatWasClickedId = item.getItemId();
//        if (itemThatWasClickedId == R.id.action_search) {
//            makeGithubSearchQuery();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//
//        String queryUrl = mUrlDisplayTextView.getText().toString();
//        outState.putString(SEARCH_QUERY_URL_EXTRA, queryUrl);
    }
}

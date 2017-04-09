package com.example.android.androidhack;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SupporterActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<SupporterAdapter.User>>, SupporterAdapter.SupporterAdapterOnClickHandler {

    SupporterAdapter mAdapter;
    RecyclerView mNumbersList;

    private static final int USERS_SEARCH_LOADER = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supporter);

        mNumbersList = (RecyclerView) findViewById(R.id.supporter_rv);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mNumbersList.setLayoutManager(layoutManager);

        mAdapter = new SupporterAdapter(getApplicationContext(), this);

        mNumbersList.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(USERS_SEARCH_LOADER, null, SupporterActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.support_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<SupporterAdapter.User>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<SupporterAdapter.User>>(this) {

            List<SupporterAdapter.User> userList;

            @Override
            protected void onStartLoading() {

                if (userList != null){
                    deliverResult(userList);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<SupporterAdapter.User> loadInBackground() {

                String userQuery = "http://hack.nae9tmkea8.us-east-2.elasticbeanstalk.com/todo/api/v1.0/users";
                URL weatherRequestUrl = NetworkUtils.buildUrl(userQuery);

                try {
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                    List<SupporterAdapter.User> myUsers;
                    try{
                        myUsers = JsonUtils.getUsersFromJson(jsonResponse);
                    }catch (Exception e){
                        return new ArrayList<>();
                    }

                    return myUsers;
                } catch(Exception e) {
                    return new ArrayList<>();
                }
            }

            // TODO (3) Override deliverResult and store the data in mGithubJson

            @Override
            public void deliverResult(List<SupporterAdapter.User> data) {
                userList = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<SupporterAdapter.User>> loader, List<SupporterAdapter.User> data) {
        mAdapter.setUsers(data);
    }

    @Override
    public void onLoaderReset(Loader<List<SupporterAdapter.User>> loader) {

    }

    @Override
    public void onClick(SupporterAdapter.User clickedUser) {
        Context context = this;
        Class destinationClass = ProfileActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, clickedUser.id);
        startActivity(intentToStartDetailActivity);
    }
}

package com.example.android.androidhack;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity  implements
        LoaderManager.LoaderCallbacks<List<PayActivity.MyAccount>>{

    String mId;

    RadioGroup mAccountsGroup;
    EditText mAmountET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mId = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

        mAccountsGroup = (RadioGroup) findViewById(R.id.payment_options_rg);
        mAmountET = (EditText) findViewById(R.id.payment_amount_et);


        getSupportLoaderManager().initLoader(69, null, PayActivity.this);
    }

    @Override
    public Loader<List<MyAccount>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<MyAccount>>(this) {

            List<MyAccount> mAccounts;

            @Override
            protected void onStartLoading() {

                if (mAccounts != null){
                    deliverResult(mAccounts);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<MyAccount> loadInBackground() {
                URL weatherRequestUrl = NetworkUtils.buildGetAccountsUrl("");

                try {
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                    List<MyAccount> myAccount;
                    try{
                        myAccount = JsonUtils.getAccountsFromJson(jsonResponse);
                    }catch (Exception e){
                        myAccount = new ArrayList<>();
                    }

                    return myAccount;
                } catch(Exception e) {
                    return null;
                }

            }

            // TODO (3) Override deliverResult and store the data in mGithubJson

            @Override
            public void deliverResult(List<MyAccount> data) {
                // TODO (4) Call super.deliverResult after storing the data
                mAccounts = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<MyAccount>> loader, List<MyAccount> data) {
        for (int i=0; i<data.size(); i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(data.get(i).mNickName);
            radioButton.setHint(data.get(i).mId);
            radioButton.setPadding(5,30,30,30);
            radioButton.setTextSize(25);
            mAccountsGroup.addView(radioButton);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MyAccount>> loader) {

    }

    public static class MyAccount{
        public String mId, mNickName, mType;
        public MyAccount(String id, String nickName, String type){
            mId = id;
            mNickName = nickName;
            mType = type;
        }
    }

    public void submitClicked(View view){
        if (mAccountsGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please select an account to use.", Toast.LENGTH_SHORT).show();
        }
        if (mAmountET.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();
        }
        if (!(mAccountsGroup.getCheckedRadioButtonId() == -1) && !(mAmountET.getText().toString().equals(""))){
            int selectedId = mAccountsGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            if (Integer.parseInt(mId) == 1){
                RadioButton miRB = (RadioButton) findViewById(selectedId);


                MainActivity.paymentUrl = "http://api.reimaginebanking.com/accounts/"+miRB.getHint().toString()+"/transfers?key=f6d64fb885ad93299519595cc1a2e380";
                MainActivity.paymentBody = "{\n" +
                        "\"medium\": \"balance\",\n" +
                        "\"payee_id\": \"58ea08eda73e4942cdafd44c\",\n" +
                        "\"amount\": " + mAmountET.getText().toString() +",\n" +
                        "\"transaction_date\": \"2017-0-4-09\",\n" +
                        "\"description\": \"test\"\n" +
                        "}";
                MainActivity.paymentAmount = mAmountET.getText().toString();
            }



            Intent intentToStartDetailActivity = new Intent(this, SupporterActivity.class);
            startActivity(intentToStartDetailActivity);
        }
    }


}

package com.example.android.androidhack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhutchinson on 4/9/17.
 */

public class JsonUtils {

    public static StudentAdapter.User getUserFromJson(String json) throws JSONException{
        JSONObject userJson = new JSONObject(json);
        userJson = userJson.getJSONObject("user");

        return new StudentAdapter.User(
                userJson.getString("name"),
                userJson.getString("school"),
                userJson.getString("bio"),
                userJson.getString("image")
        );
    }

    public static List<PayActivity.MyAccount> getAccountsFromJson(String json) throws JSONException{
        JSONArray accountArray = new JSONArray(json);

        List<PayActivity.MyAccount> myAccountsList = new ArrayList<>();
        for (int i=0; i<accountArray.length(); i++){
            JSONObject account = accountArray.getJSONObject(i);
            myAccountsList.add(i, new PayActivity.MyAccount(
                    account.getString("customer_id"),
                    account.getString("nickname"),
                    account.getString("type")
            ));
        }

        return myAccountsList;
    }

    public static List<StudentAdapter.Event> getEventsFromJson(String json) throws JSONException{
        JSONObject eventsJson = new JSONObject(json);
        JSONArray eventArray = eventsJson.getJSONArray("events");

        List<StudentAdapter.Event> myEvents = new ArrayList<>();
        for (int i=0; i<eventArray.length(); i++){
            JSONObject event = eventArray.getJSONObject(i);
            myEvents.add(i, new StudentAdapter.Event(
                    event.getString("title"),
                    event.getString("details"),
                    event.getString("date"),
                    event.getString("image")
            ));
        }
        return  myEvents;
    }

    public static List<SupporterAdapter.User> getUsersFromJson(String json) throws JSONException{
        JSONObject eventsJson = new JSONObject(json);
        JSONArray eventArray = eventsJson.getJSONArray("users");

        List<SupporterAdapter.User> myUsers = new ArrayList<>();
        for (int i=0; i<eventArray.length(); i++){
            JSONObject event = eventArray.getJSONObject(i);
            myUsers.add(i, new SupporterAdapter.User(
                    event.getString("name"),
                    event.getString("school"),
                    event.getString("bio"),
                    event.getString("image"),
                    event.getString("id")
            ));
        }
        return  myUsers;
    }

}

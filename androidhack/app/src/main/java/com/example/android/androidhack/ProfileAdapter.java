package com.example.android.androidhack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProfileAdapter extends StudentAdapter {

    public ProfileAdapter(Context context) {
        super(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        if (viewType == 0){
            int layoutIdForListItem = R.layout.profile_header_visitor;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
            MyViewHolder viewHolder = new MyViewHolder(view, viewType);

            return viewHolder;
        }

        int layoutIdForListItem = R.layout.pledge_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view, viewType);

        return viewHolder;
    }


}

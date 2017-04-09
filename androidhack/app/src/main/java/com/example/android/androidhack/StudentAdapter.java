package com.example.android.androidhack;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder>{

    private User mUser;
    private List<Event> mEventList;

    private Context context;

    public StudentAdapter(Context context){
        mEventList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mUser != null){
            return 0;
        }
        return 1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        if (viewType == 0){
            int layoutIdForListItem = R.layout.profile_header;
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

    //fill in the card with the proper info
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) == 0){
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);

            holder.name.setText(mUser.name);
            holder.school.setText(mUser.school);
            holder.bio.setText(mUser.bio);
            if(!mUser.image.equals("")) Picasso.with(context).load(NetworkUtils.buildImageUrl(mUser.image)).resize(500,500).into(holder.photo);

            return;
        } else {
            int element = position;
            if (element <= 0) element = 1;
            holder.title.setText(mEventList.get(element-1).title);
            holder.details.setText(mEventList.get(element-1).details);
            holder.date.setText(mEventList.get(element-1).date);
            if (!mEventList.get(element-1).image.equals(""))
                Picasso.with(context).load(NetworkUtils.buildImageUrl(mEventList.get(element-1).image)).resize(500, 500).into(holder.image);
            else {
                holder.image.setVisibility(View.GONE);
            }
        }
    }

    //return the item count
    @Override
    public int getItemCount() {
        if (mUser == null){
            return mEventList.size();
        } else {
            return mEventList.size() + 1;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        int viewType;
        TextView name, school, bio;
        TextView title, details, date;
        ImageView photo, image;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);

            this.viewType = viewType;

            if (viewType == 0){
                name = (TextView) itemView.findViewById(R.id.name_tv);
                school = (TextView) itemView.findViewById(R.id.school_tv);
                bio = (TextView) itemView.findViewById(R.id.bio_tv);
                photo = (ImageView) itemView.findViewById(R.id.profile_pic_iv);
            }else{
                title = (TextView) itemView.findViewById(R.id.event_name);
                details = (TextView) itemView.findViewById(R.id.event_details);
                date = (TextView) itemView.findViewById(R.id.event_date);
                image = (ImageView) itemView.findViewById(R.id.event_image_iv);
            }

        }

    }

    public void setUser(User user) {
        mUser = user;
        notifyDataSetChanged();
    }

    public void setEvent(List<Event> events){
        mEventList = events;
        notifyDataSetChanged();
    }

    public static class User{
        public String name, school, bio, image;
        public User(String name, String school, String bio, String image){
            this.name = name;
            this.school = school;
            this.bio = bio;
            this.image = image;
        }
    }

    public static class Event{
        public String title, details, date, image;
        public Event(String title, String details, String date, String image){
            this.title = title;
            this.details = details;
            this.date = date;
            this.image = image;
        }
    }

}

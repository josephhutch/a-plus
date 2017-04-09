package com.example.android.androidhack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SupporterAdapter extends RecyclerView.Adapter<SupporterAdapter.MySupportViewHolder> {

    private List<User> mUserList;

    private Context context;

    private final SupporterAdapterOnClickHandler mClickHandler;

    public SupporterAdapter(Context context, SupporterAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
        mUserList = new ArrayList<>();
        this.context = context;
    }

    public interface SupporterAdapterOnClickHandler {
        void onClick(User clickedUser);
    }


    @Override
    public MySupportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.supporter_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MySupportViewHolder viewHolder = new MySupportViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MySupportViewHolder holder, int position) {
        holder.name.setText(mUserList.get(position).name);
        holder.school.setText(mUserList.get(position).school);
        holder.bio.setText(mUserList.get(position).bio);
        Picasso.with(context).load(NetworkUtils.buildImageUrl(mUserList.get(position).image)).resize(500,500).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class MySupportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, school, bio;
        ImageView image;

        public MySupportViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.user_name);
            school = (TextView) itemView.findViewById(R.id.user_school);
            bio = (TextView) itemView.findViewById(R.id.user_bio);
            image = (ImageView) itemView.findViewById(R.id.user_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            User clickedUser = mUserList.get(adapterPosition);
            mClickHandler.onClick(clickedUser);
        }

    }

    public void setUsers(List<User> users) {
        mUserList = users;
        notifyDataSetChanged();
    }

    public static class User{
        public String name, school, bio, image, id;
        public User(String name, String school, String bio, String image, String id){
            this.name = name;
            this.school = school;
            this.bio = bio;
            this.image = image;
            this.id = id;
        }
    }
}

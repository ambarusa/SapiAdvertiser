package com.example.lszlsomai.sapiadvertiser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder> {

    private Context mContext;
    private List<Ad> mAdsList;
//    RequestOptions avatarOption;
//    RequestOptions thumbnailOption;

    public AdsAdapter(Context mContext, List<Ad> mAdsList) {
        this.mContext = mContext;
        this.mAdsList = mAdsList;

        //RequestOption for Glide
//        avatarOption = new RequestOptions().centerCrop().placeholder(R.drawable.img_avatar);
//        thumbnailOption = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.ad_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.adTitle.setText(mAdsList.get(position).getTitle());
        holder.adShortDescription.setText(mAdsList.get(position).getShortDescription());
        holder.adViews.setText(mAdsList.get(position).getViews());

        //Load Images using Glide
//        Glide.with(mContext).load(mAdsList.get(position).getAvatarURL()).apply(avatarOption).into(holder.avatar);
//        Glide.with(mContext).load(mAdsList.get(position).getPostThumbnailURL()).apply(thumbnailOption).into(holder.postThumbnail);
        holder.adAvatar.setImageResource(R.drawable.img_avatar);
        holder.adThumbnail.setImageResource(R.drawable.launch_screen);
    }

    @Override
    public int getItemCount() {
        return mAdsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView adTitle;
        TextView adShortDescription;
        ImageView adAvatar;
        ImageView adThumbnail;
        TextView adViews;


        public ViewHolder(View itemView) {
            super(itemView);
            adTitle = itemView.findViewById(R.id.ad_title);
            adShortDescription = itemView.findViewById(R.id.ad_description);
            adAvatar = itemView.findViewById(R.id.ad_avatar);
            adThumbnail = itemView.findViewById(R.id.ad_thumbnail);
            adViews = itemView.findViewById(R.id.ad_views);
        }
    }
}

package com.example.lszlsomai.sapiadvertiser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdViewHolder extends RecyclerView.ViewHolder {

    TextView adTitle;
    TextView adShortDescription;
    ImageView adAvatar;
    ImageView adThumbnail;
    TextView adViews;


    public AdViewHolder(View itemView) {
        super(itemView);
        adTitle = itemView.findViewById(R.id.ad_title);
        adShortDescription = itemView.findViewById(R.id.ad_description);
        adAvatar = itemView.findViewById(R.id.ad_avatar);
        adThumbnail = itemView.findViewById(R.id.ad_thumbnail);
        adViews = itemView.findViewById(R.id.ad_views);
    }
}

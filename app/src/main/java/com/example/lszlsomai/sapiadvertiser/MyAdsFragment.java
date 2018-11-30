package com.example.lszlsomai.sapiadvertiser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

@SuppressLint("ValidFragment")
public class MyAdsFragment extends Fragment {
    private View view;
    private RecyclerView mAdsContainer;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerOptions<Ad> options;
    private FirebaseRecyclerAdapter<Ad, AdViewHolder> adapter;

    private String phoneNumber;


    public MyAdsFragment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Initialize the Recycle View
        mAdsContainer = view.findViewById(R.id.ads_container);
        mAdsContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdsContainer.setItemAnimator(new DefaultItemAnimator());

        //Get Instance from database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ads");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = mDatabase.orderByChild("phoneNumber").equalTo(phoneNumber);
        options = new FirebaseRecyclerOptions.Builder<Ad>().setQuery(query, Ad.class).build();
        adapter = new FirebaseRecyclerAdapter<Ad, AdViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final AdViewHolder holder, int position, final Ad ad) {
                String id = getRef(position).getKey();
                mDatabase.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String shortDescription = dataSnapshot.child("shortDescription").getValue().toString();
                        String views = dataSnapshot.child("views").getValue().toString();

                        holder.adTitle.setText(title);
                        holder.adShortDescription.setText(shortDescription);
                        holder.adViews.setText(views);

                        //Load Images using Glide
//        Glide.with(mContext).load(mAdsList.get(position).getAvatarURL()).apply(avatarOption).into(holder.avatar);
//        Glide.with(mContext).load(mAdsList.get(position).getPostThumbnailURL()).apply(thumbnailOption).into(holder.postThumbnail);
                        holder.adAvatar.setImageResource(R.drawable.img_avatar);
                        holder.adThumbnail.setImageResource(R.mipmap.ic_launcher);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_item, parent, false);
                return new AdViewHolder(view);
            }
        };

        mAdsContainer.setAdapter(adapter);
        adapter.startListening();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

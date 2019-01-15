package ro.sapientia.ms.sapiadvertiser.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.sapientia.ms.sapiadvertiser.Models.Ad;
import ro.sapientia.ms.sapiadvertiser.Activities.AdActivity;
import ro.sapientia.ms.sapiadvertiser.Models.GlideApp;
import ro.sapientia.ms.sapiadvertiser.R;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView mAdsContainer;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private FirebaseRecyclerOptions<Ad> options;
    private FirebaseRecyclerAdapter<Ad, AdViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Initialize the Recycle View
        mAdsContainer = view.findViewById(R.id.ads_container);
        mAdsContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdsContainer.setItemAnimator(new DefaultItemAnimator());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ads");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        options = new FirebaseRecyclerOptions.Builder<Ad>().setQuery(mDatabase, Ad.class).build();
        adapter = new FirebaseRecyclerAdapter<Ad, AdViewHolder>(options) {

            @Override
            protected void onBindViewHolder(final AdViewHolder holder, final int position, final Ad ad) {
                final String id = getRef(position).getKey();
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
                        GlideApp.with(getContext())
                                .load(mStorageRef.child("/images/" + title))
                                .into(holder.adThumbnail);
                        holder.adAvatar.setImageResource(R.drawable.img_avatar);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                holder.adLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), AdActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
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
}


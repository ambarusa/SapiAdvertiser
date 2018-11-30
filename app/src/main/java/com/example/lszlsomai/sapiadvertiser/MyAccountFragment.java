package com.example.lszlsomai.sapiadvertiser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

@SuppressLint("ValidFragment")
public class MyAccountFragment extends Fragment {

    MyAdsFragment.OnFragmentInteractionListener mCallback;

    private AppCompatImageButton mSignoutButton;
    private AppCompatImageButton mMyAdsButton;
    private User mUser;

    private TextView firstName;
    private TextView lastName;
    private TextView eMail;
    private TextView phoneNumber;
    private TextView address;

    public MyAccountFragment(User mUser) {
        this.mUser = mUser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        firstName = view.findViewById(R.id.firstNameText);
        lastName = view.findViewById(R.id.lastNameText);
        eMail = view.findViewById(R.id.emailText);
        phoneNumber = view.findViewById(R.id.phoneNumberText);
        address = view.findViewById(R.id.addressText);

        firstName.setText(mUser.getFirstName());
        lastName.setText(mUser.getLastName());
        eMail.setText(mUser.getEmail());
        phoneNumber.setText(mUser.getPhoneNumber());
        address.setText(mUser.getAddress());

        mSignoutButton = view.findViewById(R.id.signout_button);
        mSignoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mMyAdsButton = view.findViewById(R.id.my_ads_button);
        mMyAdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyAdsFragment(mUser.getPhoneNumber())).commit();
            }
        });
        return view;
    }

}

package com.example.lszlsomai.sapiadvertiser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

@SuppressLint("ValidFragment")
public class MyProfileFragment extends Fragment {

    private AppCompatImageButton mSignOutBtn;

    private TextView firstName;
    private TextView lastName;
    private TextView eMail;
    private TextView phoneNumber;
    private TextView address;

    private User mUser;

    public MyProfileFragment(User user) {
        this.mUser = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        firstName = view.findViewById(R.id.firstNameText);
        lastName = view.findViewById(R.id.lastNameText);
        eMail = view.findViewById(R.id.emailText);
        phoneNumber = view.findViewById(R.id.phoneNumberText);
        address = view.findViewById(R.id.addressText);

        SetUserData();

        mSignOutBtn = (AppCompatImageButton) view.findViewById(R.id.signoutButton);
        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void SetUserData() {
        firstName.setText(mUser.getFirstName());
        lastName.setText(mUser.getLastName());
        eMail.setText(mUser.getEmail());
        phoneNumber.setText(mUser.getPhoneNumber());
        address.setText(mUser.getAddress());
    }

}

package com.example.lszlsomai.sapiadvertiser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("ValidFragment")
public class CreateAdFragment extends Fragment {

    private EditText adTitle;
    private EditText adPhone;
    private EditText adLocation;
    private EditText shortDescr;
    private EditText longDescr;
    private TextView errorText;
    private Button createAdBtn;
    private User currentUser;
    private FirebaseFirestore db;
    private View view;

    @SuppressLint("ValidFragment")
    public CreateAdFragment(User user) {
        this.currentUser = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_ad, container, false);

        db = FirebaseFirestore.getInstance();

        adTitle = view.findViewById(R.id.adTitle);
        shortDescr = view.findViewById(R.id.ad_shortDescr);
        longDescr = view.findViewById(R.id.ad_longDescr);
        adPhone = view.findViewById(R.id.ad_phone);
        adLocation = view.findViewById(R.id.ad_Location);
        createAdBtn = view.findViewById(R.id.createAdBtn);
        errorText = view.findViewById(R.id.errorText);

        createAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( validateForm() == false ) {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("Please, fill out all fields.");
                    return;
                }

                Map<String, Object> ad = new HashMap<>();

                ad.put("title", adTitle.getText().toString());
                ad.put("shortDescription", shortDescr.getText().toString());
                ad.put("longDescription", longDescr.getText().toString());
                ad.put("phoneNumber", adPhone.getText().toString());
                ad.put("address", adLocation.getText().toString());

                addAdvertismentToFirestore(ad);

            }
        });

        return view;
    }

    private boolean validateForm() {
        boolean valid = true;

        String title = adTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            adTitle.setError("Required.");
            valid = false;
        } else {
            adTitle.setError(null);
        }

        String shortDescText = shortDescr.getText().toString();
        if (TextUtils.isEmpty(shortDescText)) {
            shortDescr.setError("Required.");
            valid = false;
        } else {
            shortDescr.setError(null);
        }

        String longDescText = longDescr.getText().toString();
        if (TextUtils.isEmpty(longDescText)) {
            longDescr.setError("Required.");
            valid = false;
        } else {
            longDescr.setError(null);
        }

        String phoneNumber = adPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            adPhone.setError("Required.");
            valid = false;
        } else {
            adPhone.setError(null);
        }

        String location = adLocation.getText().toString();
        if (TextUtils.isEmpty(location)) {
            adLocation.setError("Required.");
            valid = false;
        } else {
            adLocation.setError(null);
        }

        return valid;
    }

    private void addAdvertismentToFirestore(Map<String, Object> ad) {
        db.collection("ads")
                .add(ad)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        // Add this advertisment's ID for the user's Ads document

                        addAdvertismentIDforUser(documentReference.getId());

                        Toast.makeText(getActivity(), "Advertisment created.", Toast.LENGTH_SHORT).show();
                        Log.d("ADCreation", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error: Can't create the ad.", Toast.LENGTH_SHORT).show();
                        Log.w("ADCreation", "Error adding document", e);
                    }
                });
    }

    private void addAdvertismentIDforUser(String ID) {
        Map<String, Object> emptyDoc = new HashMap<>();

        emptyDoc.put("empty", true);

        db.collection("users").document(currentUser.getPhoneNumber()).collection("myAds").document(ID)
                .set(emptyDoc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.w("ADCreation", "Auto-generated ID added for user's myAds document.");
                        getFragmentManager().beginTransaction().replace(R.id.container, new tasks()).commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADCreation", "Failed to add auto-generated ID for user's myAds document.");
                    }
                });
    }

}

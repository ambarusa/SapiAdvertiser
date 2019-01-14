package ro.sapientia.ms.sapiadvertiser.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ro.sapientia.ms.sapiadvertiser.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class CreateAdFragment extends Fragment {

    private EditText adTitle;
    private EditText adPhoneNumber;
    private EditText adEmail;
    private EditText adAddress;
    private EditText adShortDescription;
    private EditText adLongDescription;
    private Button createAdBtn;
    private Button chooseBtn;
    private ImageView uploadedImg;

    private final int SELECT_IMAGE_REQUEST = 71;
    private Uri filePath;

    private View view;

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_ad, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ads");

        adTitle = view.findViewById(R.id.adTitle);
        adShortDescription = view.findViewById(R.id.ad_shortDescr);
        adLongDescription = view.findViewById(R.id.ad_longDescr);
        adPhoneNumber = view.findViewById(R.id.ad_phone);
        adEmail = view.findViewById(R.id.ad_email);
        adAddress = view.findViewById(R.id.ad_Location);
        createAdBtn = view.findViewById(R.id.createAdBtn);
        chooseBtn = view.findViewById(R.id.chooseBtn);
        uploadedImg = view.findViewById(R.id.uploadedImg);

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE_REQUEST ); /*Pick Image Request*/
            }
        });

        createAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    Toast.makeText(getActivity(), "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> adMap = new HashMap<>();

                adMap.put("title", adTitle.getText().toString());
                adMap.put("shortDescription", adShortDescription.getText().toString());
                adMap.put("longDescription", adLongDescription.getText().toString());
                adMap.put("phoneNumber", adPhoneNumber.getText().toString());
                adMap.put("email", adEmail.getText().toString());
                adMap.put("address", adAddress.getText().toString());
                adMap.put("views", "0");

                mDatabase.push().updateChildren(adMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            adTitle.getText().clear();
                            adShortDescription.getText().clear();
                            adLongDescription.getText().clear();
                            adPhoneNumber.getText().clear();
                            adAddress.getText().clear();
                            Toast.makeText(getActivity(), "Ad created successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "There was an error, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });

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

        String shortDescText = adShortDescription.getText().toString();
        if (TextUtils.isEmpty(shortDescText)) {
            adShortDescription.setError("Required.");
            valid = false;
        } else {
            adShortDescription.setError(null);
        }

        String longDescText = adLongDescription.getText().toString();
        if (TextUtils.isEmpty(longDescText)) {
            adLongDescription.setError("Required.");
            valid = false;
        } else {
            adLongDescription.setError(null);
        }

        String phoneNumber = adPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            adPhoneNumber.setError("Required.");
            valid = false;
        } else {
            adPhoneNumber.setError(null);
        }

        String email = adEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            adEmail.setError("Required.");
            valid = false;
        } else {
            adEmail.setError(null);
        }

        String location = adAddress.getText().toString();
        if (TextUtils.isEmpty(location)) {
            adAddress.setError("Required.");
            valid = false;
        } else {
            adAddress.setError(null);
        }
        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                uploadedImg.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            ImageView uploadDoneImg = view.findViewById(R.id.uploadDoneImg);
            uploadDoneImg.setVisibility(View.VISIBLE);
        }
    }
}

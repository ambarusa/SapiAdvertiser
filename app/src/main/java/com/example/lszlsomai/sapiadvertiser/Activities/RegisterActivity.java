package com.example.lszlsomai.sapiadvertiser.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lszlsomai.sapiadvertiser.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mEmail;
    private EditText mFname;
    private EditText mLname;
    private EditText mAddress;
    private TextView mErrorText;
    private Button mSignUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.reg_email);
        mFname = (EditText) findViewById(R.id.reg_first_name);
        mLname = (EditText) findViewById(R.id.reg_last_name);
        mAddress = (EditText) findViewById(R.id.reg_address);
        mErrorText = (TextView) findViewById(R.id.errorText);
        mSignUpBtn = (Button) findViewById(R.id.buttonSignUp);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! validateForm()) {
                    mErrorText.setText("Fill out all fields!");
                    return;
                }
                String email = mEmail.getText().toString();
                String fname = mFname.getText().toString();
                String lname = mLname.getText().toString();
                String address = mAddress.getText().toString();

                Intent intent = getIntent();
                String phoneNumber = intent.getStringExtra("phoneNumber");
                Map<String, Object> user = new HashMap<>();

                user.put("FirstName", fname);
                user.put("LastName", lname);
                user.put("Email", email);
                user.put("Address", address);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").document(phoneNumber)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mErrorText.setText("Failed to create user account.");
                            }
                        });

            }
        });

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String firstName = mFname.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            mFname.setError("Required.");
            valid = false;
        } else {
            mFname.setError(null);
        }

        String lastName = mLname.getText().toString();
        if (TextUtils.isEmpty(lastName)) {
            mLname.setError("Required.");
            valid = false;
        } else {
            mLname.setError(null);
        }

        String address = mAddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            mAddress.setError("Required.");
            valid = false;
        } else {
            mAddress.setError(null);
        }

        return valid;
    }
}

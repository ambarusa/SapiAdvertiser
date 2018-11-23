package com.example.lszlsomai.sapiadvertiser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText mPhoneNumber;
    private EditText mVerificationCode;
    private TextView mErrorText;
    private Button mLoginBtn;

    private int btnType = 0; // 0 = phone number verification function
                            //  1 = code verify function

    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // UI elements
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumberEditText);
        mVerificationCode = (EditText) findViewById(R.id.verificationCode);
        mLoginBtn = (Button) findViewById(R.id.buttonSignIn);
        mErrorText = (TextView) findViewById(R.id.errorText);

        // When Sign In button pressed listener
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnType == 0) {

                    mLoginBtn.setEnabled(false);
                    mPhoneNumber.setEnabled(false);

                    // Save the phone number
                    String phoneNumber = mPhoneNumber.getText().toString();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            // Phone number to verify
                            phoneNumber,
                            // Timeout duration
                            60,
                            // Unit of timeout
                            TimeUnit.SECONDS,
                            // Activity (for callback binding)
                            LoginActivity.this,
                            // OnVerificationStateChangedCallbacks
                            mCallbacks);
                } else {
                    mLoginBtn.setEnabled(false);
                    String verificationCode = mVerificationCode.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                mErrorText.setVisibility(View.VISIBLE);
                mErrorText.setText("There was some error in logging in.");
                mPhoneNumber.setEnabled(true);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
               // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                btnType = 1; // change the function of the btn
                mLoginBtn.setEnabled(true);
                mVerificationCode.setVisibility(View.VISIBLE);
                mLoginBtn.setText("Verify code");
            }
        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();

                            Intent authIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(authIntent);
                            finish(); // onDestroy allapot

                        } else {
                            // Sign in failed, display a message and update the UI
                            mErrorText.setVisibility(View.VISIBLE);
                            mErrorText.setText("There was some error in logging in.");
                            mPhoneNumber.setEnabled(true);
                            btnType = 0;
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if ( currentUser != null ) {
//            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(mainIntent);
//            finish();
//        }

    }

    public void goToRegistration(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


}

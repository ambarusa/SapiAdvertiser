package ro.sapientia.ms.sapiadvertiser.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ro.sapientia.ms.sapiadvertiser.Models.GlideApp;
import ro.sapientia.ms.sapiadvertiser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AdActivity extends AppCompatActivity {

    private TextView adTitle;
    private ImageView adImage;
    private TextView adLongDescription;
    private TextView adEmail;
    private TextView adPhoneNumber;
    private TextView adAddress;
    private ImageButton adReport;
    private ImageButton adShare;
    private ImageView adAvatar;
    private TextView adCreator;

    private DatabaseReference mDatabase, reportsRef;
    private StorageReference mStorageRef;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        //Remove the titlebar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_ad);

        adTitle = findViewById(R.id.opened_ad_title);
        adImage = findViewById(R.id.opened_ad_image);
        adLongDescription = findViewById(R.id.opened_ad_longDescription);
        adEmail = findViewById(R.id.opened_ad_email);
        adPhoneNumber = findViewById(R.id.opened_ad_phoneNumber);
        adAddress = findViewById(R.id.opened_ad_address);
        adReport = findViewById(R.id.opened_ad_report);
        adShare = findViewById(R.id.opened_ad_share);
        adAvatar = findViewById(R.id.opened_ad_avatar);
        adCreator = findViewById(R.id.opened_ad_creator);

        //Get Instance from database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ads");
        reportsRef = FirebaseDatabase.getInstance().getReference().child("Reports");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Link with Firestore
        db = FirebaseFirestore.getInstance();

        // Share button
        adShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check this out. Very good ad.");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Choose app"));
            }
        });

        // Report button
        adReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a map for report
                Map<String, Object> report = new HashMap<>();

                report.put("ID", getIntent().getStringExtra("id"));
                report.put("Text", "Reported.");

                // Push to realtime-database
                reportsRef.push().setValue(report, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            // Error in report
                            Toast.makeText(getBaseContext(), "There was an error in report. Try again.", Toast.LENGTH_LONG).show();
                        } else {
                            // Reported successsfully
                            Toast.makeText(getBaseContext(), "Reported", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String id = getIntent().getStringExtra("id");

        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title = dataSnapshot.child("title").getValue().toString();
                String longDescription = dataSnapshot.child("longDescription").getValue().toString();
                String phoneNumber = dataSnapshot.child("phoneNumber").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();

                adTitle.setText(title);
                adLongDescription.setText(longDescription);
                adPhoneNumber.setText(phoneNumber);
                adEmail.setText(email);
                adAddress.setText(address);
                GetCreatorName(phoneNumber);

                adAvatar.setImageResource(R.drawable.img_avatar);
                GlideApp.with(getApplicationContext())
                        .load(mStorageRef.child("/images/" + title))
                        .into(adImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child(id).child("views").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value = Integer.parseInt(dataSnapshot.getValue().toString());
                value++;
                dataSnapshot.getRef().setValue(String.valueOf(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void GetCreatorName(final String phoneNumber) {
        final DocumentReference docRef = db.collection("users").document(phoneNumber);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String firstName = document.getString("FirstName");
                        adCreator.setText(firstName);
                    } else {
                        Log.d("FIRESTORE", "No document exists");
                    }
                } else {
                    Log.d("FIRESTORE", String.valueOf(task.getException()));
                }
            }
        });
    }
}

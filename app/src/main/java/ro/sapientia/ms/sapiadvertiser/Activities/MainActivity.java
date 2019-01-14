package ro.sapientia.ms.sapiadvertiser.Activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import ro.sapientia.ms.sapiadvertiser.Fragments.CreateAdFragment;
import ro.sapientia.ms.sapiadvertiser.Fragments.HomeFragment;
import ro.sapientia.ms.sapiadvertiser.Fragments.MyAccountFragment;
import ro.sapientia.ms.sapiadvertiser.Fragments.MyAdsFragment;
import ro.sapientia.ms.sapiadvertiser.R;
import ro.sapientia.ms.sapiadvertiser.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
        implements MyAdsFragment.OnFragmentInteractionListener {

    private User mUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        //Remove the titlebar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);

        //Link with Firestore
        db = FirebaseFirestore.getInstance();

        //Get the informations about the user
        GetUserDataFromFirestore();

        //Initialize Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener);

        //Set Starting Fragment
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private void GetUserDataFromFirestore() {
        final String phoneNumber = getIntent().getStringExtra("phoneNumber");
        final DocumentReference docRef = db.collection("users").document(phoneNumber);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String firstName = document.getString("FirstName");
                        String lastName = document.getString("LastName");
                        String eMail = document.getString("Email");
                        String address = document.getString("Address");

                        mUser = new User(firstName, lastName, eMail, phoneNumber, address);
                    }
                    else {
                        Log.d("FIRESTORE", "No document exists");
                    }
                }
                else {
                    Log.d("FIRESTORE", String.valueOf(task.getException()));
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_create:
                            selectedFragment = new CreateAdFragment();
                            break;
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_my_profile:
                            selectedFragment = new MyAccountFragment(mUser);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

package dk.easj.ianx0156.therealmandatoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;

    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button buttonAddReservation;
    private Button buttonShowReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = firebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()== null){
            finish();
            startActivity(new Intent(this, LoginActvity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonShowReservation =(Button) findViewById(R.id.ButtonShowReservations);

        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Signed in as: " + user.getEmail());








        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome "+ user.getEmail());
        buttonLogout =(Button) findViewById(R.id.toolbarLogOutButton);

        buttonAddReservation = findViewById(R.id.ButtonAddReservation);

        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    if (v == buttonLogout)
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActvity.class
        ));
    }

    }

    public void addReservationOnclick(View view) {
        if (view == buttonAddReservation)
        {
            startActivity(new Intent(this, AddReservationActivity.class));
        }


    }

    public void ShowReservationOnclick(View view) {
        if (view == buttonShowReservation )
        {
            startActivity(new Intent(this, RoomReservationActivity.class));
        }
    }
}

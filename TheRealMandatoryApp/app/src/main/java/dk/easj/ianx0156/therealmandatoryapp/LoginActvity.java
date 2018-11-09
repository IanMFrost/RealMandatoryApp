package dk.easj.ianx0156.therealmandatoryapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActvity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);

        firebaseAuth = firebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        buttonSignin = (Button) findViewById(R.id.buttonLoginRegister);
        textViewSignin = (TextView) findViewById(R.id.textViewLogin);


        buttonSignin.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please enter email",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
            // password is empty
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    // start the profile acitvity
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSignin) {
            userLogin();
        }
        if(v == textViewSignin){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }


}

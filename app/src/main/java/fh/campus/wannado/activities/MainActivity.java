package fh.campus.wannado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fh.campus.wannado.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText emailId, password, username;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore firestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        username = findViewById(R.id.editTextUsername);
        emailId = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btnSignUp = findViewById(R.id.buttonSignUp);
        tvSignIn = findViewById(R.id.textViewSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String email  = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter your Email");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (uname.isEmpty()){
                    username.setError("Please enter your Username");
                    username.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty() && uname.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty() && uname.isEmpty()){
                    Toast.makeText(MainActivity.this, "Email and Username are empty!", Toast.LENGTH_SHORT).show();
                }
                else if (uname.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Username and Email are empty!", Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Password and Email are empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                FirebaseUser fuser = mFirebaseAuth.getCurrentUser();
                                fuser.sendEmailVerification()
                                        .addOnSuccessListener(e -> Toast.makeText(MainActivity.this, "Verification email has been sent", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Log.d(TAG, "onFailure: Email not sent" + e.getMessage()));

                                userID = mFirebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("username", uname);
                                user.put("email", email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user profile is created for " + userID);
                                    }
                                });
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                }
            }
        });
        tvSignIn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }

}
package fh.campus.wannado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fh.campus.wannado.R;

public class ThreadDetailsActivity extends AppCompatActivity {

    TextView titleDb, MessageDb;
    EditText comment;
    Button buttonComment;
    FirebaseAuth mFirebaseAuth;
    String userID;
    FirebaseFirestore  firestore;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onclickthread_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        titleDb = findViewById(R.id.textTitleFromDb);
        MessageDb = findViewById(R.id.textMessageFromDb);
        comment = findViewById(R.id.editTextComment);
        buttonComment = findViewById(R.id.buttonPostComment);

        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insertedComment = comment.getText().toString();

                if(insertedComment.isEmpty()){
                    comment.setError("Please enter an comment");
                    comment.requestFocus();
                }
                else if (!insertedComment.isEmpty()){
                    userID = mFirebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = firestore.collection("thread").document();
                    Map<String, Object> thread = new HashMap<>();
                    thread.put("comment", comment);
                    thread.put("commentedUserID", userID);
                    documentReference.set(thread).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "onSuccess: user comment has been stored"+ userID);
                        }
                    });
                    startActivity(new Intent(ThreadDetailsActivity.this, HomeActivity.class));
                }
                else {
                    Toast.makeText(ThreadDetailsActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void setPostInfo(){

    }
}

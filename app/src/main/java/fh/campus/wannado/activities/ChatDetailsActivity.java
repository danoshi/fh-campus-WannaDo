package fh.campus.wannado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fh.campus.wannado.R;
import fh.campus.wannado.collections.chats.ChatCollection;
import fh.campus.wannado.collections.chats.ChatDocument;

public class ChatDetailsActivity  extends AppCompatActivity {

    TextView textViewMessage, textViewDate, textViewSender;
    EditText editTextAnswer;
    Button buttonSend;
    ArrayList<ChatDocument> items;
    String userID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdetails);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        items = new ArrayList<>();
        textViewMessage = findViewById(R.id.textViewMessageDB);
        textViewDate = findViewById(R.id.textViewDate);
        textViewSender = findViewById(R.id.textViewSender);
        editTextAnswer = findViewById(R.id.editTextTAnswer);
        buttonSend = findViewById(R.id.buttonSend);


        ChatCollection.getUserChats(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                    ChatDocument chatDocument = queryDocumentSnapshot.toObject(ChatDocument.class);
                    items.add(chatDocument);
                    textViewMessage.setText(chatDocument.getLastMessage());
                    textViewDate.setText((int) chatDocument.getLastMessageTime());
                    textViewSender.setText(chatDocument.getParticipantsID().get(0));
                }
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = editTextAnswer.getText().toString();

                if(answer.isEmpty()){
                    editTextAnswer.setError("Please enter your answer");
                    editTextAnswer.requestFocus();
                }
                else if(!answer.isEmpty()){
                    userID = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = firestore.collection("chat").document(userID);
                    Map<String, Object> answerToMessage = new HashMap<>();
                    answerToMessage.put("answer", answer);
                    documentReference.set(answerToMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "onSuccess: user message is send out ");
                        }
                    });
                }
                else {
                    Toast.makeText(ChatDetailsActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

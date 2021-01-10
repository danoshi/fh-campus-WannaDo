package fh.campus.wannado.activities;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fh.campus.wannado.R;
import fh.campus.wannado.collections.chats.ChatCollection;
import fh.campus.wannado.collections.chats.ChatDocument;

public class ChatDetailsActivity  extends AppCompatActivity {

    TextView textViewMessage, textViewDate, textViewSender;
    ArrayList<ChatDocument> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdetails);

        items = new ArrayList<>();
        textViewMessage = findViewById(R.id.textViewMessageDB);
        textViewDate = findViewById(R.id.textViewDate);
        textViewSender = findViewById(R.id.textViewSender);
        ChatCollection.getUserChats(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                    ChatDocument chatDocument = queryDocumentSnapshot.toObject(ChatDocument.class);
                    items.add(chatDocument);
                    textViewMessage.setText(chatDocument.getLastMessage());
                    //textViewDate.setText(chatDocument.getLastMessageTime());
                    textViewSender.setText(chatDocument.getParticipantsID().get(0));
                }
            }
        });
    }
}

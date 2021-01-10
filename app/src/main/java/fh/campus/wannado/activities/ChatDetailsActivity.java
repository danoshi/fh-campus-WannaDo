package fh.campus.wannado.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.QuerySnapshot;

import fh.campus.wannado.R;
import fh.campus.wannado.collections.chats.ChatCollection;
import fh.campus.wannado.collections.chats.ChatDocument;

public class ChatDetailsActivity  extends AppCompatActivity {

    TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdetails);

        textViewMessage = findViewById(R.id.textViewMessageDB);
        ChatCollection.getUserChats(task -> {
            if(task.isSuccessful()){
                QuerySnapshot queryDocumentSnapshot = task.getResult();
                ChatDocument chatDocument = queryDocumentSnapshot.toObjects(ChatDocument.class);
                textViewMessage.setText(chatDocument.getLastMessage());
            }
        });
    }
}

package fh.campus.wannado.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Collections;
import java.util.List;

import fh.campus.wannado.collections.chats.UserInfo;
import fh.campus.wannado.collections.messages.Message;
import fh.campus.wannado.collections.messages.MessagesCollection;
import fh.campus.wannado.collections.messages.MessagesDocument;
import fh.campus.wannado.databinding.ChatdetailsActivityBinding;
import fh.campus.wannado.fragments.home.adapters.MessagesAdapter;

public class ChatDetailsActivity extends AppCompatActivity {

    private ChatdetailsActivityBinding binding;
    private List<Message> messages = Collections.emptyList();
    private String chatID;
    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatdetailsActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        chatID = getIntent().getStringExtra("CHAT_ID");
        messagesAdapter = new MessagesAdapter(this, messages);

        initRecyclerView();
        MessagesCollection.getChatMessages(this::setMessages, chatID);
        setContentView(view);
        binding.sendButton.setOnClickListener(e -> sendMessage());
    }


    public void initRecyclerView() {
        RecyclerView recyclerView = binding.messagesRecyclerview;
        recyclerView.setAdapter(messagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setMessages(Task<DocumentSnapshot> task){
        MessagesDocument messagesDocument;
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                messagesDocument = document.toObject(MessagesDocument.class);
                messages = messagesDocument.getMessages();
                messagesAdapter.setMessages(messages);
            }
        }
    }

    public void sendMessage() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        messages.add(Message.builder()
                .content(binding.textBar.getText().toString())
                .sender(UserInfo.builder()
                        .id(userID)
                        .nickname(email)
                        .build())
                .build());
        MessagesCollection.saveMessages(chatID, new MessagesDocument(messages));
        messagesAdapter.setMessages(messages);
    }

}

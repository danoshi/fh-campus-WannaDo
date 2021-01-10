package fh.campus.wannado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import fh.campus.wannado.R;
import fh.campus.wannado.collections.chats.ChatCollection;
import fh.campus.wannado.collections.comment.CommentCollection;
import fh.campus.wannado.collections.comment.CommentDocument;
import fh.campus.wannado.collections.post.PostDocument;

public class ThreadDetailsActivity extends AppCompatActivity {

    TextView titleDb, MessageDb, commentsFromDb;
    EditText comment;
    Button buttonComment, buttonChat;
    FirebaseAuth mFirebaseAuth;
    String userID;
    FirebaseFirestore firestore;
    PostDocument postDocument;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onclickthread_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        titleDb = findViewById(R.id.textTitleFromDb);
        MessageDb = findViewById(R.id.textMessageFromDb);
        comment = findViewById(R.id.editTextComment);
        buttonComment = findViewById(R.id.buttonPostComment);
        commentsFromDb = findViewById(R.id.textViewCommentsFromDb);
        buttonChat = findViewById(R.id.start_chat);

        postDocument = (PostDocument) getIntent().getExtras().get("THREAD");


        titleDb.setText(postDocument.getTitle());
        MessageDb.setText(postDocument.getMessage());
        CommentCollection.getThreadComments(e -> {
            StringBuilder sb = new StringBuilder();
            for (QueryDocumentSnapshot document : e.getResult()) {
                sb.append(document.toObject(CommentDocument.class).toString());
            }
            setComments(sb.toString());
        }, postDocument.getThreadID());

        buttonComment.setOnClickListener(v -> postComment());
        buttonChat.setOnClickListener(v -> setStartChatButton());

    }

    public void setComments(String comments) {
        commentsFromDb.setText(comments);
    }


    public void postComment() {
        String insertedComment = comment.getText().toString();
        if (insertedComment.isEmpty()) {
            comment.setError("Please enter an comment");
            comment.requestFocus();
        } else if (!insertedComment.isEmpty()) {
            userID = mFirebaseAuth.getCurrentUser().getUid();
            CommentCollection.saveThreadComments(CommentDocument.builder()
                    .comment(comment.getText().toString())
                    .threadID(postDocument.getThreadID())
                    .userID(userID)
                    .build()
            );
            onBackPressed();
        }
    }

    public void setStartChatButton() {
//        ChatCollection.getChatWithUser(task -> {
//            if (task.isSuccessful() && !task.getResult().isEmpty()) {
//                    String chatID = task.getResult().getDocuments().get(0).getId();
//                    Intent intent;
//                    intent = new Intent(this, ChatDetailsActivity.class);
//                    intent.putExtra("CHAT_ID", chatID);
//                    startActivity(intent);
//            }else{
//                startNewChat();
//            }
//        }, postDocument.getUserID());
        startNewChat();
    }

    private void startNewChat() {
        userID = mFirebaseAuth.getCurrentUser().getUid();
        String chatID = ChatCollection.createNewChat(userID, postDocument.getUserID());
        Intent intent;
        intent = new Intent(this, ChatDetailsActivity.class);
        intent.putExtra("CHAT_ID", chatID);
        startActivity(intent);
    }

}

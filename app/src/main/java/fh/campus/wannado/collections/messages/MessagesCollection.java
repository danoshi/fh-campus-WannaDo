package fh.campus.wannado.collections.messages;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MessagesCollection {

    private final static String COLLECTION_NAME = "messages";
    public final static String CHAT_FIELD = "chatID";
    public final static String MESSAGES_FIELD = "messages";

    public final static String SENDER_FIELD = "senderID";
    public final static String TIMESTAMP_FIELD = "timestamp";
    public final static String CONTENT_FIELD = "content";

    public void getCurrentMessage(OnCompleteListener<QuerySnapshot> onCompleteListener){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore.collection(COLLECTION_NAME)
                .whereArrayContains("senderID", userID)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }
}

package fh.campus.wannado.collections.messages;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import fh.campus.wannado.collections.post.PostDocument;

public class MessagesCollection {

    private final static String COLLECTION_NAME = "messages";
    public final static String CHAT_FIELD = "chatID";
    public final static String MESSAGES_FIELD = "messages";

    public final static String SENDER_FIELD = "senderID";
    public final static String TIMESTAMP_FIELD = "timestamp";
    public final static String CONTENT_FIELD = "content";

    public static void getCurrentMessage(OnCompleteListener<QuerySnapshot> onCompleteListener, String chatID){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(COLLECTION_NAME)
                .whereEqualTo("chatID", chatID)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

}

package fh.campus.wannado.collections.chats;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ChatCollection {
    private final static String COLLECTION_NAME = "chat";
    public final static String USER1_FIELD = "user1";
    public final static String USER2_FIELD = "user2";

    public final static String LAST_MESSAGE_FIELD = "lastMessage";
    public final static String TIMESTAMP_FIELD = "lastMessageTime";

    //// w onCompleteListener document.toObject(ChatDocument.class)
    public void getUserChats(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore.collection(COLLECTION_NAME)
                .whereArrayContains("participantsID", userID)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }



}

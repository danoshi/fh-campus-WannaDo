package fh.campus.wannado.collections.messages;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import fh.campus.wannado.collections.comment.CommentDocument;
import fh.campus.wannado.collections.post.PostDocument;

public class MessagesCollection {

    private final static String COLLECTION_NAME = "messages";
    public final static String MESSAGES_FIELD = "messages";


    public static void getChatMessages(OnCompleteListener<DocumentSnapshot> onCompleteListener, String chatID){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(COLLECTION_NAME)
                .document(chatID)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }


    public static void saveMessages(String chatID, MessagesDocument document){
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document();
            Map<String, Object> message = new HashMap<>();
            message.put(MESSAGES_FIELD, document);
            documentReference.set(message);
    }

}

package fh.campus.wannado.collections.users;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostCollection {
    private final static String COLLECTION_NAME = "thread";
    public final static String USERNAME_FIELD = "username";
    public final static String TITLE_FIELD = "title";
    public final static String MESSAGE_FIELD = "message";

    public static void getCurrentUser(OnCompleteListener<DocumentSnapshot> onCompleteListener){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getById(userId, onCompleteListener);
    }
    public static void getById(String userId, OnCompleteListener<DocumentSnapshot> onCompleteListener){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(userId);
        documentReference.get().addOnCompleteListener(onCompleteListener);
    }
    public static PostDocument getUserMessage(DocumentSnapshot documentSnapshot){
        return PostDocument.builder()
                .username(documentSnapshot.getString(USERNAME_FIELD))
                .title(documentSnapshot.getString(TITLE_FIELD))
                .message(documentSnapshot.getString(MESSAGE_FIELD))
                .build();
    }
}

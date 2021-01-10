package fh.campus.wannado.collections.post;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PostCollection {
    private final static String COLLECTION_NAME = "thread";
    public final static String USERNAME_FIELD = "userID";
    public final static String TITLE_FIELD = "title";
    public final static String MESSAGE_FIELD = "message";


    public static void getAllPosts(OnCompleteListener<QuerySnapshot> onCompleteListener){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
    }

    public static PostDocument postOf(QueryDocumentSnapshot queryDocumentSnapshot){
        return PostDocument.builder()
                .userID(queryDocumentSnapshot.getString(USERNAME_FIELD))
                .threadID(queryDocumentSnapshot.getId())
                .title(queryDocumentSnapshot.getString(TITLE_FIELD))
                .message(queryDocumentSnapshot.getString(MESSAGE_FIELD))
                .build();
    }
    public static void getCurrentUserPosts(OnCompleteListener<QuerySnapshot> onCompleteListener){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore.collection(COLLECTION_NAME).whereEqualTo("userID", userID).get().addOnCompleteListener(onCompleteListener);
    }
}

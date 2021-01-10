package fh.campus.wannado.collections.comment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CommentCollection {

    private final static String COLLECTION_NAME = "comments";
    public final static String THREAD_ID_FIELD = "threadID";
    public final static String MESSAGE_FIELD = "comment";
    public final static String USER_ID_FIELD = "userID";


    public static void getThreadComments(OnCompleteListener<QuerySnapshot> onCompleteListener, String threadID){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(COLLECTION_NAME)
                .whereEqualTo(THREAD_ID_FIELD, threadID)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    public static void saveThreadComments(CommentDocument commentDocument){
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document();
        Map<String, Object> comment = new HashMap<>();
        comment.put(THREAD_ID_FIELD, commentDocument.getThreadID());
        comment.put(MESSAGE_FIELD, commentDocument.getComment());
        comment.put(USER_ID_FIELD, commentDocument.getUserID());

        documentReference.set(comment);
    }

}

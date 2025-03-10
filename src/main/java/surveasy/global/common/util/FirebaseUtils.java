package surveasy.global.common.util;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;
import surveasy.domain.user.exception.UserNotFound;
import surveasy.domain.user.vo.FirebaseUserVo;

import java.util.concurrent.ExecutionException;

@Component
public class FirebaseUtils {
    private static final String USER_COLLECTION_NAME = "userData";

    public FirebaseUserVo getFirebaseUserVo(String email) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<DocumentSnapshot> future = db.collection(USER_COLLECTION_NAME).document(email).get();
            DocumentSnapshot documentSnapshot = future.get();
            if(documentSnapshot.exists()) {
                return FirebaseUserVo.of(
                        documentSnapshot.getString("name"),
                        documentSnapshot.getString("email"),
                        documentSnapshot.getString("phoneNumber")
                );
            } else {
                throw UserNotFound.EXCEPTION;
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

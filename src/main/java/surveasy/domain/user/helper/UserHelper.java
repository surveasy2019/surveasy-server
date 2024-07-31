package surveasy.domain.user.helper;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.exception.MismatchPassword;
import surveasy.domain.panel.exception.PanelNotFoundFB;
import surveasy.domain.user.domain.InflowPath;
import surveasy.domain.user.domain.User;
import surveasy.domain.user.domain.UserIdentity;
import surveasy.domain.user.dto.request.UserSignInRequestDto;
import surveasy.domain.user.repository.UserRepository;
import surveasy.domain.user.vo.FirebaseUserInfoVo;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
public class UserHelper {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String USER_COLLECTION_NAME = "userData";

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User addExistingUserIfNeed(UserSignInRequestDto signInRequestDto) {
        User user = findUserByEmailOrNull(signInRequestDto.email());
        if(user == null) {
            user = createUserFromFirestore(signInRequestDto);
            return saveUser(user);
        }
        if(!matchesPassword(signInRequestDto.password(), user.getPassword())) {
            throw MismatchPassword.EXCEPTION;
        }
        return user;
    }

    private User createUserFromFirestore(UserSignInRequestDto signInRequestDto) {
        FirebaseUserInfoVo firebaseUserInfoVo = getFirebaseUserInfoVo(signInRequestDto);
        return User.createUserFromFirebase(firebaseUserInfoVo);
    }

    private User findUserByEmailOrNull(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private FirebaseUserInfoVo getFirebaseUserInfoVo(UserSignInRequestDto signInRequestDto) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<DocumentSnapshot> future = db.collection(USER_COLLECTION_NAME).document(signInRequestDto.email()).get();
            DocumentSnapshot documentSnapshot = future.get();

            if(documentSnapshot.exists()) {
                return FirebaseUserInfoVo.of(
                        documentSnapshot.getString("name"),
                        documentSnapshot.getString("email"),
                        encodePassword(signInRequestDto.password()),
                        documentSnapshot.getString("phoneNumber"),
                        UserIdentity.ofFirebase(documentSnapshot.getString("identity")),
                        InflowPath.ofFirebase(documentSnapshot.getString("funnel")),
                        documentSnapshot.getString("funnel_detail"),
                        documentSnapshot.get("point_current", Integer.class),
                        documentSnapshot.get("point_total", Integer.class)
                );
            } else {
                throw PanelNotFoundFB.EXCEPTION;
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public User findUserByIdOrThrow(Long id) {
        System.out.println("__________________________ " + id);
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }
}

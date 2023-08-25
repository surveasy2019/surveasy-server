package surveasy.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.service-account}")
    private String serviceAccountBase64;

    @PostConstruct
    public void init() {
        try {
//            FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebaseServiceAccountKey.json");

            InputStream serviceAccount = new ByteArrayInputStream(getBase64DecodeBytes(serviceAccountBase64));
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] getBase64DecodeBytes(String input) {
        return Base64.decodeBase64(input);
    }
}
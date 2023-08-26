package surveasy.domain.panel.helper;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelInfoFirstSurveyDAO;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.exception.PanelNotFound;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.domain.panel.repository.PanelRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class PanelHelper {

    private final PanelMapper panelMapper;
    private final PanelRepository panelRepository;

    public static final String COLLECTION_NAME = "panelData";
    public static final String COLLECTION_FS_NAME = "FirstSurvey";


    // addExistingPanel 호출 전에 이미 db에 있는 패널인지 확인 필요


    public Panel addExistingPanel(PanelUidDTO panelUidDTO) throws ExecutionException, InterruptedException, ParseException {
        Firestore db = FirestoreClient.getFirestore();

        // Fetch Info
        ApiFuture<DocumentSnapshot> future = db.collection(COLLECTION_NAME).document(panelUidDTO.getUid()).get();
        DocumentSnapshot documentSnapshot = future.get();

        // Fetch First Survey Info
        ApiFuture<DocumentSnapshot> futureFirstSurvey = db.collection(COLLECTION_NAME).document(panelUidDTO.getUid())
                                                        .collection(COLLECTION_FS_NAME).document(panelUidDTO.getUid()).get();
        DocumentSnapshot documentSnapshotFS = futureFirstSurvey.get();


        if(documentSnapshot.exists()) {
            Date birth = strToDate(documentSnapshot.getString("birthDate"));
            Date signedAt = strToDate(documentSnapshot.getString("registerDate"));
            Boolean didFirstSurvey = false;
            PanelInfoFirstSurveyDAO panelInfoFirstSurveyDAO = null;

            if(documentSnapshotFS.exists()) {
                didFirstSurvey = true;
                panelInfoFirstSurveyDAO = PanelInfoFirstSurveyDAO.builder()
                        .english(documentSnapshotFS.getBoolean("EngSurvey"))
                        .city(documentSnapshotFS.getString("city"))
                        .district(documentSnapshotFS.getString("district"))
                        .family(documentSnapshotFS.getString("family"))
                        .houseType(documentSnapshotFS.getString("housingType"))
                        .job(documentSnapshotFS.getString("job"))
                        .major(documentSnapshotFS.getString("major"))
                        .married(documentSnapshotFS.getString("married"))
                        .military(documentSnapshotFS.getString("military"))
                        .pet(documentSnapshotFS.getString("pet"))
                        .university(documentSnapshotFS.getString("university"))
                        .build();
            }

            PanelInfoDAO panelInfoDAO = PanelInfoDAO.builder()
                    .name(documentSnapshot.getString("name"))
                    .email(documentSnapshot.getString("email"))
                    .fcmToken(documentSnapshot.getString("fcmToken"))
                    .gender(documentSnapshot.getString("gender"))
                    .birth(birth)
                    .accountOwner(documentSnapshot.getString("accountOwner"))
                    .accountType(documentSnapshot.getString("accountType"))
                    .didFirstSurvey(didFirstSurvey)
                    .inflowPath(documentSnapshot.getString("inflowPath"))
                    .lastParticipatedAt(documentSnapshot.getDate("lastParticipatedDate"))
                    .marketingAgree(documentSnapshot.getBoolean("marketingAgree"))
                    .phoneNumber(documentSnapshot.getString("phoneNumber"))
                    .platform(0)
                    .pushOn(documentSnapshot.getBoolean("pushOn"))
                    .signedUpAt(signedAt)
                    .rewardCurrent(documentSnapshot.get("reward_current", Integer.class))
                    .rewardTotal(documentSnapshot.get("reward_total", Integer.class))
                    .snsAuth(documentSnapshot.getBoolean("snsAuth"))
                    .snsUid(documentSnapshot.getString("snsUid"))
                    .build();


            Panel newPanel = panelMapper.toEntity(panelInfoDAO, panelInfoFirstSurveyDAO);
            Panel savedPanel = panelRepository.save(newPanel);

            // 토큰 생성


            return savedPanel;
        } else {
            // 가입된 적 없는 패널 처리
            throw PanelNotFound.EXCEPTION;
        }
    }

    private Date strToDate(String strDate) throws ParseException {
        if(strDate == null) return null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(strDate);
        return date;
    }

}

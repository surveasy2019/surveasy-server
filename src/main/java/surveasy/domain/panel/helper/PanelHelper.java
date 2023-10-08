package surveasy.domain.panel.helper;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelInfoFirstSurveyDAO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.dto.response.PanelAdminListResponse;
import surveasy.domain.panel.dto.response.PanelInfoResponse;
import surveasy.domain.panel.exception.PanelDuplicateData;
import surveasy.domain.panel.exception.PanelNotFoundFB;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.domain.panel.repository.PanelRepository;
import surveasy.global.common.dto.PageInfo;
import surveasy.global.config.user.PanelDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class PanelHelper {

    private final PanelMapper panelMapper;
    private final PanelRepository panelRepository;

    public static final String COLLECTION_NAME = "panelData";
    public static final String COLLECTION_FS_NAME = "FirstSurvey";


    private Date strToDate(String strDate) throws ParseException {
        if(strDate == null) return null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(strDate);
        return date;
    }

    private Panel createPanelFromFirestore(String uid) throws ExecutionException, InterruptedException, ParseException {
        Firestore db = FirestoreClient.getFirestore();

        // Fetch - Firebase Panel Info
        ApiFuture<DocumentSnapshot> future = db.collection(COLLECTION_NAME).document(uid).get();
        DocumentSnapshot documentSnapshot = future.get();

        // Fetch - Firebase Panel First Survey Info
        ApiFuture<DocumentSnapshot> futureFirstSurvey = db.collection(COLLECTION_NAME).document(uid)
                .collection(COLLECTION_FS_NAME).document(uid).get();
        DocumentSnapshot documentSnapshotFS = futureFirstSurvey.get();


        if(documentSnapshot.exists()) {
            Date birth = strToDate(documentSnapshot.getString("birthDate"));
            Date signedAt = strToDate(documentSnapshot.getString("registerDate"));
            Boolean didFirstSurvey = false;
            PanelInfoFirstSurveyDAO panelInfoFirstSurveyDAO = null;

            if (documentSnapshotFS.exists()) {
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
                    .uid(documentSnapshot.getString("uid"))
                    .name(documentSnapshot.getString("name"))
                    .email(documentSnapshot.getString("email"))
                    .fcmToken(documentSnapshot.getString("fcmToken"))
                    .gender(documentSnapshot.getString("gender"))
                    .birth(birth)
                    .accountOwner(documentSnapshot.getString("accountOwner"))
                    .accountType(documentSnapshot.getString("accountType"))
                    .accountNum(documentSnapshot.getString("accountNumber"))
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


            Panel panel = panelMapper.toEntityExisting(panelInfoDAO, panelInfoFirstSurveyDAO);
            return panel;

        } else {
            throw PanelNotFoundFB.EXCEPTION;
        }
    }


    public Panel addExistingPanelIfNeed(PanelUidDTO panelUidDTO) throws ExecutionException, InterruptedException, ParseException {
        String uid = panelUidDTO.getUid();
        Optional<Panel> panel = panelRepository.findByUid(uid);

        // DB에 아직 없는 패널
        if(panel.isEmpty()) {
            Panel newPanel = createPanelFromFirestore(uid);
            panelRepository.save(newPanel);
        }

        // DB에 이미 존재하는 패널
        else {
            throw PanelDuplicateData.EXCEPTION;
        }

        return panelRepository.findByUid(uid).get();
    }

    public Panel addNewPanelIfNeed(PanelSignUpDTO panelSignUpDTO) {
        String email = panelSignUpDTO.getEmail();
        Optional<Panel> panel = panelRepository.findByEmail(email);

        // DB에 아직 없는 패널
        if(panel.isEmpty()) {
            Panel newPanel = panelMapper.toEntityNew(panelSignUpDTO);
            panelRepository.save(newPanel);
        }

        // DB에 이미 존재하는 패널
        else {
            throw PanelDuplicateData.EXCEPTION;
        }

        return panelRepository.findByEmail(email).get();
    }


    public Long getPanelResponseCount(Long userId) {
        return 10L;     // responseRepository.findAllByUserId(userId);
    }


    public PanelAdminListResponse getAdminPanelList(Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        Page<Panel> panels = panelRepository.findAll(pageRequest);

        List<Panel> panelList = new ArrayList<>();

        if(panels != null && panels.hasContent()) {
            panelList = panels.getContent();
        }

        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalElements(panels.getTotalElements())
                .totalPages(panels.getTotalPages())
                .build();

        return PanelAdminListResponse.from(panelList, pageInfo);
    }
}

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
import surveasy.domain.activepanel.domain.Activepanel;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.*;
import surveasy.domain.panel.dto.response.PanelAdminListResponse;
import surveasy.domain.panel.exception.PanelDuplicateData;
import surveasy.domain.panel.exception.PanelNotFoundFB;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.domain.panel.repository.PanelRepository;
import surveasy.global.common.SurveyOptions;
import surveasy.global.common.dto.PageInfo;
import surveasy.global.common.function.DateAndString;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class PanelHelper {

    private final PanelMapper panelMapper;
    private final PanelRepository panelRepository;

    public static final String COLLECTION_NAME = "panelData";
    public static final String COLLECTION_FS_NAME = "FirstSurvey";


    // [App] Firebase 기존 패널 정보 가져오기
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
            Date birth = DateAndString.stringToDateYMD(documentSnapshot.getString("birthDate"));
            Date signedAt = DateAndString.stringToDateYMD(documentSnapshot.getString("registerDate"));
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
                    .accountNumber(documentSnapshot.getString("accountNumber"))
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


    // [App] 기존 패널 가입 처리
    public Panel addExistingPanelIfNeed(PanelUidDTO panelUidDTO) throws ExecutionException, InterruptedException, ParseException {
        String uid = panelUidDTO.getUid();
        Optional<Panel> panel = panelRepository.findByUid(uid);

        if(panel.isEmpty()) {   // DB에 아직 없는 패널
            Panel newPanel = createPanelFromFirestore(uid);
            panelRepository.save(newPanel);
        } else {                // DB에 이미 존재하는 패널
            throw PanelDuplicateData.EXCEPTION;
        }

        return panelRepository.findByUid(uid).get();
    }


    // [App] 신규 회원 가입 처리
    public Panel addNewPanelIfNeed(PanelSignUpDTO panelSignUpDTO) {
        String email = panelSignUpDTO.getEmail();
        Optional<Panel> panel = panelRepository.findByEmail(email);

        if(panel.isEmpty()) {   // DB에 아직 없는 패널
            Panel newPanel = panelMapper.toEntityNew(panelSignUpDTO);
            panelRepository.save(newPanel);
        } else {                // DB에 이미 존재하는 패널
            throw PanelDuplicateData.EXCEPTION;
        }

        return panelRepository.findByEmail(email).get();
    }


    // [App] List - 설문 참여 완료 후 패널 정보 업데이트
    /* rewardCurrent, lastParticipatedDate */
    public void updatePanelInfoAfterResponse(Panel panel, Integer reward) {
        Integer rewardCurrent = panel.getRewardCurrent();

        panel.setRewardCurrent(rewardCurrent + reward);
        panel.setLastParticipatedAt(new Date());

        panelRepository.save(panel);
    }


    // [App] MyPage - 패널 정보 업데이트
    /* phoneNumber, accountType, accountNumber, english */
    public Panel updatePanelInfo(Panel panel, PanelInfoUpdateDTO panelInfoUpdateDTO) {
        if(panelInfoUpdateDTO.getPhoneNumber() != null) {
            panel.setPhoneNumber(panelInfoUpdateDTO.getPhoneNumber());
        }

        if(panelInfoUpdateDTO.getAccountType() != null) {
            panel.setAccountType(panelInfoUpdateDTO.getAccountType());
        }

        if(panelInfoUpdateDTO.getAccountNumber() != null) {
            panel.setAccountNumber(panelInfoUpdateDTO.getAccountNumber());
        }

        if(panelInfoUpdateDTO.getEnglish() != null) {
            panel.setEnglish(panelInfoUpdateDTO.getEnglish());
        }

        return panelRepository.save(panel);
    }


    // [Web] Active Panel 목록
    /* 성별로 구분 */
    public Activepanel getActivePanelList() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date aWeekAgo = cal.getTime();

        String totalList = "", maleList = "", femaleList = "";

        for(int i=0 ; i<SurveyOptions.AGES.length-1 ; i++) {
            Calendar ageFrom = Calendar.getInstance();
            Calendar ageEnd = Calendar.getInstance();
            ageFrom.add(Calendar.YEAR, -SurveyOptions.AGES[i]);
            ageEnd.add(Calendar.YEAR, -SurveyOptions.AGES[i+1]);

            long malePanel = panelRepository.countActivePanel(0, aWeekAgo, ageFrom.getTime(), ageEnd.getTime());
            long femalePanel = panelRepository.countActivePanel(1, aWeekAgo, ageFrom.getTime(), ageEnd.getTime());

            totalList += (malePanel + femalePanel) + ",";
            maleList += malePanel + ",";
            femaleList += femalePanel + ",";
        }

        return Activepanel.of(totalList, maleList, femaleList);
    }


    // [Web] Admin - 패널 전체 목록
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

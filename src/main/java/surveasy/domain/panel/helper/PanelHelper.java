package surveasy.domain.panel.helper;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import surveasy.domain.activepanel.domain.Activepanel;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.option.AuthProvider;
import surveasy.domain.panel.domain.option.PanelPlatform;
import surveasy.domain.panel.domain.option.PanelStatus;
import surveasy.domain.panel.dto.request.*;
import surveasy.domain.panel.dto.response.OAuth2AppleResponse;
import surveasy.domain.panel.dto.response.OAuth2Response;
import surveasy.domain.panel.dto.response.PanelAdminListResponse;
import surveasy.domain.panel.dto.response.PanelTokenResponse;
import surveasy.domain.panel.exception.*;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.domain.panel.repository.PanelRepository;
import surveasy.domain.panel.util.RedisUtil;
import surveasy.domain.survey.domain.target.TargetGender;
import surveasy.global.common.SurveyOptions;
import surveasy.global.common.dto.PageInfo;
import surveasy.global.common.util.DateAndStringUtils;
import surveasy.global.config.jwt.TokenProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class PanelHelper {

    private final PanelMapper panelMapper;
    private final PanelRepository panelRepository;

    private final TokenProvider tokenProvider;

    private final RedisUtil redisUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String COLLECTION_NAME = "panelData";
    private static final String COLLECTION_FS_NAME = "FirstSurvey";

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Panel findPanelById(Long panelId) {
        return panelRepository.findById(panelId)
                .orElseThrow(() -> PanelNotFound.EXCEPTION);
    }

    public Panel findPanelByEmailAndAuthProvider(String email, AuthProvider authProvider) {
        return panelRepository.findByEmailAndAuthProvider(email, authProvider).orElse(null);
    }


    /* [App] 패널 생성 - existing */
    public Panel addExistingPanelIfNeed(PanelEmailSignInDTO panelEmailSignInDTO) throws ExecutionException, InterruptedException {
        Panel panel = panelRepository.findByEmail(panelEmailSignInDTO.getEmail()).orElse(null);

        // DB에 정보 없는 패널
        if(panel == null) {
            panel = createPanelFromFirestore(panelEmailSignInDTO);
            return panelRepository.save(panel);
        }

        // DB에 정보 있는 패널
        if(!matchesPassword(panelEmailSignInDTO.getPassword(), panel.getPassword())) {
            throw MismatchPassword.EXCEPTION;
        }
        return panel;
    }


    /* [App] Firebase 기존 패널 정보 가져오기 */
    private Panel createPanelFromFirestore(PanelEmailSignInDTO panelEmailSignInDTO) throws ExecutionException, InterruptedException {
        String uid = panelEmailSignInDTO.getUid();
        Firestore db = FirestoreClient.getFirestore();

        // Firebase Panel Info
        ApiFuture<DocumentSnapshot> future = db.collection(COLLECTION_NAME).document(uid).get();
        DocumentSnapshot documentSnapshot = future.get();

        // Firebase Panel First Survey Info
        ApiFuture<DocumentSnapshot> futureFirstSurvey = db.collection(COLLECTION_NAME).document(uid).collection(COLLECTION_FS_NAME).document(uid).get();
        DocumentSnapshot documentSnapshotFS = futureFirstSurvey.get();

        if(documentSnapshot.exists()) {
            LocalDate birth = DateAndStringUtils.stringToLocalDate(documentSnapshot.getString("birthDate"));
            String signedUpAtStr = documentSnapshot.getString("registerDate");
            LocalDate signedUpAt = signedUpAtStr == null ? LocalDate.now() : DateAndStringUtils.stringToLocalDate(signedUpAtStr);

            boolean didFirstSurvey = false;
            PanelInfoFirstSurveyDAO panelInfoFirstSurveyDAO = null;

            if (documentSnapshotFS.exists()) {
                didFirstSurvey = true;
                panelInfoFirstSurveyDAO = PanelInfoFirstSurveyDAO.builder()
                        .english(documentSnapshotFS.getBoolean("EngSurvey"))
                        .city(documentSnapshotFS.getString("city"))
                        .family((panelEmailSignInDTO.getPlatform().equals(PanelPlatform.ANDROID)) ? documentSnapshotFS.getString("family") : documentSnapshotFS.getString("housingType"))
                        .job(documentSnapshotFS.getString("job"))
                        .major(documentSnapshotFS.getString("major"))
                        .pet(documentSnapshotFS.getString("pet"))
                        .build();
            }

            PanelInfoDAO panelInfoDAO = PanelInfoDAO.builder()
                    .name(documentSnapshot.getString("name"))
                    .email(documentSnapshot.getString("email"))
                    .password(encodePassword(panelEmailSignInDTO.getPassword()))
                    .fcmToken(documentSnapshot.getString("fcmToken"))
                    .gender(documentSnapshot.getString("gender"))
                    .birth(birth)
                    .accountOwner(documentSnapshot.getString("accountOwner"))
                    .accountType(documentSnapshot.getString("accountType"))
                    .accountNumber(documentSnapshot.getString("accountNumber"))
                    .didFirstSurvey(didFirstSurvey)
                    .inflowPath(String.valueOf(documentSnapshot.get("inflowPath")))
                    .phoneNumber(documentSnapshot.getString("phoneNumber"))
                    .platform(panelEmailSignInDTO.getPlatform())
                    .pushOn(documentSnapshot.getBoolean("pushOn"))
                    .marketingAgree(documentSnapshot.getBoolean("marketingAgree"))
                    .rewardCurrent(documentSnapshot.get("reward_current", Integer.class))
                    .rewardTotal(documentSnapshot.get("reward_total", Integer.class))
                    .signedUpAt(signedUpAt)
                    .lastParticipatedAt(DateAndStringUtils.dateToLocalDateTime(documentSnapshot.getDate("lastParticipatedDate")))
                    .build();


            return panelMapper.toEntityExisting(panelInfoDAO, panelInfoFirstSurveyDAO);

        } else {
            throw PanelNotFoundFB.EXCEPTION;
        }
    }


    private Panel addNewPanel(OAuth2UserInfo oAuth2UserInfo) {
        Panel newPanel = panelMapper.toEntityNew(oAuth2UserInfo);
        return panelRepository.save(newPanel);
    }

    private Panel addNewPanelApple(PanelAppleSignUpDTO panelAppleSignUpDTO) {
        Panel newPanel = panelMapper.toEntityNewApple(panelAppleSignUpDTO);
        return panelRepository.save(newPanel);
    }


    public OAuth2Response createOAuth2Response(OAuth2UserInfo oAuth2UserInfo) {
        Panel panel = findPanelByEmailAndAuthProvider(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getAuthProvider());
        boolean existingPanel, additionalInfo;

        // 신규 가입 패널
        if(panel == null) {
            panel = addNewPanel(oAuth2UserInfo);
            existingPanel = false;
            additionalInfo = false;
        }

        // 추가정보 미입력 패널
        else if(panel.getStatus().equals(PanelStatus.INFO_YET)) {
            existingPanel = true;
            additionalInfo = false;
        }

        // 기존에 가입된 패널
        else {
            panel = panel.updateFrom(oAuth2UserInfo);   // OAuth2 Server 최신 정보로 업데이트
            existingPanel = true;
            additionalInfo = true;
        }

        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);

        return panelMapper.toOAuth2Response(existingPanel, additionalInfo, accessToken, refreshToken);
    }


    public OAuth2AppleResponse createOauth2AppleResponse(AuthIdDTO authIdDTO) {
        final Panel panel = panelRepository.findByAuthProviderAndAuthId(AuthProvider.APPLE, authIdDTO.getAuthId()).orElse(null);
        boolean isSignedUp = (panel != null);

        if(isSignedUp) {
            final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
            final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
            final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);
            return panelMapper.toOAuth2AppleResponse(true, PanelTokenResponse.of(accessToken, refreshToken));
        }

        return OAuth2AppleResponse.of(false, null);
    }


    /* [App] 패널 추가 정보 입력 */
    public Panel signUp(Panel panel, PanelSignUpDTO panelSignUpDTO) {
        panel.setPlatform(panelSignUpDTO.getPlatform());
        panel.setFcmToken("Temp_FCM");
        panel.setAccountOwner(panelSignUpDTO.getAccountOwner());
        panel.setAccountType(panelSignUpDTO.getAccountType());
        panel.setAccountNumber(panelSignUpDTO.getAccountNumber());
        panel.setInflowPath(panelSignUpDTO.getInflowPath());
        if(panelSignUpDTO.getInflowPathEtc() != null) panel.setInflowPathEtc(panelSignUpDTO.getInflowPathEtc());
        panel.setPushOn(panelSignUpDTO.getPushOn());
        panel.setMarketingAgree(panelSignUpDTO.getMarketingAgree());
        panel.setStatus(PanelStatus.FS_YET);
        panel.setRole("ROLE_USER");

        return panelRepository.save(panel);
    }


    public PanelTokenResponse signUpApple(PanelAppleSignUpDTO panelAppleSignUpDTO) {
        final Panel checkPanel = panelRepository.findByAuthProviderAndAuthId(AuthProvider.APPLE, panelAppleSignUpDTO.getAuthId()).orElse(null);
        if(checkPanel != null) throw PanelDuplicateData.EXCEPTION;

        Panel panel = addNewPanelApple(panelAppleSignUpDTO);
        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);
        return PanelTokenResponse.of(accessToken, refreshToken);
    }


    /* [App] 리프레쉬 토큰 검증 */
    public void matchesRefreshToken(String refreshToken, Panel panel) {
        String savedToken = redisUtil.getRefreshToken(panel.getId().toString());
        if(savedToken == null || !savedToken.equals(refreshToken)) {
            throw RefreshTokenNotFound.EXCEPTION;
        }
    }


    /* [App] First Survey */
    public Long doFirstSurvey(Panel panel, PanelFirstSurveyDTO panelFirstSurveyDTO) {
        panel.setEnglish(panelFirstSurveyDTO.getEnglish());
        panel.setCity(panelFirstSurveyDTO.getCity());
        panel.setFamily(panelFirstSurveyDTO.getFamily());
        panel.setJob(panelFirstSurveyDTO.getJob());
        if(panelFirstSurveyDTO.getMajor() != null) panel.setMajor(panelFirstSurveyDTO.getMajor());
        panel.setPet(panelFirstSurveyDTO.getPet());
        panel.setLastParticipatedAt(LocalDateTime.now());
        panel.setRewardCurrent(200);
        panel.setRewardTemp(200);
        panel.setStatus(PanelStatus.FS_ONLY_DONE);

        return panelRepository.save(panel).getId();
    }


    /* [App] 설문 참여 완료 후 패널 정보 업데이트
    * rewardCurrent, lastParticipatedDate */
    public void updatePanelInfoAfterResponse(Panel panel, Integer reward) {
        Integer rewardCurrent = panel.getRewardCurrent();

        panel.setRewardCurrent(rewardCurrent + reward);
        panel.setLastParticipatedAt(LocalDateTime.now());
        if(panel.getStatus().equals(PanelStatus.FS_ONLY_DONE)) panel.setStatus(PanelStatus.FS_DONE);

        panelRepository.save(panel);
    }


    /* [App] 마이페이지 패널 정보 업데이트
    * phoneNumber, accountType, accountNumber, english */
    public Panel updatePanelInfo(Panel panel, PanelInfoUpdateDTO panelInfoUpdateDTO) {
        if(panelInfoUpdateDTO.getPhoneNumber() != null) {
            panel.setPhoneNumber(panelInfoUpdateDTO.getPhoneNumber());
        }

        if(panelInfoUpdateDTO.getAccountOwner() != null) {
            panel.setAccountOwner(panelInfoUpdateDTO.getAccountOwner());
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


    /* [Web] 홈화면 전체 패널 수 */
    public Long getPanelCount() {
        return panelRepository.count();
    }


    /* [Web] Active Panel 목록
    * 성별로 구분 */
    public Activepanel getActivePanelList() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date aWeekAgo = cal.getTime();

        StringBuilder totalList = new StringBuilder();
        StringBuilder maleList = new StringBuilder();
        StringBuilder femaleList = new StringBuilder();

        for(int i=0 ; i<SurveyOptions.AGES.length-1 ; i++) {
            Calendar ageFrom = Calendar.getInstance();
            Calendar ageEnd = Calendar.getInstance();
            ageFrom.add(Calendar.YEAR, -SurveyOptions.AGES[i]);
            ageEnd.add(Calendar.YEAR, -SurveyOptions.AGES[i+1]);

            long malePanel = panelRepository.countActivePanel(TargetGender.MALE, aWeekAgo, ageFrom.getTime(), ageEnd.getTime());
            long femalePanel = panelRepository.countActivePanel(TargetGender.FEMALE, aWeekAgo, ageFrom.getTime(), ageEnd.getTime());

            totalList.append(malePanel + femalePanel);
            maleList.append(malePanel);
            femaleList.append(femalePanel);

            if(i<SurveyOptions.AGES.length-2) {
                totalList.append(", ");
                maleList.append(", ");
                femaleList.append(", ");
            }
        }

        return Activepanel.of(totalList.toString(), maleList.toString(), femaleList.toString());
    }


    /* [Web] Admin - 패널 전체 목록 */
    public PanelAdminListResponse getAdminPanelList(Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        Page<Panel> panels = panelRepository.findAll(pageRequest);

        List<Panel> panelList = new ArrayList<>();

        if (panels != null && panels.hasContent()) {
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

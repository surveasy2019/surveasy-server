package surveasy.domain.survey.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import surveasy.domain.response.repository.ResponseRepository;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.admin.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDTO;
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.web.SurveyAdminListResponse;
import surveasy.domain.survey.exception.SurveyCannotDelete;
import surveasy.domain.survey.exception.SurveyCannotEdit;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.domain.survey.vo.SurveyAppHomeListItemVo;
import surveasy.global.common.dto.PageInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SurveyHelper {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;

    private final ResponseRepository responseRepository;

    public Survey findById(Long surveyId) {
        return surveyRepository
                .findById(surveyId)
                .orElseThrow(() -> {
                    throw SurveyNotFound.EXCEPTION;
                });
    }

    public PageInfo getPageInfo(int pageNum, int pageSize, Page<Survey> surveys) {
        return PageInfo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalElements(surveys.getTotalElements())
                .totalPages(surveys.getTotalPages())
                .build();
    }


    // [Web] 홈화면 진행중인 설문 개수
    public Long getSurveyTotalCount() {
        return surveyRepository
                .countByProgressGreaterThan(1);
    }


    // [Web] 설문 주문하기
    public Long createSurvey(SurveyServiceDTO surveyServiceDTO) {
        Survey newSurvey = surveyMapper.toEntity(surveyServiceDTO);
        Survey savedSurvey = surveyRepository.save(newSurvey);

        return savedSurvey.getId();
    }


    // [Web] 마이 페이지 진행중인 설문 개수
    public Long getMyPageSurveyOngoingCount(String email) {
        return surveyRepository.countByEmailAndProgressEquals(email, 2);
    }


    // [Web] 마이 페이지 완료된 설문 개수
    public Long getMyPageSurveyDoneCount(String email) {
        return surveyRepository.countByEmailAndProgressGreaterThanEqual(email, 3);
    }


    // [Web] 마이 페이지 주문 목록
    public List<Survey> getMyPageOrderList(String email) {
        return surveyRepository.findAllByEmail(email);
    }


    // [Web] 마이 페이지 설문 수정 (title, link, headCount, price)
    /* progress 2 미만일 경우만 가능 */
    public Long editMyPageSurvey(Long id, SurveyMyPageEditDTO surveyMyPageEditDTO) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> {
                    throw SurveyNotFound.EXCEPTION;
                });

        if(survey.getProgress() >= 2) {
            throw SurveyCannotEdit.EXCEPTION;
        }

        if(surveyMyPageEditDTO.getTitle() != null) {
            survey.setTitle(surveyMyPageEditDTO.getTitle());
        }

        if(surveyMyPageEditDTO.getLink() != null) {
            survey.setLink(surveyMyPageEditDTO.getLink());
        }

        if(surveyMyPageEditDTO.getHeadCount() != null) {
            survey.setHeadCount(surveyMyPageEditDTO.getHeadCount());
        }

        if(surveyMyPageEditDTO.getPrice() != null) {
            survey.setPrice(surveyMyPageEditDTO.getPrice());
        }

        return surveyRepository.save(survey).getId();
    }


    // [Web] 마이 페이지 설문 삭제
    /* progress 2 미만일 경우만 가능 */
    public Long deleteMyPageSurvey(Long id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> {
            throw SurveyNotFound.EXCEPTION;
        });

        if(survey.getProgress() >= 2) {
            throw SurveyCannotDelete.EXCEPTION;
        }

        surveyRepository.deleteById(id);
        return id;
    }


    // [Web] 어드민 설문 전체 목록
    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());
        Page<Survey> surveys = surveyRepository.findAll(pageRequest);
        PageInfo pageInfo = getPageInfo(pageNum, pageSize, surveys);

        List<Survey> surveyList = new ArrayList<>();
        if(surveys != null && surveys.hasContent()) {
            surveyList = surveys.getContent();
        }

        return SurveyAdminListResponse.from(surveyList, pageInfo);
    }


    // [Web] 현재 sid 최댓값 가져오기
    /* 검수 후 progress == 2로 업데이트 하려는 경우, 현재 sid == 0이면 sid를 (최댓값 + 1)으로 부여 */
    public Long findMaxSid() {
        return surveyRepository.findMaxSid();
    }


    // [Web] 어드민 검수 완료 (progress, noticeToPanel, reward)
    /* sid == 0 인 경우, sid를 current max 값으로 업데이트 */
    public Long updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        Survey survey = surveyRepository
                        .findById(id)
                        .orElseThrow(() -> {
                            throw SurveyNotFound.EXCEPTION;
                        });

        if(surveyAdminDTO.getProgress() != null) {
            survey.setProgress(surveyAdminDTO.getProgress());

            if(survey.getSid() == 0 && surveyAdminDTO.getProgress() == 2) {
                survey.setSid(findMaxSid()+1);      // sid 발급
            }
        }

        if(surveyAdminDTO.getReward() != null) {
            survey.setReward(surveyAdminDTO.getReward());
        }

        if(surveyAdminDTO.getLink() != null) {
            survey.setLink(surveyAdminDTO.getLink());
        }

        if(surveyAdminDTO.getNoticeToPanel() != null) {
            survey.setNoticeToPanel(surveyAdminDTO.getNoticeToPanel());
        }

        return surveyRepository.save(survey).getId();
    }

    // [App] 진행중인 설문 목록
    /* progress == 2 */
    public List<Survey> getSurveyListProgressEq2() {
        List<Survey> surveyList = surveyRepository.findSurveyListByProgressEq2();
        return surveyList;
    }


    // [App] 설문 현재 응답수 업데이트
    public Integer updateCurrentHeadCount(Survey survey) {
        Integer responseCount = survey.getResponseCount();
        survey.setResponseCount(responseCount + 1);

        return surveyRepository.save(survey).getResponseCount();
    }


    // [App] 설문 progress 업데이트 (2 -> 3)
    public Long updateProgress2To3(Survey survey) {
        survey.setProgress(3);
        return surveyRepository.save(survey).getId();
    }
}

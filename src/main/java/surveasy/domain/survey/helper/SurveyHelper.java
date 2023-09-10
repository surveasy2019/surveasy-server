package surveasy.domain.survey.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.web.SurveyAdminListResponse;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.global.common.dto.PageInfo;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SurveyHelper {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;

    public Survey findById(Long surveyId) {
        return surveyRepository
                .findById(surveyId)
                .orElseThrow(() -> {
                    throw SurveyNotFound.EXCEPTION;
                });
    }

    public Long getSurveyTotalCount() {
        return surveyRepository
                .countByProgressGreaterThan(0);
    }

    public Long createSurvey(SurveyServiceDTO surveyServiceDTO) {
        Survey newSurvey = surveyMapper.toEntity(surveyServiceDTO);
        Survey savedSurvey = surveyRepository.save(newSurvey);

        return savedSurvey.getId();
    }

    public Long getMyPageSurveyOngoingCount(String email) {
        return surveyRepository.countByEmailAndProgressEquals(email, 2);
    }

    public Long getMyPageSurveyDoneCount(String email) {
        return surveyRepository.countByEmailAndProgressGreaterThanEqual(email, 3);
    }

    public List<Survey> getMyPageOrderList(String email) {
        return surveyRepository.findAllByEmail(email);
    }

    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        Page<Survey> surveys = surveyRepository.findAll(pageRequest);
        List<Survey> surveyList = new ArrayList<>();

        if(surveys != null && surveys.hasContent()) {
            surveyList = surveys.getContent();
        }

        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalElements(surveys.getTotalElements())
                .totalPages(surveys.getTotalPages())
                .build();

        return SurveyAdminListResponse.from(surveyList, pageInfo);
    }

    public Long updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        Survey survey = surveyRepository
                        .findById(id)
                        .orElseThrow(() -> {
                            throw SurveyNotFound.EXCEPTION;
                        });

        if(surveyAdminDTO.getProgress() != null) {
            survey.setProgress(surveyAdminDTO.getProgress());
            if(surveyAdminDTO.getProgress() == 2) {
                // lastCheckedId 발급
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
}

package surveasy.domain.survey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.payment.domain.Payment;
import surveasy.domain.payment.helper.PaymentHelper;
import surveasy.domain.pg.dto.response.TossPaymentsResponseDto;
import surveasy.domain.pg.provider.TossServiceImpl;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.dto.request.admin.AdminSurveyUpdateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyUpdateRequestDto;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.vo.SurveyAppListDetailVo;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;
import surveasy.domain.user.domain.User;
import surveasy.domain.user.exception.UnauthorizedUser;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class SurveyServiceImpl implements SurveyService {
    private final SurveyHelper surveyHelper;
    private final SurveyMapper surveyMapper;
    private final PanelHelper panelHelper;
    private final PaymentHelper paymentHelper;
    private final TossServiceImpl tossServiceImpl;

    @Override
    public SurveyCreateResponseDto createSurvey(User user, SurveyCreateRequestDto surveyCreateRequestDto) {
        createSurveyAndSave(user, surveyCreateRequestDto);
        TossPaymentsResponseDto responseDto = tossServiceImpl.requestPayments(surveyCreateRequestDto.paymentInfo());
        return surveyMapper.toSurveyCreateResponseDto(responseDto);
    }

    @Override
    public SurveyHomeResponse getSurveyTotalCount() {
        long surveyTotalCount = surveyHelper.getSurveyTotalCount();
        long panelTotalCount = panelHelper.getPanelCount();
        return surveyMapper.toSurveyHomeResponse(surveyTotalCount, panelTotalCount);
    }

    @Override
    public SurveyListResponse getSurveyList(Pageable pageable) {
        Page<SurveyListVo> surveyList = surveyHelper.findAllSurveyListVos(pageable);
        return surveyMapper.toSurveyListResponse(surveyList);
    }

    @Override
    public SurveyMyPageOrderListResponse getSurveyMyPageOrderList(User user) {
        List<SurveyMyPageOrderVo> surveyMyPageOrderVoList = surveyHelper.getMyPageOrderList(user.getId());
        return surveyMapper.toSurveyMyPageOrderListResponse(surveyMyPageOrderVoList);
    }

    @Override
    public SurveyMyPageCountResponse getMyPageSurveyCounts(User user) {
        Long ongoingSurveyCount = surveyHelper.getCountByUserIdAndStatus(user.getId(), SurveyStatus.IN_PROGRESS);
        Long doneSurveyCount = surveyHelper.getCountByUserIdAndStatus(user.getId(), SurveyStatus.DONE);
        return surveyMapper.toSurveyMyPageCountResponse(ongoingSurveyCount, doneSurveyCount);
    }

    @Override
    public SurveyIdResponse updateSurvey(Long surveyId, SurveyUpdateRequestDto surveyUpdateRequestDto, User user) {
        Survey survey = surveyHelper.findSurveyByIdOrThrow(surveyId);
        validateAuthorizedUser(survey, user);
        return surveyMapper.toSurveyIdResponse(surveyHelper.updateSurvey(survey, surveyUpdateRequestDto));
    }

    @Override
    public SurveyIdResponse deleteSurvey(Long surveyId, User user) {
        Survey survey = surveyHelper.findSurveyByIdOrThrow(surveyId);
        validateAuthorizedUser(survey, user);
        return surveyMapper.toSurveyIdResponse(surveyHelper.deleteSurvey(survey));
    }

    @Override
    public SurveyAppHomeListResponse getSurveyAppHomeList(Panel panel) {
        return surveyMapper.toSurveyAppHomeList(surveyHelper.getSurveyAppHomeList(panel), panel.getStatus());
    }

    @Override
    public SurveyAppListResponse getSurveyAppList(Panel panel, Pageable pageable) {
        return surveyMapper.toSurveyAppList(surveyHelper.getSurveyAppList(panel, pageable), panel.getStatus());
    }

    @Override
    public SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId) {
        return surveyHelper.getSurveyAppListDetail(surveyId);
    }

    @Override
    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable, String username) {
        return surveyHelper.getAdminSurveyList(pageable, username);
    }

    @Override
    public Survey getAdminSurvey(Long surveyId) {
        return surveyHelper.getAdminSurvey(surveyId);
    }

    @Override
    public SurveyIdResponse updateAdminSurvey(Long surveyId, AdminSurveyUpdateRequestDto adminSurveyUpdateRequestDto) {
        Survey survey = surveyHelper.findSurveyByIdOrThrow(surveyId);
        return surveyMapper.toSurveyIdResponse(surveyHelper.updateAdminSurvey(survey, adminSurveyUpdateRequestDto));
    }

    @Override
    public void deleteAdminSurvey(Long surveyId) {
        surveyHelper.deleteAdminSurvey(surveyId);
    }

    private void createSurveyAndSave(User user, SurveyCreateRequestDto surveyCreateRequestDto) {
        Survey survey = surveyHelper.createSurveyAndSave(user, surveyCreateRequestDto);
        Payment payment = paymentHelper.createPaymentAndSave(surveyCreateRequestDto.paymentInfo());
        survey.setPayment(payment);
    }

    private void validateAuthorizedUser(Survey survey, User user) {
        if(!Objects.equals(survey.getUser().getId(), user.getId())) throw UnauthorizedUser.EXCEPTION;
    }
}

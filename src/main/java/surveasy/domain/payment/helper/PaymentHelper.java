package surveasy.domain.payment.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.payment.domain.Payment;
import surveasy.domain.payment.domain.RefundType;
import surveasy.domain.payment.dto.request.PaymentCreateRequestDto;
import surveasy.domain.payment.repository.PaymentRepository;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.exception.SurveyCannotRefund;

@RequiredArgsConstructor
@Component
public class PaymentHelper {
    private final PaymentRepository paymentRepository;

    public Payment createPaymentAndSave(PaymentCreateRequestDto paymentCreateRequestDto) {
        Payment newPayment = Payment.createPayment(paymentCreateRequestDto);
        return paymentRepository.save(newPayment);
    }

    public RefundType getRefundType(Survey survey) {
        if(survey.getStatus().equals(SurveyStatus.CREATED) || survey.getStatus().equals(SurveyStatus.WAITING)) return RefundType.ALL;
        return RefundType.PERCENT;
    }

    public Integer getRefundPrice(RefundType refundType, Survey survey, Payment payment) {
        if(refundType.equals(RefundType.ALL))
            return payment.getPrice();
        else if(survey.getResponseCount() >= survey.getHeadCount().getValue())
            throw SurveyCannotRefund.EXCEPTION;
        int refundPrice = (int)(payment.getPrice() * (survey.getResponseCount() / (double)survey.getHeadCount().getValue()));
        return Math.max(refundPrice, 0);
    }
}

package surveasy.domain.pg.provider;

import org.springframework.stereotype.Service;
import surveasy.domain.payment.domain.RefundType;
import surveasy.domain.payment.dto.request.PaymentCreateRequestDto;
import surveasy.domain.pg.dto.response.TossPaymentsResponseDto;

@Service
public interface TossService {
    TossPaymentsResponseDto requestPayments(PaymentCreateRequestDto paymentCreateRequestDto);
    TossPaymentsResponseDto getPaymentsDetail(String paymentKey);
    TossPaymentsResponseDto cancelPayments(RefundType refundType, String paymentKey, Integer cancelAmount);
}

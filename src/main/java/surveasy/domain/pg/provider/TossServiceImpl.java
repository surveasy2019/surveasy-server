package surveasy.domain.pg.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import surveasy.domain.payment.domain.RefundType;
import surveasy.domain.payment.dto.request.PaymentCreateRequestDto;
import surveasy.domain.pg.dto.request.TossPaymentsRequestDto;
import surveasy.domain.pg.dto.response.TossPaymentsResponseDto;
import surveasy.global.common.util.toss.TossFeignController;

@RequiredArgsConstructor
@Service
public class TossServiceImpl implements TossService {
    private static final String CANCEL_REASON = "고객이 취소를 원함";
    private final TossFeignController tossFeignController;

    @Override
    public TossPaymentsResponseDto requestPayments(PaymentCreateRequestDto paymentCreateRequestDto) {
        TossPaymentsRequestDto requestDto = TossPaymentsRequestDto.of(paymentCreateRequestDto);
        return tossFeignController.createPayments(requestDto);
    }

    @Override
    public TossPaymentsResponseDto getPaymentsDetail(String paymentKey) {
        return null;
    }

    @Override
    public TossPaymentsResponseDto cancelPayments(RefundType refundType, String paymentKey, Integer cancelAmount) {
        return null;
    }
}

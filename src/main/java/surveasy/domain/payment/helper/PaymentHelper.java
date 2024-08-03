package surveasy.domain.payment.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.payment.domain.Payment;
import surveasy.domain.payment.dto.request.PaymentCreateRequestDto;
import surveasy.domain.payment.repository.PaymentRepository;

@RequiredArgsConstructor
@Component
public class PaymentHelper {
    private final PaymentRepository paymentRepository;

    public Payment createPaymentAndSave(PaymentCreateRequestDto paymentCreateRequestDto) {
        Payment newPayment = Payment.createPayment(paymentCreateRequestDto);
        return paymentRepository.save(newPayment);
    }
}

package surveasy.domain.payment.dto.request;

public record PaymentCreateRequestDto(
        Integer priceDiscounted,
        Integer pointAdd,
        String paymentKey,
        String orderId,
        String amount
) {
}

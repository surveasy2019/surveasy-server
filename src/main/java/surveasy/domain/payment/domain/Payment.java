package surveasy.domain.payment.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.payment.dto.request.PaymentCreateRequestDto;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "payment")
@Entity
public class Payment {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer price;

    @NotNull
    private Integer priceDiscounted;

    @NotNull
    private Integer pointAdd;

    @NotNull
    @Builder.Default
    private Boolean isRefunded = false;

    @NotNull
    private String paymentKey;

    private String orderId;

    @NotNull
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    public static Payment createPayment(PaymentCreateRequestDto paymentCreateRequestDto) {
        return Payment.builder()
                .price(Integer.valueOf(paymentCreateRequestDto.amount()))
                .priceDiscounted(paymentCreateRequestDto.priceDiscounted())
                .pointAdd(paymentCreateRequestDto.pointAdd())
                .paymentKey(paymentCreateRequestDto.paymentKey())
                .orderId(paymentCreateRequestDto.orderId())
                .build();
    }
}

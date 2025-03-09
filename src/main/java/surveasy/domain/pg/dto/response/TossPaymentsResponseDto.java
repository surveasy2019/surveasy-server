package surveasy.domain.pg.dto.response;

import jakarta.annotation.Nullable;
import lombok.Builder;
import surveasy.domain.pg.dto.response.toss.*;

import java.util.List;

@Builder
public record TossPaymentsResponseDto(
        String mId,
        @Nullable String lastTransactionKey,
        String paymentKey,
        String orderId,
        String orderName,
        Integer taxExemptionAmount,
        String status,
        String requestedAt,
        String approvedAt,
        Boolean useEscrow,
        Boolean cultureExpense,
        @Nullable CardInfo card,
        @Nullable VirtualAccountInfo virtualAccount,
        @Nullable TransferInfo transfer,
        @Nullable MobilePhoneInfo mobilePhone,
        @Nullable GiftCertificateInfo giftCertificate,
        @Nullable CashReceiptInfo cashReceipt,
        @Nullable List<CashReceiptsInfo> cashReceipts,
        @Nullable DiscountInfo discount,
        @Nullable List<CancelsInfo> cancels,
        @Nullable String secret,
        String type,
        @Nullable PayInfo easyPay,
        String country,
        @Nullable FailureInfo failure,
        Boolean isPartialCancelable,
        @Nullable ReceiptInfo receipt,
        @Nullable CheckoutInfo checkout,
        String currency,
        Integer totalAmount,
        Integer balanceAmount,
        Integer suppliedAmount,
        Integer vat,
        Integer taxFreeAmount,
        String method,
        String version
) {
}

package surveasy.global.common.util.toss;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import surveasy.domain.pg.dto.request.TossPaymentsCancelRequestDto;
import surveasy.domain.pg.dto.request.TossPaymentsRequestDto;
import surveasy.domain.pg.dto.response.TossPaymentsResponseDto;
import surveasy.global.config.TossFeignClientConfig;

@FeignClient(name = "toss-feign-client", url = "https://api.tosspayments.com/v1/payments", configuration = TossFeignClientConfig.class)
public interface TossFeignController {
    @PostMapping("/confirm")
    TossPaymentsResponseDto createPayments(@RequestBody final TossPaymentsRequestDto requestDto);

    @GetMapping("/{paymentKey}")
    TossPaymentsResponseDto getPaymentDetail(@PathVariable(value = "paymentKey") final String paymentKey);

    @PostMapping("/{paymentKey}/cancel")
    TossPaymentsResponseDto cancelPayments(@PathVariable(value = "paymentKey") final String paymentKey, @RequestBody final TossPaymentsCancelRequestDto requestDto);
}

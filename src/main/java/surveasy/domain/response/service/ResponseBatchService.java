package surveasy.domain.response.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import surveasy.domain.response.helper.ResponseHelper;

@RequiredArgsConstructor
@Service
public class ResponseBatchService {
    private final ResponseHelper responseHelper;

    public void doneAggregation() {
        responseHelper.doneAggregation();
    }
}

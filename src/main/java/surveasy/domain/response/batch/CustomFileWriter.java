package surveasy.domain.response.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import surveasy.domain.response.vo.ResponseFileVo;

@Component
public class CustomFileWriter {

    @StepScope
    public FlatFileItemWriter<ResponseFileVo> CustomFileWriter(Resource fileResource) {
        return new FlatFileItemWriterBuilder<ResponseFileVo>()
                .name("customFileWriter")
                .resource((WritableResource) fileResource)
                .formatted()
                .format("%s;%s;%s;%s")
                .names(new String[] {
                        "accountOwner",
                        "accountNumber",
                        "reward"
                })
                .build();
    }
}

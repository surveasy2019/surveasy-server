package surveasy.domain.file.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import surveasy.domain.file.domain.File;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Getter
public class FileAdminListResponse {

    private List<File> fileList;
    private PageInfo pageInfo;

    @Builder
    private FileAdminListResponse(Page<File> fileList) {
        if(fileList.hasContent()) this.fileList = fileList.getContent();
        this.pageInfo = PageInfo.from(fileList);
    }

    public static FileAdminListResponse from(Page<File> fileList) {
        return FileAdminListResponse.builder()
                .fileList(fileList)
                .build();
    }
}

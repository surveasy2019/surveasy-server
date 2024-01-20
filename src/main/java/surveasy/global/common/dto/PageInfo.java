package surveasy.global.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageInfo {

    private int pageNum;
    private int pageSize;
    private long totalElements;
    private long totalPages;

    @Builder
    private PageInfo(int pageNum, int pageSize, long totalElements, long totalPages) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PageInfo from(Page<?> page) {
        return PageInfo.builder()
                .pageNum(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}

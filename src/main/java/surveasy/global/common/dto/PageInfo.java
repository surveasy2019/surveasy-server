package surveasy.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageInfo {

    private int pageNum;
    private int pageSize;
    private long totalElements;
    private long totalPages;

    @Builder
    public PageInfo(int pageNum, int pageSize, long totalElements, long totalPages) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}

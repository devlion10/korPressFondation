package kr.or.kpf.lms.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Getter
@Setter
@NoArgsConstructor
public class PageSupport {
    @JsonIgnore
    private Pageable pageable;

    public void setPageable(PageRequest pageRequest) {
        this.pageable = pageRequest;
    }

    public void setPageable(Pageable pageRequest) {
        this.pageable = pageRequest;
    }

    /**
     * 페이지 offset
     */
    @SuppressWarnings("unused")
    private Integer pageNum;

    public int getPageNum() {
        return this.pageable.getPageNumber();
    }

    /**
     * 페이지 limit
     */
    @SuppressWarnings("unused")
    private Integer pageSize;

    public int getPageSize() {
        return this.pageable.getPageSize();
    }

    /**
     * 리스트 페이징 처리(시작)
     *
     * @param pageable
     * @return
     */
    public static int firstIndex(Pageable pageable) {
        return (pageable.getPageNumber() - 1) * pageable.getPageSize();
    }

    /**
     * 리스트 페이징 처리(마지막)
     *
     * @param pageable
     * @param total
     * @return
     */
    public static int lastIndex(Pageable pageable, int total) {
        return ((pageable.getPageNumber() - 1) * pageable.getPageSize()) + pageable.getPageSize() < total
                    ? ((pageable.getPageNumber() - 1) * pageable.getPageSize()) + pageable.getPageSize()
                    : total;
    }
}

package kr.or.kpf.lms.framework.model;

import kr.or.kpf.lms.framework.exception.CSRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseSummary {

	private static final int PAGE_SIZE_LIMIT = 1000; // 최대 페이지 크기 제한
	private static final int PAGE_SIZE_DEFAULT = 10; // 기본 페이지 크기 (파라미터 없는 경우 적용)


	private int count;
	private int offset;
	private int limit;

	/**
	 * 전체 건수에 따라 {@link Pageable}의 page와 size를 올바른값으로 보정 (page>=0, size>=1, 최대size제한)
	 * @param pageable Pagination 정보 객체
	 * @param pageSizeLimit 페이지당 최대 표시 갯수 제한
	 * @return 보정된 Pageable 객체
	 */
    public Pageable ensureValidPageable(Pageable pageable, int pageSizeLimit) {
    	return ensureValidPageableProc(pageable, pageSizeLimit);
    }
	/**
	 * 전체 건수에 따라 {@link Pageable}의 page와 size를 올바른값으로 보정 (page>=0, size>=1, 최대size제한)
	 * @param pageable Pagination 정보 객체
	 * @return 보정된 Pageable 객체
	 */
    public Pageable ensureValidPageable(Pageable pageable) {
		return ensureValidPageableProc(pageable, PAGE_SIZE_LIMIT);
	}

    public Pageable ensureValidPageableProc(Pageable pageable, int pageSizeLimit) {
    	if (pageable == null) {
			return null;
		}
		if (pageable instanceof PageRequest) {
			int page = pageable.getPageNumber();
			int size = pageable.getPageSize();
			Sort sort = pageable.getSort();

			return PageRequest.of(page, size, sort);
		} else {
			throw new CSRuntimeException("Unknown Pageable Type. type: " + pageable.getClass().getSimpleName());
		}
    }
}

package kr.or.kpf.lms.framework.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * {@link JsonView}를 지원하는 {@link PageImpl}
 *
 * 기본 구현체인 {@link PageImpl}이 {@link JsonView}를 처리할 수 없어서 래퍼 구현
 */
public class CSPageImpl<T> implements Page<T>, Slice<T> {

	@JsonIgnore
	private Page<T> page;

	/** 생성자 */
	protected CSPageImpl(Page<T> page) {
		this.page = page;
	}

	/** 생성자 */
	protected CSPageImpl(List<T> content, Pageable pageable, long total) {
		this.page = new PageImpl<>(content, pageable, total);
	}

	/** 팩토리함수 */
	public static <T> CSPageImpl<T> of(List<T> content, Pageable pageable, long total) {
		return new CSPageImpl<>(content, pageable, total);
	}

	/** 팩토리함수 -  추가 정보 포함 */
	public static <T> CSPageImpl<T> of(List<T> content, Pageable pageable, long total, Map<String, String> infoMap) {
		CSPageImpl page = new CSPageImpl<>(content, pageable, total);
		page.setInfoMap(infoMap);
		return page;
	}

	/** 추가 정보 내용 */
	public Map<String, String> infoMap;
	@JsonView(CSJsonView.CSBaseView.class)
	public void setInfoMap(Map<String, String> infoMap) { this.infoMap = infoMap; }


	@Override
	public Iterator<T> iterator() {
		return page.iterator();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public int getNumber() {
		return page.getNumber();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public int getSize() {
		return page.getSize();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public int getNumberOfElements() {
		return page.getNumberOfElements();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public List<T> getContent() {
		return page.getContent();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public boolean hasContent() {
		return page.hasContent();
	}

	@JsonView(CSJsonView.CSBaseView.class)
	public Sort getSort() {
		return CSSort.of(page.getSort());
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public boolean isFirst() {
		return page.isFirst();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public boolean isLast() {
		return page.isLast();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public boolean hasNext() {
		return page.hasNext();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public boolean hasPrevious() {
		return page.hasPrevious();
	}

	@Override
	public Pageable nextPageable() {
		return page.nextPageable();
	}

	@Override
	public Pageable previousPageable() {
		return page.previousPageable();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public int getTotalPages() {
		return page.getTotalPages();
	}

	@Override
	@JsonView(CSJsonView.CSBaseView.class)
	public long getTotalElements() {
		return page.getTotalElements();
	}

	@Override
	public <U> Page<U> map(Function<? super T, ? extends U> converter) {
		return page.map(converter);
	}

}

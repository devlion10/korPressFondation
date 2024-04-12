package kr.or.kpf.lms.framework.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * {@link Sort}의 구현이 {@link JsonView}를 지원할 수 없어서 변경구현
 */
public class CSSort extends Sort {

	private static final long serialVersionUID = -1447880823114987087L;

	/**
	 * 생성자
	 * @deprecated
	 */
	@Deprecated
	public CSSort(List<Order> orders) {
		super(orders);
	}

	/**
	 * 주어진 {@link Sort}객체로 {@link JsonView}를 지원하는 {@link CSSort}를 빌드
	 */
	public static CSSort of(Sort sort) {
		List<Order> orders = new ArrayList<>();
		for(Order order : sort) {
			orders.add(new CSOrder(order.getDirection(), order.getProperty(), order.getNullHandling()));
		}
		return new CSSort(orders);
	}

	/**
	 * {@link Order}의 구현이 {@link JsonView}를 지원하지 못해서 변경 구현
	 */
	public static class CSOrder extends Order {
		private static final long serialVersionUID = -4235892487869747963L;

		public CSOrder(Direction direction, String property) {
			super(direction, property);
		}

		public CSOrder(Direction direction, String property, NullHandling nullHandlingHint) {
			super(direction, property, nullHandlingHint);
		}

		@Override
		@JsonView(CSJsonView.CSBaseView.class)
		public Direction getDirection() {
			return super.getDirection();
		}

		@Override
		@JsonView(CSJsonView.CSBaseView.class)
		public String getProperty() {
			return super.getProperty();
		}

		@Override
		@JsonView(CSJsonView.CSBaseView.class)
		public boolean isAscending() {
			return super.isAscending();
		}

		@Override
		@JsonView(CSJsonView.CSBaseView.class)
		public boolean isIgnoreCase() {
			return super.isIgnoreCase();
		}

		@Override
		@JsonView(CSJsonView.CSBaseView.class)
		public NullHandling getNullHandling() {
			return super.getNullHandling();
		}

	}
}
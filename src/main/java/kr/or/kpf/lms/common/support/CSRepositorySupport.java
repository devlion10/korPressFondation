package kr.or.kpf.lms.common.support;

import com.querydsl.core.types.Predicate;

import java.util.Optional;
import java.util.function.Function;

public abstract class CSRepositorySupport extends CSComponentSupport {

    /**
     * 데이터가 있을 경우만, where절 추가
     *
     * @param value
     * @param function
     * @return
     * @param <T>
     */
    protected <T> Predicate condition(T value, Function<T, Predicate> function) {
        return Optional.ofNullable(value).map(function).orElse(null);
    }
}

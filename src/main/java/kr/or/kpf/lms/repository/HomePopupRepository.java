package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.HomePopup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 배너 Repository
 */
public interface HomePopupRepository extends JpaRepository<HomePopup, String>, QueryByExampleExecutor<HomePopup> {
    List<HomePopup> findAllByPopupStartYmdLessThanEqualAndPopupEndYmdGreaterThanAndPopupStts(LocalDateTime startDate, LocalDateTime endDate, int status);
    List<HomePopup> findAllByPopupStartYmdLessThanEqualAndPopupEndYmdGreaterThanAndPopupViewTypeAndPopupStts(LocalDateTime startDate, LocalDateTime endDate, String popupType,int status);
}

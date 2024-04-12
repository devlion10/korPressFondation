package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.HomeBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 배너 Repository
 */
public interface HomeBannerRepository extends JpaRepository<HomeBanner, String>, QueryByExampleExecutor<HomeBanner> {
    List<HomeBanner> findAllByBannerStartYmdLessThanEqualAndBannerEndYmdGreaterThanEqualAndBannerStts(LocalDateTime startDate, LocalDateTime endDate, int status);
}

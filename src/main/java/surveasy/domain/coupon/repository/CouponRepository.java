package surveasy.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.coupon.domain.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);
    List<Coupon> findAll();
}

package surveasy.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.coupon.domain.Coupon;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findByCode(String code);

    List<Coupon> findAll();
}

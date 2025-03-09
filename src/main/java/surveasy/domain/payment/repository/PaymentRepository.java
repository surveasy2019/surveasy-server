package surveasy.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

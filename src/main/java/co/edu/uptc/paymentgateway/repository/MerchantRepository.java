package co.edu.uptc.paymentgateway.repository;

import co.edu.uptc.paymentgateway.model.entity.Merchant;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
}

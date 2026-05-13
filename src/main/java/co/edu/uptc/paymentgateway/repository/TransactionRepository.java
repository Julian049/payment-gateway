package co.edu.uptc.paymentgateway.repository;

import co.edu.uptc.paymentgateway.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
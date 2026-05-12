package co.edu.uptc.paymentgateway.repository;

import co.edu.uptc.paymentgateway.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "", nativeQuery = true)
    void createTransaction(Transaction transaction);
    @Query(value = "", nativeQuery = true)
    void saveTransaction(Transaction transaction);
}
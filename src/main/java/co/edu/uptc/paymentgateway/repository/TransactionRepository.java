package co.edu.uptc.paymentgateway.repository;

import co.edu.uptc.paymentgateway.model.entity.Transaction;
import co.edu.uptc.paymentgateway.model.enums.LiquidationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findAllByIdInAndMerchantIdIdAndLiquidationStatus(
        List<UUID> ids, UUID merchantId,LiquidationStatus liquidationStatus);

    @Modifying
    @Query("UPDATE Transaction t SET t.liquidationStatus = 'LIQUIDADO' WHERE t.id IN :ids")
    void liquidateByIds(@Param("ids") List<UUID> ids);
}
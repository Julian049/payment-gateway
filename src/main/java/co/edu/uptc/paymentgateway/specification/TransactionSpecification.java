package co.edu.uptc.paymentgateway.specification;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import co.edu.uptc.paymentgateway.model.entity.Transaction;
import co.edu.uptc.paymentgateway.model.enums.LiquidationStatus;
import co.edu.uptc.paymentgateway.model.enums.TransactionStatus;
import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {

    public static Specification<Transaction> unliquidated(UUID merchantId, OffsetDateTime startDate, OffsetDateTime endDate) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("merchantId").get("id"), merchantId));
            predicates.add(cb.equal(root.get("liquidationStatus"), LiquidationStatus.NO_LIQUIDADO));
            predicates.add(cb.equal(root.get("transactionStatus"), TransactionStatus.APROBADO));

            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("transactionDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("transactionDate"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

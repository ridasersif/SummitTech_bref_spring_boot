package org.supplychain.supplychain.repository.modelDelivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.supplychain.supplychain.model.ProductOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}

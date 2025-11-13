package org.supplychain.supplychain.repository.Production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.supplychain.supplychain.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {
}

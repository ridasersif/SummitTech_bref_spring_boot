package org.supplychain.supplychain.repository.approvisionnement;


import org.supplychain.supplychain.model.SupplyOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyOrderLineRepository extends JpaRepository<SupplyOrderLine, Long>{

}

package org.supplychain.supplychain.service.modelSupplier;

import org.springframework.data.domain.Page;
import org.supplychain.supplychain.dto.supplier.SupplierDTO;
import org.supplychain.supplychain.dto.supplyOrder.SupplyOrderDTO;

import java.util.List;

public interface SupplierService {
    SupplierDTO createSupplier(SupplierDTO dto);
    SupplierDTO updateSupplier(Long id, SupplierDTO dto);
    void deleteSupplier(Long id);
//    List<SupplierDTO> getAllSuppliers();
    Page<SupplierDTO> getAllSuppliers(int page, int size);

    List<SupplierDTO> searchSupplierByName(String name);

    interface SupplyOrderService {
        SupplyOrderDTO createOrder(SupplyOrderDTO dto);
        SupplyOrderDTO getOrderById(Long id);
        List<SupplyOrderDTO> getAllOrders();
        SupplyOrderDTO updateOrder(Long id, SupplyOrderDTO dto);
        void deleteOrder(Long id);
    }
}

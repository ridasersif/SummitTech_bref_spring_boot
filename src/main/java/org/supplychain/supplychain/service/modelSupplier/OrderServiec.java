package org.supplychain.supplychain.service.modelSupplier;

import org.supplychain.supplychain.dto.order.OrderDTO;

import java.util.List;

public interface OrderServiec {
    OrderDTO createOrder(OrderDTO dto);
    OrderDTO updateOrder(Long id, OrderDTO dto);
    void deleteOrder(Long id);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long id);
}

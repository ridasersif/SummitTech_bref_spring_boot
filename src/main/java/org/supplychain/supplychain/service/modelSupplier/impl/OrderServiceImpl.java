package org.supplychain.supplychain.service.modelSupplier.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.supplychain.supplychain.dto.order.OrderDTO;
import org.supplychain.supplychain.mapper.modelSupplier.OrderMapper;
import org.supplychain.supplychain.model.Order;
import org.supplychain.supplychain.repository.approvisionnement.OrderRepository;
import org.supplychain.supplychain.repository.modelDelivery.CustomerRepository;
import org.supplychain.supplychain.service.modelSupplier.OrderServiec;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderServiec {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
//    private final ProductOrderRepository productOrderRepository;

    //    private final ProductOrderRepository productOrderRepository;
    private final OrderMapper orderMapper;

   @Override
    public OrderDTO createOrder(OrderDTO dto) {
        Order order = orderMapper.toEntity(dto);

        // relate customer
        order.setCustomer(customerRepository.findById(dto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Client introuvable")));

        // relate ProductOrders
//       order.setProductOrders(productOrderRepository.findAllById(dto.getProductOrderIds()));

       Order saved = orderRepository.save(order);

        return orderMapper.toDto(saved);
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO dto) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order introuvable"));

        existing.setStatus(dto.getStatus());
        existing.setTotalAmount(dto.getTotalAmount());


        Order saved = orderRepository.save(existing);
        return orderMapper.toDto(saved);
    }

    @Override
    public void deleteOrder(Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order introuvable"));

        // we wil add condition if order has delivery we con't not remove it
        if (existing.getDelivery() != null) {
            throw new RuntimeException("Impossible de supprimer : livraison existante");
        }

        orderRepository.delete(existing);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Order introuvable"));
    }

}

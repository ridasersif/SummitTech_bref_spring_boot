package org.supplychain.supplychain.service.modelSupplier.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.supplychain.supplychain.dto.modelDelivery.ProductOrderDto;
import org.supplychain.supplychain.dto.order.OrderDTO;
import org.supplychain.supplychain.mapper.modelDelivery.ProductOrderMapper;
import org.supplychain.supplychain.mapper.modelSupplier.OrderMapper;
import org.supplychain.supplychain.model.Order;
import org.supplychain.supplychain.model.Product;
import org.supplychain.supplychain.model.ProductOrder;
import org.supplychain.supplychain.repository.Production.ProductRepository;
import org.supplychain.supplychain.repository.approvisionnement.OrderRepository;
import org.supplychain.supplychain.repository.modelDelivery.CustomerRepository;
import org.supplychain.supplychain.repository.modelDelivery.ProductOrderRepository;
import org.supplychain.supplychain.service.modelSupplier.OrderServiec;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderServiec {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductOrderRepository productOrderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final ProductOrderMapper productOrderMapper;

   @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

       if (orderDTO.getCustomerId() == null) {
           throw new IllegalArgumentException("Customer must not be null");
       }


        Order order = orderMapper.toEntity(orderDTO);

        // relate customer
        order.setCustomer(customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Client introuvable")));


       //claculate total amount
       BigDecimal totalAmount = BigDecimal.ZERO;


       List<ProductOrder> productOrderEntities = new ArrayList<>();
       for(ProductOrderDto poDto : orderDTO.getProductOrders()){
           Product product = productRepository.findById(poDto.getProductId())
                   .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'ID : " + poDto.getProductId()));


           ProductOrder productOrder = productOrderMapper.toEntity(poDto);
           productOrder.setOrder(order);
           productOrder.setProduct(product);


           BigDecimal totalPrice = product.getCost().multiply(BigDecimal.valueOf(poDto.getQuantity()));
           productOrder.setUnitPrice(product.getCost());
           productOrder.setTotalPrice(totalPrice);

           totalAmount = totalAmount.add(totalPrice);
           productOrderEntities.add(productOrder);
       }

       order.setProductOrders(productOrderEntities);
       order.setTotalAmount(totalAmount);

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

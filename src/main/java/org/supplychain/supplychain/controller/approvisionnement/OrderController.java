package org.supplychain.supplychain.controller.approvisionnement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supplychain.supplychain.constants.supplierConstants.ApiConstants;
import org.supplychain.supplychain.dto.order.OrderDTO;
import org.supplychain.supplychain.service.modelSupplier.OrderServiec;
import java.util.List;

@RestController
@RequestMapping(ApiConstants.API+ ApiConstants.ORDER_ENDPOINT)
@RequiredArgsConstructor
public class OrderController {


        private final OrderServiec orderService;

        @PostMapping
        public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO dto) {
            return ResponseEntity.ok(orderService.createOrder(dto));
        }

        @PutMapping("/{id}")
        public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO dto) {
            return ResponseEntity.ok(orderService.updateOrder(id, dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping
        public ResponseEntity<List<OrderDTO>> getAllOrders() {
            return ResponseEntity.ok(orderService.getAllOrders());
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
            return ResponseEntity.ok(orderService.getOrderById(id));
        }
    }



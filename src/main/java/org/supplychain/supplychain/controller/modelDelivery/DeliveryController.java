package org.supplychain.supplychain.controller.modelDelivery;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supplychain.supplychain.dto.modelDelivery.DeliveryDto;
import org.supplychain.supplychain.response.SuccessResponse;
import org.supplychain.supplychain.service.modelDelivery.interfaces.IDeliveryService;

import java.util.List;

@RestController
@RequestMapping("/api/Delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final IDeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<SuccessResponse<DeliveryDto>> createDelivery(
            @Valid @RequestBody DeliveryDto dto,
            HttpServletRequest request) {

        DeliveryDto createdDelivery = deliveryService.createDelivery(dto);

        SuccessResponse<DeliveryDto> response = SuccessResponse.of(
                HttpStatus.CREATED,
                "Livraison créée avec succès",
                createdDelivery,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<DeliveryDto>>> getAllDeliveries(HttpServletRequest request) {
        List<DeliveryDto> deliveries = deliveryService.getAllDeliveries();

        SuccessResponse<List<DeliveryDto>> response = SuccessResponse.of(
                HttpStatus.OK,
                "Liste des livraisons récupérée avec succès",
                deliveries,
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<DeliveryDto>> getDeliveryById(@PathVariable Long id, HttpServletRequest request) {
        DeliveryDto delivery = deliveryService.getDeliveryById(id);

        SuccessResponse<DeliveryDto> response = SuccessResponse.of(
                HttpStatus.OK,
                "Livraison récupérée avec succès",
                delivery,
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<DeliveryDto>> updateDelivery(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryDto dto,
            HttpServletRequest request) {

        DeliveryDto updated = deliveryService.updateDelivery(id, dto);

        SuccessResponse<DeliveryDto> response = SuccessResponse.of(
                HttpStatus.OK,
                "Livraison mise à jour avec succès",
                updated,
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> deleteDelivery(@PathVariable Long id, HttpServletRequest request) {
        deliveryService.deleteDelivery(id);

        SuccessResponse<Void> response = SuccessResponse.of(
                HttpStatus.NO_CONTENT,
                "Livraison supprimée avec succès",
                null,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}

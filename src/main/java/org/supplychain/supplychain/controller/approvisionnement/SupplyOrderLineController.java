package org.supplychain.supplychain.controller.approvisionnement;

import lombok.RequiredArgsConstructor;
import org.supplychain.supplychain.dto.supplyOrder.SupplyOrderLineDTO;
import org.supplychain.supplychain.service.modelSupplier.SupplyOrderLineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply-order-lines")
@RequiredArgsConstructor
public class SupplyOrderLineController {

    private final SupplyOrderLineService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplyOrderLineDTO createLine(@RequestBody SupplyOrderLineDTO dto) {
        return service.createLine(dto);
    }

    @GetMapping("/{id}")
    public SupplyOrderLineDTO getLine(@PathVariable Long id) {
        return service.getLineById(id);
    }

    @GetMapping
    public List<SupplyOrderLineDTO> getAllLines() {
        return service.getAllLines();
    }

    @PutMapping("/{id}")
    public SupplyOrderLineDTO updateLine(@PathVariable Long id, @RequestBody SupplyOrderLineDTO dto) {
        return service.updateLine(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLine(@PathVariable Long id) {
        service.deleteLine(id);
    }
}

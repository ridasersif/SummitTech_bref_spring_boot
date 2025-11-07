package org.supplychain.supplychain.service.modelSupplier.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.supplychain.supplychain.dto.supplyOrder.SupplyOrderLineDTO;
import org.supplychain.supplychain.mapper.modelSupplier.SupplyOrderLineMapper;
import org.supplychain.supplychain.model.RawMaterial;
import org.supplychain.supplychain.model.SupplyOrder;
import org.supplychain.supplychain.model.SupplyOrderLine;
import org.supplychain.supplychain.repository.approvisionnement.SupplyOrderLineRepository;
import org.supplychain.supplychain.repository.approvisionnement.RawMaterialRepository;
import org.supplychain.supplychain.repository.approvisionnement.SupplierOrderRepository;
import org.supplychain.supplychain.service.modelSupplier.SupplyOrderLineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplyOrderLineServiceImpl implements SupplyOrderLineService {

    private final SupplyOrderLineRepository repository;
    private final SupplyOrderLineMapper mapper;
    private final SupplierOrderRepository orderRepository;
    private final RawMaterialRepository rawMaterialRepository;

    @Override
    public SupplyOrderLineDTO createLine(SupplyOrderLineDTO dto) {
        SupplyOrderLine line = new SupplyOrderLine();

        // get order and rawMaterial and realted between them
        SupplyOrder order = orderRepository.findById(dto.getSupplyOrderId())
                .orElseThrow(() -> new RuntimeException("SupplyOrder not found"));
        RawMaterial material = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new RuntimeException("RawMaterial not found"));

        line.setSupplyOrder(order);
        line.setRawMaterial(material);
        line.setQuantity(dto.getQuantity());
        line.setUnitPrice(dto.getUnitPrice());

        SupplyOrderLine saved = repository.save(line);
        return mapper.toDto(saved);
    }

    @Override
    public SupplyOrderLineDTO getLineById(Long id) {
        SupplyOrderLine line = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Line not found"));
        return mapper.toDto(line);
    }

    @Override
    public List<SupplyOrderLineDTO> getAllLines() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public SupplyOrderLineDTO updateLine(Long id, SupplyOrderLineDTO dto) {
        SupplyOrderLine line = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Line not found"));

        // update daata
        if (dto.getQuantity() != null) line.setQuantity(dto.getQuantity());
        if (dto.getUnitPrice() != null) line.setUnitPrice(dto.getUnitPrice());

        // updapte order and rawmaterial if needed
        if (dto.getRawMaterialId() != null) {
            RawMaterial material = rawMaterialRepository.findById(dto.getRawMaterialId())
                    .orElseThrow(() -> new RuntimeException("RawMaterial not found"));
            line.setRawMaterial(material);
        }
        if (dto.getSupplyOrderId() != null) {
            SupplyOrder order = orderRepository.findById(dto.getSupplyOrderId())
                    .orElseThrow(() -> new RuntimeException("SupplyOrder not found"));
            line.setSupplyOrder(order);
        }

        SupplyOrderLine saved = repository.save(line);
        return mapper.toDto(saved);
    }

    @Override
    public void deleteLine(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Line not found");
        }
        repository.deleteById(id);
    }
}

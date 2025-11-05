package org.supplychain.supplychain.mapper.modelSupplier;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.supplychain.supplychain.dto.supplyOrder.SupplyOrderDTO;
import org.supplychain.supplychain.model.SupplyOrder;

@Mapper(componentModel = "spring")
public interface SupplierOrderMapper {

    // dto to entity
    @Mapping(source = "supplierId", target = "supplier.idSupplier")
    SupplyOrder toEntity(SupplyOrderDTO dto);

    // from entitu to Dto
    @Mapping(source = "idOrder", target = "id") // realted between order.id model with id of order in dto
    @Mapping(source = "supplier.idSupplier", target = "supplierId")
    SupplyOrderDTO toDto(SupplyOrder entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "supplierId", target = "supplier.idSupplier")
    SupplyOrder updateEntity(@MappingTarget SupplyOrder entity, SupplyOrderDTO dto);
}

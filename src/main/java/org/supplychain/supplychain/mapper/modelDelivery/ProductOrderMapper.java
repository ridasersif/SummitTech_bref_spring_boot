package org.supplychain.supplychain.mapper.modelDelivery;

import org.mapstruct.*;
import org.supplychain.supplychain.dto.modelDelivery.ProductOrderDto;
import org.supplychain.supplychain.model.ProductOrder;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductOrderMapper {

    @Mapping(source = "product.idProduct", target = "productId")
    ProductOrderDto toDto(ProductOrder productOrder);

    @InheritInverseConfiguration
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductOrder toEntity(ProductOrderDto dto);
}

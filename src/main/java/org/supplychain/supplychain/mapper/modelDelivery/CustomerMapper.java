package org.supplychain.supplychain.mapper.modelDelivery;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.supplychain.supplychain.dto.modelDelivery.CustomerDto;
import org.supplychain.supplychain.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {


    //@Mapping(target = "id", source = "idCustomer")
    CustomerDto toDto(Customer customer);


    //@Mapping(target = "idCustomer", source = "id")
    Customer toEntity(CustomerDto customerDto);
}




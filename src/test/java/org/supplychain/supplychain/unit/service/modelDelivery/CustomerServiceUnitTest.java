package org.supplychain.supplychain.unit.service.modelDelivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.supplychain.supplychain.dto.modelDelivery.CustomerDto;
import org.supplychain.supplychain.exception.DuplicateResourceException;
import org.supplychain.supplychain.exception.ResourceNotFoundException;
import org.supplychain.supplychain.mapper.modelDelivery.CustomerMapper;
import org.supplychain.supplychain.model.Customer;
import org.supplychain.supplychain.repository.modelDelivery.CustomerRepository;
import org.supplychain.supplychain.service.modelDelivery.impl.CustomerServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerServiceUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerDto dto;
    private Customer entity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        dto = new CustomerDto();
        dto.setIdCustomer(1L);
        dto.setName("Test User");
        dto.setEmail("test@example.com");

        entity = new Customer();
        entity.setIdCustomer(1L);
        entity.setName("Test User");
        entity.setEmail("test@example.com");
    }

    @Test
    @DisplayName("Unit Test - createCustomer success")
    void testCreateCustomerSuccess() {
        when(customerRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(customerMapper.toEntity(dto)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(entity);
        when(customerMapper.toDto(entity)).thenReturn(dto);

        CustomerDto created = customerService.createCustomer(dto);

        assertThat(created.getEmail()).isEqualTo("test@example.com");
        verify(customerRepository, times(1)).save(entity);
    }

    @Test
    @DisplayName("Unit Test - createCustomer duplicate email")
    void testCreateCustomerDuplicateEmail() {
        when(customerRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> customerService.createCustomer(dto));
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Unit Test - getCustomerById success")
    void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(customerMapper.toDto(entity)).thenReturn(dto);

        CustomerDto result = customerService.getCustomerById(1L);
        assertThat(result.getName()).isEqualTo("Test User");
    }

    @Test
    @DisplayName("Unit Test - getCustomerById not found")
    void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    @DisplayName("Unit Test - getAllCustomers with filter")
    void testGetAllCustomers() {
        Page<Customer> page = new PageImpl<>(List.of(entity));
        when(customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("test", "test", PageRequest.of(0, 10)))
                .thenReturn(page);
        when(customerMapper.toDto(entity)).thenReturn(dto);

        Page<CustomerDto> result = customerService.getAllCustomers(0, 10, "test");
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Unit Test - updateCustomer")
    void testUpdateCustomer() {
        CustomerDto updatedDto = new CustomerDto();
        updatedDto.setName("Updated");
        updatedDto.setEmail("updated@example.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(customerRepository.save(entity)).thenReturn(entity);
        when(customerMapper.toDto(entity)).thenReturn(updatedDto);

        CustomerDto result = customerService.updateCustomer(1L, updatedDto);
        assertThat(result.getName()).isEqualTo("Updated");
    }

    @Test
    @DisplayName("Unit Test - deleteCustomer")
    void testDeleteCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(customerRepository).delete(entity);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).delete(entity);
    }
}

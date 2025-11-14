package org.supplychain.supplychain.integration.repository.modelDelivery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.supplychain.supplychain.model.Customer;
import org.supplychain.supplychain.repository.modelDelivery.CustomerRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should save a new customer")
    void testSaveCustomer() {
        // 1 arrange  prepsare data
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("1234567890");
        customer.setAddress("123 Street");

        //act
        Customer saved = customerRepository.save(customer);

        //assert
        assertThat(saved.getIdCustomer()).isNotNull();
        assertThat(saved.getName()).isEqualTo("John Doe");
        assertThat(saved.getEmail()).isEqualTo("john@example.com");
        assertThat(saved.getPhone()).isEqualTo("1234567890");
    }



    @Test
    @DisplayName("Should check if email exists")
    void testExistsByEmail() {
        Customer customer = new Customer();
        customer.setName("Jane Doe");
        customer.setEmail("jane@example.com");
        customer.setAddress("456 Avenue");

        customerRepository.save(customer);

        boolean exists = customerRepository.existsByEmail("jane@example.com");
        boolean notExists = customerRepository.existsByEmail("unknown@example.com");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("Should search by name or email")
    void testFindByNameOrEmail() {
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setEmail("alice@example.com");
        customer1.setAddress("Street 1");

        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setEmail("bob@example.com");
        customer2.setAddress("Street 2");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        Page<Customer> page = customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                "ali", null, PageRequest.of(0, 10));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName()).isEqualTo("Alice");

        page = customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                null, "bob@example.com", PageRequest.of(0, 10));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName()).isEqualTo("Bob");
    }

    @Test
    @DisplayName("Should find all customers in pageable")
    void testFindAllPageable() {
        Customer c1 = new Customer(null, "C1", "c1@example.com", "123", "Addr1", null);
        Customer c2 = new Customer(null, "C2", "c2@example.com", "456", "Addr2", null);

        customerRepository.save(c1);
        customerRepository.save(c2);

        Page<Customer> page = customerRepository.findAll(PageRequest.of(0, 1));
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalElements()).isEqualTo(2);
    }
}


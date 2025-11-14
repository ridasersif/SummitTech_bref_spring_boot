package org.supplychain.supplychain.integration.repository.modelDelivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.supplychain.supplychain.model.Customer;
import org.supplychain.supplychain.model.Delivery;
import org.supplychain.supplychain.model.Order;
import org.supplychain.supplychain.enums.DeliveryStatus;
import org.supplychain.supplychain.repository.approvisionnement.OrderRepository;
import org.supplychain.supplychain.repository.modelDelivery.CustomerRepository;
import org.supplychain.supplychain.repository.modelDelivery.DeliveryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class DeliveryRepositoryIntegrationTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private Order order;
    private Delivery delivery;

    @BeforeEach
    void setup() {
        deliveryRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();


        customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setAddress("123 Test Street");
        customerRepository.save(customer);


        order = new Order();
        order.setCustomer(customer);
        orderRepository.save(order);


        delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDeliveryAddress("Street 1");
        delivery.setDriver("Driver 1");
        delivery.setStatus(DeliveryStatus.PLANIFIEE);
        delivery.setDeliveryDate(LocalDate.now().plusDays(1));
        delivery.setDeliveryCost(new BigDecimal("50.00"));
        deliveryRepository.save(delivery);
    }

    @Test
    @DisplayName("Check if delivery exists by order")
    void testExistsByOrder() {
        boolean exists = deliveryRepository.existsByOrder(order);
        assertThat(exists).isTrue();
    }


    @DisplayName("Save a new delivery")
    void testSaveDelivery() {

        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        orderRepository.save(newOrder);

        Delivery newDelivery = new Delivery();
        newDelivery.setOrder(newOrder);
        newDelivery.setDeliveryAddress("Street 2");
        newDelivery.setDriver("Driver 2");
        newDelivery.setStatus(DeliveryStatus.EN_COURS);
        newDelivery.setDeliveryDate(LocalDate.now().plusDays(2));
        newDelivery.setDeliveryCost(new BigDecimal("30.00"));

        Delivery saved = deliveryRepository.save(newDelivery);

        assertThat(saved.getIdDelivery()).isNotNull();
        assertThat(saved.getDriver()).isEqualTo("Driver 2");
    }


    @Test
    @DisplayName("Find delivery by ID")
    void testFindById() {
        Optional<Delivery> found = deliveryRepository.findById(delivery.getIdDelivery());
        assertThat(found).isPresent();
        assertThat(found.get().getDriver()).isEqualTo("Driver 1");
    }

    @Test
    @DisplayName("Find all deliveries")
    void testFindAll() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        assertThat(deliveries).hasSize(1);
    }

    @Test
    @DisplayName("Delete delivery")
    void testDelete() {
        deliveryRepository.delete(delivery);
        assertThat(deliveryRepository.existsById(delivery.getIdDelivery())).isFalse();
    }

    @Test
    @DisplayName("Find order by ID")
    void testFindOrderById() {
        Optional<Order> foundOrder = orderRepository.findById(order.getIdOrder());
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getCustomer().getName()).isEqualTo("Test Customer");
    }
}

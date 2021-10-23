package io.github.nimbo1999.domain.repository;

import io.github.nimbo1999.domain.entity.Customer;
import io.github.nimbo1999.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCustomer(Customer customer);
}

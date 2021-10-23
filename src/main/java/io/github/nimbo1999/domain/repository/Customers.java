package io.github.nimbo1999.domain.repository;

import io.github.nimbo1999.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Customers extends JpaRepository<Customer, Integer > {

    @Query(value = " select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Customer> encontrarPorNome( @Param("nome") String nome );

    @Query(" delete from Customer c where c.nome =:nome ")
    @Modifying
    void deleteByNome(String nome);

    boolean existsByNome(String nome);

    @Query(" select c from Customer c left join fetch c.pedidos where c.id = :id  ")
    Customer findCustomerFetchPedidos( @Param("id") Integer id );


}

package io.github.nimbo1999.service;

import java.util.List;

import io.github.nimbo1999.rest.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO findById(Integer customerId);
    CustomerDTO save(CustomerDTO customerDto);
    void delete(Integer customerId);
    void update(Integer customerId, CustomerDTO customerDto);
    List<CustomerDTO> findAll(CustomerDTO filter);
}

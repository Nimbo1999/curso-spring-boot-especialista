package io.github.nimbo1999.adapter;

import java.util.List;
import java.util.stream.Collectors;

import io.github.nimbo1999.domain.entity.Customer;
import io.github.nimbo1999.rest.dto.CustomerDTO;

public class CustomerAdapter {

    public static Customer dtoToEntity(CustomerDTO customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setCpf(customerDto.getPersonId());
        customer.setNome(customerDto.getName());
        customer.setCreatedAt(customerDto.getCreatedAt());
        customer.setUpdatedAt(customerDto.getUpdatedAt());
        return customer;
    }

    public static CustomerDTO entityToDto(Customer customer) {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getNome());
        customerDto.setPersonId(customer.getCpf());
        customerDto.setCreatedAt(customer.getCreatedAt());
        customerDto.setUpdatedAt(customer.getUpdatedAt());
        return customerDto;
    }

    public static List<CustomerDTO> entityListToDtoList(List<Customer> customers) {
        return customers
            .stream()
            .map(CustomerAdapter::entityToDto)
            .collect(Collectors.toList());
    }

}

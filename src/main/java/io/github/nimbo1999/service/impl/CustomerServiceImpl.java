package io.github.nimbo1999.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.nimbo1999.adapter.CustomerAdapter;
import io.github.nimbo1999.domain.entity.Customer;
import io.github.nimbo1999.domain.repository.Customers;
import io.github.nimbo1999.exception.RegraNegocioException;
import io.github.nimbo1999.rest.dto.CustomerDTO;
import io.github.nimbo1999.service.CustomerService;
import io.github.nimbo1999.utils.InstantUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final Customers customerRepository;

    @Override
    public CustomerDTO findById(Integer customerId) {
        Customer persistedCustomer = customerRepository
            .findById(customerId)
            .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado!"));

        return CustomerAdapter.entityToDto(persistedCustomer);
    }

    @Override
    public CustomerDTO save(CustomerDTO customer) {
        if (customer.getName() == null) {
            throw new RegraNegocioException("It's required to send customer name");
        }
        Customer newCustomer = CustomerAdapter.dtoToEntity(customer);
        Instant now = InstantUtils.instantNow();
        newCustomer.setCreatedAt(now);
        newCustomer.setUpdatedAt(now);

        Integer customerId = customerRepository.save(newCustomer).getId();
        customer.setId(customerId);
        customer.setCreatedAt(newCustomer.getCreatedAt());
        customer.setUpdatedAt(newCustomer.getUpdatedAt());
        return customer;
    }

    @Override
    public void delete(Integer customerId) {
        Customer customer = customerRepository
            .findById(customerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Customer not found"
            ));

        customerRepository.delete(customer);
    }

    @Override
    public void update(Integer customerId, CustomerDTO customerDto) {
        customerRepository
            .findById(customerId)
            .map(result -> {
                Customer persistedCustomer = result;
                if (customerDto.getName() != null) {
                    persistedCustomer.setNome(customerDto.getName());
                }
                if (customerDto.getPersonId() != null) {
                    persistedCustomer.setCpf(customerDto.getPersonId());
                }
                persistedCustomer.setUpdatedAt(InstantUtils.instantNow());
                return customerRepository.save(persistedCustomer);
            })
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Customer not found")
            );
    }

    @Override
    public List<CustomerDTO> findAll(CustomerDTO filter) {
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Customer> example = Example.of(CustomerAdapter.dtoToEntity(filter), matcher);
        List<Customer> customerList = customerRepository.findAll(example);
        return CustomerAdapter.entityListToDtoList(customerList);
    }
    
}

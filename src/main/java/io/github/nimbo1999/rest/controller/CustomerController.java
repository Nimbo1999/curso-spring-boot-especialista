package io.github.nimbo1999.rest.controller;

import io.github.nimbo1999.rest.dto.CustomerDTO;
import io.github.nimbo1999.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerServiceImpl service;

    @GetMapping("{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Integer customerId) {
        return service.findById(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO save(@RequestBody @Valid CustomerDTO customerDto){
        return service.save(customerDto);
    }

    @DeleteMapping("{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer customerId){
        service.delete(customerId);
    }

    @PutMapping("{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer customerId, @RequestBody CustomerDTO customerDto) {
        service.update(customerId, customerDto);
    }

    @GetMapping
    public List<CustomerDTO> find(CustomerDTO filter) {
        return service.findAll(filter);
    }

}

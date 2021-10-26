package io.github.nimbo1999.validation.contraintValidation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.nimbo1999.domain.enums.StatusPedido;
import io.github.nimbo1999.validation.OrderStatusField;

public class OrderStatusFieldValidator implements ConstraintValidator<OrderStatusField, String> {

    @Override
    public boolean isValid(String status, ConstraintValidatorContext validator) {
        if (status == null || status.isEmpty()) {
            return false;
        }
        List<StatusPedido> listOfStatus = Arrays.asList(StatusPedido.values());
        try {
            StatusPedido st = StatusPedido.valueOf(status);
            return listOfStatus.contains(st);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public void initialize(OrderStatusField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
}

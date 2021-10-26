package io.github.nimbo1999.validation.contraintValidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.nimbo1999.validation.NotEmptyList;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List<?>> {

    @Override
    public boolean isValid(List<?> listToValidate, ConstraintValidatorContext arg1) {
        return listToValidate != null && !listToValidate.isEmpty();
    }

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

}

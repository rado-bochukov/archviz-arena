package com.example.archvizarena.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {


    private String first;
    private String second;
    private String message;


    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {

        BeanWrapper beanWrapper= PropertyAccessorFactory.forBeanPropertyAccess(o);

        Object firstValue = beanWrapper.getPropertyValue(this.first);
        Object secondValue = beanWrapper.getPropertyValue(this.second);

        boolean valid;

        if(firstValue==null){
            return  secondValue==null;
        }else {
            valid=firstValue.equals(secondValue);
        }

        if(!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(second)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}

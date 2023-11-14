package com.example.archvizarena.util.validation;

import com.example.archvizarena.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUserNameValidation implements ConstraintValidator<UniqueUserName , String> {
        private final UserRepository userRepository;

    public UniqueUserNameValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return userRepository.findByUsername(value)
                .isEmpty();
    }
}

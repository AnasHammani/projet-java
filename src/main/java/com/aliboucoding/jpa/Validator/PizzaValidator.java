package com.aliboucoding.jpa.Validator;

import com.aliboucoding.jpa.models.Pizza;
import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class PizzaValidator {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    //	ValidatorFactory : Une fabrique qui permet de créer des instances de validateurs.

    private final Validator validator = factory.getValidator();
    //  Validator : C’est cet objet qui effectue réellement la validation de l’objet Pizza.

    public Set<String> validate(Pizza pizzaToValidate) {

        Set<ConstraintViolation<Pizza>> violations = validator.validate(pizzaToValidate);


        if (!violations.isEmpty()) {

            return violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

}

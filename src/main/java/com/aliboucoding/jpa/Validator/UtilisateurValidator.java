package com.aliboucoding.jpa.Validator;

import com.aliboucoding.jpa.user.Utilisateur;
import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UtilisateurValidator {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory() ;
    //	ValidatorFactory : Une fabrique qui permet de créer des instances de validateurs.

    private final Validator validator = validatorFactory.getValidator();
    //  Validator : C’est cet objet qui effectue réellement la validation de l’objet Pizza.


    public Set<String> validate(Utilisateur userToValidate) {

        Set<ConstraintViolation<Utilisateur>> violations = validator.validate(userToValidate);

        if (!violations.isEmpty()) {
            return violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

}

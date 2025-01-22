package com.aliboucoding.jpa.services;

import com.aliboucoding.jpa.Validator.UtilisateurValidator;
import com.aliboucoding.jpa.user.Utilisateur;
import com.aliboucoding.jpa.user.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor

public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurValidator utilisateurValidator;

    public List<Utilisateur> getAllUser() {
        return utilisateurRepository.findAll();

    }

    public Utilisateur getUserById(Integer id) {
        return utilisateurRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User ID not found"));
    }


    public Utilisateur saveUser(Utilisateur utilisateur) {

            var violations = utilisateurValidator.validate(utilisateur);
            if (!violations.isEmpty()) {
                throw new IllegalArgumentException(String.join("\n", violations));
            }

        return utilisateurRepository.save(utilisateur);
    }

}

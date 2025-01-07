package com.aliboucoding.jpa.services;

import com.aliboucoding.jpa.user.Utilisateur;
import com.aliboucoding.jpa.user.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor

public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUser() {
        return utilisateurRepository.findAll();

    }

    public Utilisateur getUserById(Integer id) {
        return utilisateurRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User ID not found"));
    }

    public Utilisateur saveUser(Utilisateur utilisateur) {

        return utilisateurRepository.save(utilisateur);
    }

}

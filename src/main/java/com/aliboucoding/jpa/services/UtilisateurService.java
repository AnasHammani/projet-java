package com.aliboucoding.jpa.services;

import com.aliboucoding.jpa.user.Utilisateur;
import com.aliboucoding.jpa.user.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
/*Je l'ai rajouté moi, ds la vidéo il a du en parler.
ça permet d'éviter de créer sois meme un constructeur et initialiser les attributs
*/
@AllArgsConstructor

public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;



    public List<Utilisateur> getAllUser() {
        return utilisateurRepository.findAll();
        // Utilise le repository pour obtenir la liste des users
    }

    public Utilisateur getUserById(Integer id) {
        return utilisateurRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User ID not found"));
    }

    public Utilisateur saveUser(Utilisateur utilisateur) {

        /*if (utilisateurRepository.existsByEmailUser(utilisateur.getEmail_user())) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }
        if (utilisateurRepository.existsByGsmUser(utilisateur.getGsm_user())) {
            throw new IllegalArgumentException("Numéro de téléphone déjà utilisé !");
        }
        */
        return utilisateurRepository.save(utilisateur);
    }



}

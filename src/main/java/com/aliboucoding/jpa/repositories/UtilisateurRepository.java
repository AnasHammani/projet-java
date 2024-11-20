package com.aliboucoding.jpa.repositories;

import com.aliboucoding.jpa.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {


    /*boolean existsByEmailUser(String email_user);
    boolean existsByGsmUser(String gsm_user); */

}


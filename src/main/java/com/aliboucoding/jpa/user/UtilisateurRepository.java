package com.aliboucoding.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {


    /*
        Retourner un Utilisateur trouvé dans la base s'il y en a un.
		Retourner un Optional vide si aucun utilisateur avec cet email n’existe.
    */
    Optional<Utilisateur> findByEmail(String email);



}


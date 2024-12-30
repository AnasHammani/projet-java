package com.aliboucoding.jpa.auth;

import com.aliboucoding.jpa.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    private String nom_user;
    private String email;
    private String password;
    private String adresse_user;
    private String gsm_user;
    private Role role;


}

//3. Pourquoi une classe RegisterRequest (nom, email, mdp, adresse, gsm, rôle) ?
//
//Raison principale : Transporter les données d’inscription
//
//	•	Lorsqu’un utilisateur veut s’inscrire, il doit fournir beaucoup plus d’informations que lors de la connexion
//	(nom, email, mot de passe, adresse, etc.).
//	•	Cette classe regroupe toutes ces données pour représenter clairement une requête d’inscription.
//
//Pourquoi une classe spécifique ?
//	1.	Organisation :
//	•	Cela simplifie la gestion des données : au lieu d’envoyer ou de valider chaque champ individuellement,
//	tout est regroupé dans un seul objet.
//	2.	Validation des données :
//	•	Vous pouvez définir des contraintes pour chaque champ (@NotEmpty, @Email, etc.) directement dans cette classe.
//	3.	Évolutivité :
//	•	Si vous ajoutez un nouveau champ à l’inscription (par exemple, date de naissance),
//	il suffit de le rajouter dans cette classe sans toucher aux autres parties du code.
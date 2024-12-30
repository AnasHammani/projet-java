package com.aliboucoding.jpa.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;

}

//1. Pourquoi une classe AuthenticationRequest (email + mot de passe) ?

//Raison principale : Transporter les données de connexion

	//•	Lorsqu’un utilisateur veut se connecter, il doit envoyer uniquement son email et son mot de passe.
	//•	Cette classe encapsule ces deux champs pour représenter clairement les données nécessaires à l’authentification.

//Pourquoi une classe spécifique ?
 //1.	Simplicité et clarté :
            // •	En créant une classe dédiée, il est évident que cette requête concerne la connexion.
  //2.	Validation des données :
         //    •	Vous pouvez facilement ajouter des contraintes, comme @NotEmpty ou @Email, pour vérifier les données de l’utilisateur.
//3.	Bonne pratique REST :
     //   •	Les requêtes REST doivent avoir des structures spécifiques et prévisibles. Une classe rend cela explicite.
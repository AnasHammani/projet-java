package com.aliboucoding.jpa.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;

}

// Raison principale : Retourner des réponses structurées
//
//	•	Quand un utilisateur se connecte ou s’inscrit, vous retournez un JWT token pour qu’il puisse prouver son identité.
//	•	Cette classe encapsule ce token dans un objet structuré.
//
//Pourquoi ne pas simplement retourner un String ?
//	1.	Clarté et extensibilité :
//	•	Aujourd’hui, cette classe ne contient qu’un token, mais demain, vous pourriez ajouter d’autres champs
//	(ex. rôle, ID utilisateur, expiration du token).
//	2.	Bonne pratique REST :
//	•	Les réponses REST doivent être sous forme d’objets JSON. Une classe permet de structurer cette réponse.
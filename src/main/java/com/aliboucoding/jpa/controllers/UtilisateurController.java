package com.aliboucoding.jpa.controllers;

import com.aliboucoding.jpa.user.Utilisateur;
import com.aliboucoding.jpa.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController

//	•	Définit le chemin de base pour toutes les requêtes de la classe.
//	•	Ici : toutes les routes de ce contrôleur commencent par /api/users.
@RequestMapping("/api/users")

public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    //TEST

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }



    // FIN DU TEST

    @GetMapping
    //GetMapping c est pour dire que quand un client effectue une requête Get avec l url
    // "api/users", SpringBoot execute la methode @GetMapping ci dessous

    public List<Utilisateur> getAllUser() {

        //La méthode retourne une liste d’objets Utilisateur, que Spring Boot convertira
        // automatiquement en JSON grâce à la bibliothèque Jackson (ou Gson) intégrée.
        return utilisateurService.getAllUser();
    }

    @GetMapping("/{id}")
    public Utilisateur getUserById(@PathVariable Integer id) {
        return utilisateurService.getUserById(id);
    }

    @PostMapping()
    public Utilisateur addUser(@RequestBody Utilisateur utilisateur) {

        return utilisateurService.saveUser(utilisateur);
    }
}

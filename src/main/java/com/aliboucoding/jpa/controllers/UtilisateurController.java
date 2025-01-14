package com.aliboucoding.jpa.controllers;

import com.aliboucoding.jpa.user.Utilisateur;
import com.aliboucoding.jpa.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController


@RequestMapping("/api/users")

//@PreAuthorize("hasRole('ADMIN')")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-user")
    public List<Utilisateur> getAllUser() {

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

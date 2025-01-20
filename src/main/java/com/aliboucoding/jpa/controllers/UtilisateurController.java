package com.aliboucoding.jpa.controllers;

import com.aliboucoding.jpa.user.Utilisateur;
import com.aliboucoding.jpa.services.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController

@Tag(name="Utilisateur")
@RequestMapping("/api/users")

//@PreAuthorize("hasRole('ADMIN')")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }


    @Operation(
            description = "GET : Tous les user",
            summary = "Summary pour les USER",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/token invalide",
                            responseCode = "403"
                    )
            }
    )
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

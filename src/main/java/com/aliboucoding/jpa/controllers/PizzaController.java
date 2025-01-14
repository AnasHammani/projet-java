package com.aliboucoding.jpa.controllers;

import com.aliboucoding.jpa.models.Pizza;
import com.aliboucoding.jpa.services.PizzaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")

@AllArgsConstructor
//j'ai mis ça pour ne pas avoir besoin d ecrire le constructeur testgit
public class PizzaController {

    private final PizzaService pizzaService;


    @GetMapping("/get")
    public List<Pizza> getAllPizzas() {

        return pizzaService.getAllPizzas();
    }

    @GetMapping("/{id}")
    public Pizza getPizzaById(@PathVariable Integer id) {

        return pizzaService.getPizzaById(id);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addPizza(@RequestBody Pizza pizza) {

        try {
            pizzaService.savePizza(pizza);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pizza ajoutée avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur de validation: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<String> updatePizza(@Valid @RequestBody Pizza pizza) {

        try {
            pizzaService.updatePizza(pizza);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pizza MODIFIER avec succès !");

        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur pour UPDATE pizza: " + e.getMessage());
        }
    }
}

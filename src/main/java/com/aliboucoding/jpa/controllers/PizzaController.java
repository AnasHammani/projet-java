package com.aliboucoding.jpa.controllers;

import com.aliboucoding.jpa.models.Pizza;
import com.aliboucoding.jpa.services.PizzaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pizzas")
//@SecurityRequirement(name="bearerAuth") //swagger

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

        String response = pizzaService.savePizza(pizza);

        if(response.equals("pizza added successfully")) {
            return new ResponseEntity<>("pizza added successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<String> updatePizza(@RequestBody  Pizza pizza) {

            String response = pizzaService.updatePizza(pizza);

            if(response.equals("pizza updated successfully")) {
                return new ResponseEntity<>("pizza updated successfully", HttpStatus.CREATED);
            }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}

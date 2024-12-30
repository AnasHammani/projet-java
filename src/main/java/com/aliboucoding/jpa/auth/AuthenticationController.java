package com.aliboucoding.jpa.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
        private final AuthenticationService service;

        @GetMapping
        //ResponseEntity est une classe de Spring qui représente la réponse HTTP que votre API envoie au client
        public ResponseEntity<String> sayHello() {
                return ResponseEntity.ok("Hello from secured endpoint ii");
        }


        //@RequestBody ca va capturer les données d inscription de l user comme nom prenom email gsm etc
        // RegisterRequest c est une classe avec le type de donnée que @RequestBody va capturer justement
        // on utilise le service pour déléguer la logique métier
        @PostMapping("/register")
        public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){

                return ResponseEntity.ok(service.register(request));


        }

        @PostMapping("/authenticate")
        public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){


                return ResponseEntity.ok(service.authenticate(request));

        }


}

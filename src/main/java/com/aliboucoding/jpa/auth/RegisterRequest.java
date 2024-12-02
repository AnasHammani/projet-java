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

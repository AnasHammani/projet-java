package com.aliboucoding.jpa.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//Le constructeur d’une énumération est automatiquement appelé à chaque fois que tu définis une constante
// dans l’énumération (ADMIN_READ, ADMIN_UPDATE, etc.).

public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("admin:create");
//Ici, chaque constante de l’énumération appelle le constructeur et passe une valeur en argument 

    @Getter
    private final String permission;

}
//Donc, quand tu appelles Permission.ADMIN_READ.getPermission(), voici ce qui se passe :
//	1.	Permission.ADMIN_READ fait référence à la constante ADMIN_READ.
//	2.	getPermission() retourne la valeur de l’attribut permission de cette constante, qui est "admin:read".
package com.fidev.avilasoft.util;

import org.springframework.stereotype.Component;

@Component
public class AppConst {

    public AppConst() {}

    public static final String NOT_FOUND = "404";
    public static final String CONFLICT = "409";

    public static final String USER_EXISTS_CODE = "U-EX";
    public static final String USER_EXISTS_MSG = "El usuario ya existe";

    public static final String NOT_FOUND_TOKEN = "El token no es valido";
    public static final String INVALID_TOKEN = "El token no es valido";

    public static final String EMAIL_EXISTS_CODE = "E-EX";
    public static final String EMAIL_EXISTS_MSG = "El correo ya existe";
    public static final String EMAIL_SENT_ERROR_CODE = "E-ERR";
    public static final String EMAIL_SENT_ERROR_MSG = "Error al enviar correo de verificación. Consulta a asoporte técnico";

    public static final String DEFAULT_ROLE_404_CODE = "ROL-NF";
    public static final String DEFAULT_ROLE_404_MSG = "No se encontró el rol del usuario";

}

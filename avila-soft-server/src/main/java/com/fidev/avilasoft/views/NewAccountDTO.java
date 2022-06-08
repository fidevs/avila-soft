package com.fidev.avilasoft.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewAccountDTO {
    private String email;
    private String user;
    private String pswd;
}

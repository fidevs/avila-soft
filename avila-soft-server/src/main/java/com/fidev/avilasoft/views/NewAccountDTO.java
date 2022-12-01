package com.fidev.avilasoft.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NewAccountDTO {
    private String email;
    private String user;
    private String pswd;
}

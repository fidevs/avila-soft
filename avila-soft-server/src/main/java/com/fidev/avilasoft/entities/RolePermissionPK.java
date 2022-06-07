package com.fidev.avilasoft.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
@EqualsAndHashCode
public class RolePermissionPK implements Serializable {

    @ManyToOne
    private Role role;

    @ManyToOne
    private Authorization auth;

}

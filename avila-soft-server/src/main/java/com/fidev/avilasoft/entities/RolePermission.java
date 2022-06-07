package com.fidev.avilasoft.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@AssociationOverrides({
        @AssociationOverride(name = "roleAuthPK.role", joinColumns = @JoinColumn(name = "role_id")),
        @AssociationOverride(name = "roleAuthPK.auth", joinColumns = @JoinColumn(name = "auth_id"))
})
@Table(name = "role_permission")
public class RolePermission {

    @EmbeddedId
    RolePermissionPK roleAuthPK;

    @Transient
    public Role getRole() {
        return getRoleAuthPK().getRole();
    }

    public void setRole(Role role) {
        getRoleAuthPK().setRole(role);
    }

    @Transient
    public Authorization getAuth() {
        return getRoleAuthPK().getAuth();
    }

    public void setAuth(Authorization auth) {
        getRoleAuthPK().setAuth(auth);
    }

}

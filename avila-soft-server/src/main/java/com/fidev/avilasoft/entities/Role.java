package com.fidev.avilasoft.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "usr_role")
public class Role {

    @Id
    @Column(name = "role_id", length = 36)
    private String id;

    @Column(length = 15, nullable = false)
    private String name;

    @PrePersist
    protected void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "roleAuthPK.role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RolePermission> authorizations = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

}
